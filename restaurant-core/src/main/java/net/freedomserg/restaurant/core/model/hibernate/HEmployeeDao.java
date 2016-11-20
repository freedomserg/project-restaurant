package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.EmployeeDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Status;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        return (Employee) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee loadById(int id) {
        return sessionFactory.getCurrentSession().load(Employee.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT e FROM Employee e WHERE e.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
