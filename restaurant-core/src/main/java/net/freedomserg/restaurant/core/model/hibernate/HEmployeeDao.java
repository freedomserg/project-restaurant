package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.EmployeeDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HEmployeeDao implements EmployeeDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Employee employee) {
        sessionFactory.getCurrentSession().delete(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee loadByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery
                ("SELECT e FROM Employee e WHERE e.name like :name");
        query.setParameter("name", name);
        return (Employee) query.getSingleResult();
    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT e FROM Employee e").list();
    }
}
