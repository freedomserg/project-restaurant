package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.DishUnit;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Status;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HdishUnitDao implements DishUnitDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(DishUnit dishUnit) {
        sessionFactory.getCurrentSession().save(dishUnit);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(DishUnit dishUnit) {
        dishUnit.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(dishUnit);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(DishUnit dishUnit) {
        sessionFactory.getCurrentSession().update(dishUnit);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public DishUnit load(Dish dish, Ingredient ingredient) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT du FROM DishUnit du " +
                        "WHERE du.dish.id = :dish AND du.ingredient.id = :ingredient AND du.status = :status");
        query.setParameter("dish", dish.getDishId());
        query.setParameter("ingredient", ingredient.getIngredientId());
        query.setParameter("status", Status.ACTUAL);
        return (DishUnit) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<DishUnit> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT du FROm DishUnit du WHERE du.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
