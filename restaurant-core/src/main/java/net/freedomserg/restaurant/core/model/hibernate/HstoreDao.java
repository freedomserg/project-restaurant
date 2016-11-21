package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.dao.StoreDao;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.entity.Store;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HstoreDao implements StoreDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Store store) {
        return (Integer) sessionFactory.getCurrentSession().save(store);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Store store) {
        store.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(store);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Store store) {
        sessionFactory.getCurrentSession().update(store);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Store loadByIngredient(Ingredient ingredient) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT s FROM Store s WHERE s.ingredient.ingredientId = :id AND s.status = :status");
        query.setParameter("id", ingredient.getIngredientId());
        query.setParameter("status", Status.ACTUAL);
        return (Store)query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Store loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT s FROM Store s WHERE s.id = :id AND s.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", Status.ACTUAL);
        return (Store) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Store> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT s FROM Store s WHERE s.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
