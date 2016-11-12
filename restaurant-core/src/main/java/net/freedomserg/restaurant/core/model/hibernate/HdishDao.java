package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.DishDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HdishDao implements DishDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Dish dish) {
        sessionFactory.getCurrentSession().save(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Dish dish) {
        sessionFactory.getCurrentSession().delete(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Dish dish) {
        sessionFactory.getCurrentSession().update(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish loadByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT d FROM Dish d WHERE d.dishName like :name");
        query.setParameter("name", name);
        return (Dish)query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT d FROM Dish d").list();
    }
}
