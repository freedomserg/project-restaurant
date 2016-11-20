package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Waiter;

import java.util.List;

public interface EmployeeDao {

    Integer save(Employee employee);

    void remove(Employee employee);

    void update(Employee employee);

    Employee loadByName(String name);

    Employee loadById(int id);

    List<Employee> loadAll();

    Waiter loadWaiterByName(String name);

    Waiter loadWaiterById(int id);

    List<Waiter> loadAllWaiters();
}
