package com.indas.portal.security.repositories;

import com.indas.portal.repositories.BaseRepository;
import com.indas.portal.security.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static java.util.Objects.*;

@Repository
@Transactional("securityTM")
public class UserRepository extends BaseRepository<User> {

    private final Class<User> entityClass = User.class;

    @PersistenceContext(unitName = "securityEntityManagerFactory")
    protected EntityManager em;

    public User findByLogin(String login) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> urlCriteria = cb.createQuery(entityClass);
        Root<User> urlRoot = urlCriteria.from(User.class);
        urlCriteria.where(cb.like(urlRoot.get("login"), login));
        urlCriteria.select(urlRoot);
        return em.createQuery(urlCriteria).getSingleResult();
    }

    public User save(User user) {
        if (super.isNew(user)) {
            em.persist(user);
            em.flush();
            return user;
        } else {
            return em.merge(
                    merge(em.find(entityClass, user.getId()), user)
            );
        }
    }

    public User merge(User target, User user) {
        if (nonNull(user.getLogin()) && nonNull(target.getLogin())) {
            target.setLogin(requireNonNullElse(user.getLogin(), target.getLogin()));
        }
        if (nonNull(user.getFio()) && nonNull(target.getFio())) {
            target.setFio(requireNonNullElse(user.getFio(), target.getFio()));
        }
        if (nonNull(user.getRoles()) && nonNull(target.getRoles())) {
            target.setRoles(requireNonNullElse(user.getRoles(), target.getRoles()));
        }
        if (nonNull(user.getPassword()) && !user.getPassword().isEmpty() && nonNull(target.getPassword())) {
            target.setPassword(requireNonNullElse(user.getPassword(), target.getPassword()));
        }
        if (nonNull(user.getEmplId()) && nonNull(target.getEmplId())) {
            target.setEmplId(requireNonNullElse(user.getEmplId(), target.getEmplId()));
        }
        if (nonNull(user.getExpiration()) || nonNull(target.getExpiration())) {
            target.setExpiration(requireNonNullElse(user.getExpiration(), target.getExpiration()));
        }
        return target;
    }

    @Override
    public Class<User> getClassEntity() {
        return User.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private boolean isNew(int id) {
        return em.find(entityClass, id) == null;
    }

//    public interface UserJpaRepository extends JpaRepository<User, Integer> {
//        User findByLogin(String login);
//    }
}
