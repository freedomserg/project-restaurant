package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.CategoryDao;
import net.freedomserg.restaurant.core.model.entity.Category;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.exception.NoSuchEntityRestaurantException;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class HcategoryDao implements CategoryDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Category category) {
        try{
            Category target = loadByName(category.getCategoryName());
            throw new SuchEntityAlreadyExistsRestaurantException
                    ("Such category already exists! " +
                            "id: " + category.getCategoryId() +
                            "name: " + category.getCategoryName());
        } catch (NoSuchEntityRestaurantException ex) {
            return (Integer) sessionFactory.getCurrentSession().save(category);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Category category) {
        category.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(category);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Category category) {
        sessionFactory.getCurrentSession().update(category);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Category loadByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT c FROM Category c WHERE c.categoryName like :name AND c.status = :status");
        query.setParameter("name", name);
        query.setParameter("status", Status.ACTUAL);
        Category category;
        try{
            category = (Category)query.getSingleResult();
        }catch (NoResultException ex) {
            throw new NoSuchEntityRestaurantException("No existing category with name = " + name);
        }
        return category;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Category loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT c FROM Category c WHERE c.categoryId = :id AND c.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", Status.ACTUAL);
        Category category;
        try{
            category = (Category) query.getSingleResult();
        }catch (NoResultException ex) {
            throw new NoSuchEntityRestaurantException("No existing category with id = " + id);
        }
        return category;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Category> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT c FROM Category c WHERE c.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
