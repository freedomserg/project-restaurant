package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.exception.NoSuchEntityRestaurantException;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class HingredientDao implements IngredientDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Ingredient ingredient) {
        try {
            Ingredient target = loadByName(ingredient.getIngredientName());
            throw new SuchEntityAlreadyExistsRestaurantException
                        ("Such ingredient already exists! " +
                                "id: " + ingredient.getIngredientId() +
                                " name: " + ingredient.getIngredientName());
        } catch (NoSuchEntityRestaurantException ex) {
            return (Integer) sessionFactory.getCurrentSession().save(ingredient);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Ingredient ingredient) {
        ingredient.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Ingredient ingredient) {
        sessionFactory.getCurrentSession().update(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient loadByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT i FROM Ingredient i WHERE i.ingredientName like :name AND i.status = :status");
        query.setParameter("name", name);
        query.setParameter("status", Status.ACTUAL);
        Ingredient ingredient;
        try{
            ingredient = (Ingredient) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NoSuchEntityRestaurantException("No existing ingredient with name = " + name);
        }
        return ingredient;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT i FROM Ingredient i WHERE i.ingredientId = :id AND i.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", Status.ACTUAL);
        Ingredient ingredient;
        try{
            ingredient = (Ingredient) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NoSuchEntityRestaurantException("No existing ingredient with id = " + id);
        }
        return ingredient;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Ingredient> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT i FROM Ingredient i WHERE i.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
