package com.indas.portal.repositories;

import com.indas.portal.entities.Mat;
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
public class MatRepository extends BaseRepository<Mat> {

    @PersistenceContext(unitName ="partEntityManagerFactory")
    protected EntityManager em;

    @Override
    public Class<Mat> getClassEntity() {
        return Mat.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Mat save(Mat entity) {
        if (entity.getId() == null) {
            Optional<Mat> optional = getByName(entity.getName());
            if (optional.isEmpty()){
                super.save(entity);
            } else {
                return optional.get();
            }
        }
        return super.save(entity);
    }

    public Optional<Mat> getByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Mat> cq = cb.createQuery(getClassEntity());
        Root<Mat> root = cq.from(getClassEntity());
        cq.select(root).where(cb.equal(root.get("name"),name));
        return em.createQuery(cq).getResultStream().findFirst();
    }
}
