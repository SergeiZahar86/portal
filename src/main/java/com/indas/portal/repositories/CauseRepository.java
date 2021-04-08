package com.indas.portal.repositories;

import com.indas.portal.entities.Cause;
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
public class CauseRepository extends BaseRepository<Cause>{

    @PersistenceContext(unitName ="partEntityManagerFactory")
    protected EntityManager em;

    @Override
    public Class<Cause> getClassEntity() {
        return Cause.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Cause save(Cause entity) {
        if (entity.getId() == null) {
            Optional<Cause> optional = getByName(entity.getName());
            if (optional.isEmpty()){
                super.save(entity);
            } else {
                return optional.get();
            }
        }
        return super.save(entity);
    }

    public Optional<Cause> getByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cause> cq = cb.createQuery(getClassEntity());
        Root<Cause> root = cq.from(getClassEntity());
        cq.select(root).where(cb.equal(root.get("name"),name));
        return em.createQuery(cq).getResultStream().findFirst();
    }

//    public String trim(String str){
//        return ""+str+"";
//    }
}
