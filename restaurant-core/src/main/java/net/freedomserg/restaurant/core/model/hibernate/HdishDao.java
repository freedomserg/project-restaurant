package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.DishDao;
import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.DishUnit;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class HdishDao implements DishDao {

    private SessionFactory sessionFactory;
    private DishUnitDao dishUnitDao;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDishUnitDao(DishUnitDao dishUnitDao) {
        this.dishUnitDao = dishUnitDao;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Dish dish) {
        Dish target = loadByName(dish.getDishName());
        if (target != null) {
            throw new SuchEntityAlreadyExistsRestaurantException
                    ("Dish with such name already exists!");
        }
        return (Integer) sessionFactory.getCurrentSession().save(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Dish dish) {
        dish.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(dish);
        List<DishUnit> units = dish.getUnits();
        for (DishUnit unit : units) {
            dishUnitDao.remove(unit);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Dish dish) {
        sessionFactory.getCurrentSession().update(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish loadByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT d FROM Dish d WHERE d.dishName like :name AND d.status = :status");
        query.setParameter("name", name);
        query.setParameter("status", Status.ACTUAL);
        Dish dish;
        try{
            dish = (Dish)query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return dish;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT d FROM Dish d WHERE d.dishId = :id AND d.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", Status.ACTUAL);
        return (Dish) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT d FROM Dish d WHERE d.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
