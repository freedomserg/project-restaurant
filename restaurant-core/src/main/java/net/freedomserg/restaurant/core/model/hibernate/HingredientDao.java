package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        return (Integer) sessionFactory.getCurrentSession().save(ingredient);
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
        return (Ingredient) query.getSingleResult();
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
