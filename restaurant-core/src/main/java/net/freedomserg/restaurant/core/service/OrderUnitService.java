package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.OrderUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.Order;
import net.freedomserg.restaurant.core.model.entity.OrderUnit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderUnitService {

    private OrderUnitDao orderUnitDao;

    public void setOrderUnitDao(OrderUnitDao orderUnitDao) {
        this.orderUnitDao = orderUnitDao;
    }

    @Transactional
    public Integer add(OrderUnit orderUnit) {
        return orderUnitDao.save(orderUnit);
    }

    @Transactional
    public void delete(OrderUnit orderUnit) {
        orderUnitDao.remove(orderUnit);
    }

    @Transactional
    public void update(OrderUnit orderUnit) {
        orderUnitDao.update(orderUnit);
    }

    @Transactional
    public OrderUnit get(Order order, Dish dish) {
        return orderUnitDao.load(order, dish);
    }

    @Transactional
    public OrderUnit getById(int id) {
        return orderUnitDao.loadById(id);
    }

    @Transactional
    public List<OrderUnit> getAll() {
        return orderUnitDao.loadAll();
    }
}
