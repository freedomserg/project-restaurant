package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Order;
import net.freedomserg.restaurant.core.model.entity.Waiter;

import java.util.List;

public interface OrderDao {

    Integer save(Order order);

    void remove(Order order);

    void update(Order order);

    Order loadById(int id);

    List<Order> loadAll();

    List<Order> loadByDate(String date);

    List<Order> loadByWaiter(Waiter waiter);

    List<Order> loadByTable(int tableNumber);
}
