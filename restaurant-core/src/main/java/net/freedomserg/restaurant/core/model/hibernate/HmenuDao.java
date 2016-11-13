package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.MenuDao;
import net.freedomserg.restaurant.core.model.entity.Menu;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HmenuDao implements MenuDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Menu menu) {
        sessionFactory.getCurrentSession().save(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Menu menu) {
        sessionFactory.getCurrentSession().delete(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Menu menu) {
        sessionFactory.getCurrentSession().update(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu loadByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT m FROM Menu m WHERE m.menuName like :name");
        query.setParameter("name", name);
        return (Menu)query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Menu> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT m FROM Menu m").list();
    }
}