package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    void saveEmployee(Employee employee);

    void removeEmployee(Employee employee);

    Employee loadByName(String name);

    Employee updateEmployee(Employee employee);

    List<Employee> findAll();
}
