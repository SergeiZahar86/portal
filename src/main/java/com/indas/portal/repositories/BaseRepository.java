package com.indas.portal.repositories;

import com.indas.portal.entities.BaseEntitie;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import static java.util.Objects.isNull;

@Repository
@Transactional("partTM")
public abstract class BaseRepository<T extends BaseEntitie> {

    public T save(T entity) {
        if (isNew(entity)) {
            getEntityManager().persist(entity);
            return entity;
        } else {
            return getEntityManager().merge(entity);
        }
    }

    public T getById(String id) {
        return getEntityManager().find(getClassEntity(), id);
    }

    public void remove(String id) {
        T entity = getEntityManager().find(getClassEntity(), id);
        if (isNull(entity)) throw new IllegalArgumentException("Entity is not found");
        getEntityManager().remove(entity);
    }

    protected boolean isNew(T entity) {
        return isNull(entity.getId());
    }

    public abstract Class<T> getClassEntity();

    public List<T> findAll(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getClassEntity());
        Root<T> rootEntry = cq.from(getClassEntity());
        CriteriaQuery<T> all = cq.select(rootEntry);

        TypedQuery<T> allQuery = getEntityManager().createQuery(all);
        return allQuery.getResultList();
    }

    protected abstract EntityManager getEntityManager();
}
