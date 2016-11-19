package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
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
        Ingredient ingredient = dishUnit.getIngredient();
        int quantity = dishUnit.getQuantity();
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT du FROM DishUnit du WHERE du.ingredient.id = :ingredient AND du.quantity = :quantity");
        query.setParameter("ingredient", ingredient.getIngredientId());
        query.setParameter("quantity", quantity);
        List<DishUnit> dishUnits = query.getResultList();
        if (dishUnits.isEmpty()) {
            sessionFactory.getCurrentSession().save(dishUnit);
        }
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
    public DishUnit loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT du FROM DishUnit du WHERE du.id = :id AND du.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", Status.ACTUAL);
        return (DishUnit) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public DishUnit load(Ingredient ingredient, int quantity) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT du FROM DishUnit du " +
                        "WHERE du.ingredient.id = :ingredient AND du.quantity = :quantity AND du.status = :status");
        query.setParameter("ingredient", ingredient.getIngredientId());
        query.setParameter("quantity", quantity);
        query.setParameter("status", Status.ACTUAL);
        return (DishUnit)query.getResultList().get(0);
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
