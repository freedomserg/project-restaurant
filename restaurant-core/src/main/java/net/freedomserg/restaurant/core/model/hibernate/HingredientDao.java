package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
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
    public void save(Ingredient ingredient) {
        sessionFactory.getCurrentSession().save(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Ingredient ingredient) {
        sessionFactory.getCurrentSession().delete(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Ingredient ingredient) {
        sessionFactory.getCurrentSession().update(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient loadByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery
                ("SELECT i FROM Ingredient i WHERE i.ingredientName like :name");
        query.setParameter("name", name);
        return (Ingredient) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Ingredient> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT i FROM Ingredient i").list();
    }
}