package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.EmployeeDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Waiter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeService {

    private EmployeeDao employeeDao;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Transactional
    public Integer add(Employee employee) {
        return employeeDao.save(employee);
    }

    @Transactional
    public void delete(Employee employee) {
        employeeDao.remove(employee);
    }

    @Transactional
    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    @Transactional
    public Employee getByName(String name) {
        return employeeDao.loadByName(name);
    }

    @Transactional
    public Employee getById(int id) {
        return employeeDao.loadById(id);
    }

    @Transactional
    public List<Employee> getAll() {
        return employeeDao.loadAll();
    }

    @Transactional
    public Waiter getWaiterByName(String name) {
        return employeeDao.loadWaiterByName(name);
    }

    @Transactional
    public Waiter getWaiterById(int id) {
        return employeeDao.loadWaiterById(id);
    }

    @Transactional
    public List<Waiter> getAllWaiters() {
        return employeeDao.loadAllWaiters();
    }
}
