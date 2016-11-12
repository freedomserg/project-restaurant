package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.CategoryDao;
import net.freedomserg.restaurant.core.model.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HcategoryDao implements CategoryDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Category category) {
        sessionFactory.getCurrentSession().save(category);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Category category) {
        sessionFactory.getCurrentSession().delete(category);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Category category) {
        sessionFactory.getCurrentSession().update(category);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Category loadByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT c FROM Category c WHERE c.categoryName like :name");
        query.setParameter("name", name);
        return (Category)query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Category> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT c FROM Category c").list();
    }
}
