package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.MenuDao;
import net.freedomserg.restaurant.core.model.entity.Menu;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class HmenuDao implements MenuDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Menu menu) {
        Menu target = loadByName(menu.getMenuName());
        if (target != null) {
            throw new SuchEntityAlreadyExistsRestaurantException
                    ("Menu with such name already exists!");
        }
        return (Integer) sessionFactory.getCurrentSession().save(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Menu menu) {
        menu.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Menu menu) {
        sessionFactory.getCurrentSession().update(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu loadByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT m FROM Menu m WHERE m.menuName like :name AND m.status = :status");
        query.setParameter("name", name);
        query.setParameter("status", Status.ACTUAL);
        Menu menu;
        try{
            menu = (Menu)query.getSingleResult();
        }catch (NoResultException ex) {
            return null;
        }
        return menu;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT m FROM Menu m WHERE m.menuId = :id AND m.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", Status.ACTUAL);
        return (Menu) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Menu> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT m FROM Menu m WHERE m.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
