package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.Order;
import net.freedomserg.restaurant.core.model.entity.OrderUnit;

import java.util.List;

public interface OrderUnitDao {

    void save(OrderUnit orderUnit);

    void remove(OrderUnit orderUnit);

    void update(OrderUnit orderUnit);

    OrderUnit load(Order order, Dish dish);

    List<OrderUnit> loadAll();
}
