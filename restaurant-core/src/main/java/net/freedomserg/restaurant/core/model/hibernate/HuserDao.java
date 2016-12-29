package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.UserDao;
import net.freedomserg.restaurant.core.model.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

public class HuserDao implements UserDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public User findById(int id) {
        return sessionFactory.getCurrentSession().load(User.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public User findBySso(String sso) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT u FROM User u WHERE u.ssoId like :ssoId");
        query.setParameter("ssoId", sso);
        return (User)query.getSingleResult();
    }
}
