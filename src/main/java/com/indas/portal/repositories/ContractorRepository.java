package com.indas.portal.repositories;

import com.indas.portal.entities.Contractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
@Transactional("partTM")
public class ContractorRepository extends BaseRepository<Contractor> {

    @PersistenceContext(unitName ="partEntityManagerFactory")
    protected EntityManager em;

    @Override
    public Class<Contractor> getClassEntity() {
        return Contractor.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Contractor save(Contractor entity) {
        if (entity.getId() == null) {
            Optional<Contractor> optional = getByName(entity.getName());
            if (optional.isEmpty()){
                super.save(entity);
            } else {
                return optional.get();
            }
        }
        return super.save(entity);
    }

    public Optional<Contractor> getByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contractor> cq = cb.createQuery(getClassEntity());
        Root<Contractor> root = cq.from(getClassEntity());
        cq.select(root).where(cb.equal(root.get("name"),name));
        return em.createQuery(cq).getResultStream().findFirst();
    }
}
