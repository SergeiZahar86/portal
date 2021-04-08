package com.indas.portal.security.repositories;

import com.indas.portal.repositories.BaseRepository;
import com.indas.portal.security.entities.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional("securityTM")
public class RoleRepository extends BaseRepository<Role> {

    @PersistenceContext(unitName ="securityEntityManagerFactory")
    protected EntityManager em;

    @Override
    public Class<Role> getClassEntity() {
        return Role.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
