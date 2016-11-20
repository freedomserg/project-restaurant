package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    Integer save(Employee employee);

    void remove(Employee employee);

    Employee loadByName(String name);

    void update(Employee employee);

    Employee loadById(int id);

    List<Employee> loadAll();
}
