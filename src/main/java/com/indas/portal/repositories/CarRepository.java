package com.indas.portal.repositories;

import com.indas.portal.entities.Car;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional("partTM")
public class CarRepository extends BaseRepository<Car> {

    @PersistenceContext(unitName ="partEntityManagerFactory")
    protected EntityManager em;

    @Override
    public Class<Car> getClassEntity() {
        return Car.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
