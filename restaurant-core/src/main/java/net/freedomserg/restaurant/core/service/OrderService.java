package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.OrderDao;
import net.freedomserg.restaurant.core.model.entity.Order;
import net.freedomserg.restaurant.core.model.entity.Waiter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderService {

    private OrderDao orderDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional
    public Integer add(Order order) {
        return orderDao.save(order);
    }

    @Transactional
    public void delete(Order order) {
        orderDao.remove(order);
    }

    @Transactional
    public void update(Order order) {
        orderDao.update(order);
    }

    @Transactional
    public Order getById(int id) {
        return orderDao.loadById(id);
    }

    @Transactional
    public List<Order> getAll() {
        return orderDao.loadAll();
    }

    @Transactional
    public List<Order> getByDate(String date) {
        return orderDao.loadByDate(date);
    }

    @Transactional
    public List<Order> getByWaiter(Waiter waiter) {
        return orderDao.loadByWaiter(waiter);
    }

    @Transactional
    public List<Order> getByTable(int tableNumber) {
        return orderDao.loadByTable(tableNumber);
    }
}
