package com.indas.portal.repositories;

import com.indas.portal.entities.Part;
import com.indas.portal.service.criteria.FindPartCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional("partTM")
public class PartRepository extends BaseRepository<Part> {

    @PersistenceContext(unitName ="partEntityManagerFactory")
    protected EntityManager em;

    @Override
    public Class<Part> getClassEntity() {
        return Part.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<String> findAllPartIds(){
        return findAll().stream().map(Part::getId).collect(Collectors.toList());
    }

    public List<Part> getParts(FindPartCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Part> cq = cb.createQuery(Part.class);
        Root<Part> root = cq.from(Part.class);
        cq.select(root).where(getPredicate(criteria, cb, root));
        return em.createQuery(cq).getResultList();
    }

    private Predicate getPredicate(FindPartCriteria criteria, CriteriaBuilder cb, Root<Part> root){
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getStart() != null && criteria.getEnd() != null) {
            Predicate predicateStart = cb.greaterThanOrEqualTo(root.get("startTime"), criteria.getStart());
            Predicate predicateEnd = cb.lessThanOrEqualTo(root.get("endTime"), criteria.getEnd());
            predicates.add(cb.and(predicateStart, predicateEnd));
        } else if (criteria.getStart() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("startTime"), criteria.getStart()));
        } else if (criteria.getEnd() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("endTime"), criteria.getEnd()));
        }

        if (criteria.getOper() != null){
            predicates.add(cb.like( root.get("oper"), partialCoincidence(criteria.getOper())));
        }

        if (criteria.getId() != null){
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    private String partialCoincidence(String str){
        return "%"+str+"%";
    }
}
