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
    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    @Transactional
    public void deleteEmployee(Employee employee) {
        employeeDao.remove(employee);
    }

    @Transactional
    public void deleteEmployeeById(int id) {
        employeeDao.removeEmployeeById(id);
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
        return employeeDao.findAll();
    }

    @Transactional
    public void printEmployees() {
        getAll().forEach(System.out::println);
    }
}
