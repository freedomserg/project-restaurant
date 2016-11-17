package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.dao.StoreDao;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.entity.Store;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HstoreDao implements StoreDao {

    private SessionFactory sessionFactory;
    private IngredientDao ingredientDao;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Store store) {
        sessionFactory.getCurrentSession().save(store);
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
    public Store loadByName(String name) {
        int ingredientId = ingredientDao.loadByName(name).getIngredientId();
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT s FROM Store s WHERE s.ingredientId = :id AND s.status = :status");
        query.setParameter("id", ingredientId);
        query.setParameter("status", Status.ACTUAL);
        return (Store)query.getSingleResult();
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
