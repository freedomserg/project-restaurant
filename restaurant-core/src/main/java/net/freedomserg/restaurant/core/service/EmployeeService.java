package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.EmployeeDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeService {

    private EmployeeDao employeeDao;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Transactional
    public void add(Employee employee) {
        employeeDao.save(employee);
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
    public List<Employee> getAll() {
        return employeeDao.loadAll();
    }
}
