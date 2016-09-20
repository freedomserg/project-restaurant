package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.EmployeeDao;
import net.freedomserg.restaurant.core.model.entity.Employee;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeService {

    private EmployeeDao employeeDao;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void addEmployee(Employee employee) {
        employeeDao.saveEmployee(employee);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteEmployee(Employee employee) {
        employeeDao.removeEmployee(employee);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteEmployeeById(int id) {
        employeeDao.removeEmployeeById(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getByName(String name) {
        return employeeDao.loadByName(name);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getById(int id) {
        return employeeDao.loadById(id);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getAll() {
        return employeeDao.findAll();
    }
}
