package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.DishUnit;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class HdishUnitDao implements DishUnitDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(DishUnit dishUnit) {
        DishUnit target = load(dishUnit.getDish(), dishUnit.getIngredient());
        if (target != null) {
            throw new SuchEntityAlreadyExistsRestaurantException
                    ("DishUnit entity with such Dish and Ingredient already exists!");
        }
        return (Integer) sessionFactory.getCurrentSession().save(dishUnit);
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
        DishUnit dishUnit;
        try{
            dishUnit = (DishUnit) query.getSingleResult();
        }catch (NoResultException ex) {
            return null;
        }
        return dishUnit;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public DishUnit loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT du FROM DishUnit du WHERE du.id = :id AND du.status = :status");
        query.setParameter("id", id);
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
