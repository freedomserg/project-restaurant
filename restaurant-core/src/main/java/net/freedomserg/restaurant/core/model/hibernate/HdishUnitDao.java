package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.DishDao;
import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.DishUnit;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.exception.NoSuchEntityRestaurantException;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HdishUnitDao implements DishUnitDao {

    private SessionFactory sessionFactory;

    private DishDao dishDao;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(DishUnit dishUnit) {
        try {
            DishUnit target = load(dishUnit.getDish(), dishUnit.getIngredient());
            throw new SuchEntityAlreadyExistsRestaurantException
                        ("Such dishUnit already exists! " +
                                "Dish: id: " + dishUnit.getDish().getDishId() +
                                        " name: " + dishUnit.getDish().getDishName() +
                                " Ingredient: id: " + dishUnit.getIngredient().getIngredientId() +
                                    " name: " + dishUnit.getIngredient().getIngredientName());
        } catch (NoSuchEntityRestaurantException ex) {
            return (Integer) sessionFactory.getCurrentSession().save(dishUnit);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(DishUnit dishUnit) {
        Dish dish = dishUnit.getDish();
        List<DishUnit> currentUnits = dish.getUnits();
        List<DishUnit> updatedUnits = new ArrayList<>();
        for (DishUnit currentUnit : currentUnits) {
            if (!dishUnit.equals(currentUnit)) {
                updatedUnits.add(currentUnit);
            }
        }
        dish.setUnits(updatedUnits);
        dishDao.update(dish);
        sessionFactory.getCurrentSession().remove(dishUnit);
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
                        "WHERE du.dish.id = :dish AND du.ingredient.id = :ingredient");
        query.setParameter("dish", dish.getDishId());
        query.setParameter("ingredient", ingredient.getIngredientId());
        DishUnit dishUnit;
        try{
            dishUnit = (DishUnit) query.getSingleResult();
        }catch (NoResultException ex) {
            throw new NoSuchEntityRestaurantException
                    ("No existing dishUnit with dish = " + dish.getDishName() +
                            " and ingredient = " + ingredient.getIngredientName());
        }
        return dishUnit;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public DishUnit loadById(int id) {
        DishUnit dishUnit;
        try{
            dishUnit = sessionFactory.getCurrentSession().load(DishUnit.class, id);
        } catch (ObjectNotFoundException ex) {
            throw new NoSuchEntityRestaurantException("No existing dishUnit with id = " + id);
        }
        return dishUnit;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<DishUnit> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT du FROm DishUnit du");
        return query.getResultList();
    }
}
