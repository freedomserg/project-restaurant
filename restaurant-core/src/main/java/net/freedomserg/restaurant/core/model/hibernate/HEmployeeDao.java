package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.EmployeeDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.entity.Waiter;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class HemployeeDao implements EmployeeDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Employee employee) {
        Employee target = loadByName(employee.getName());
        if (target != null) {
            throw new SuchEntityAlreadyExistsRestaurantException
                    ("Employee with such name already exists!");
        }
        return (Integer) sessionFactory.getCurrentSession().save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Employee employee) {
        employee.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee loadByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT e FROM Employee e WHERE e.name like :name AND e.status = :status");
        query.setParameter("name", name);
        query.setParameter("status", Status.ACTUAL);
        Employee employee;
        try{
            employee = (Employee) query.getSingleResult();
        }catch (NoResultException ex) {
            return null;
        }
        return employee;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee loadById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT e FROM Employee e WHERE e.id = :id AND e.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", Status.ACTUAL);
        return (Employee) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT e FROM Employee e WHERE e.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Waiter loadWaiterByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT e FROM Employee e WHERE e.name like :name AND type(e) = :etype AND e.status = :status");
        query.setParameter("name", name);
        query.setParameter("etype", Waiter.class);
        query.setParameter("status", Status.ACTUAL);
        return (Waiter) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Waiter loadWaiterById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT e FROM Employee e WHERE e.id = :id AND type(e) = :etype AND e.status = :status");
        query.setParameter("id", id);
        query.setParameter("etype", Waiter.class);
        query.setParameter("status", Status.ACTUAL);
        return (Waiter) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Waiter> loadAllWaiters() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT e FROM Employee e WHERE type(e) = :etype AND e.status = :status");
        query.setParameter("etype", Waiter.class);
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
