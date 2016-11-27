package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.OrderDao;
import net.freedomserg.restaurant.core.model.dao.OrderUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.Order;
import net.freedomserg.restaurant.core.model.entity.OrderUnit;
import net.freedomserg.restaurant.core.model.exception.NoSuchEntityRestaurantException;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HorderUnitDao implements OrderUnitDao {

    private SessionFactory sessionFactory;

    private OrderDao orderDao;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(OrderUnit orderUnit) {
        try {
            OrderUnit target = load(orderUnit.getOrder(), orderUnit.getDish());
            throw new SuchEntityAlreadyExistsRestaurantException
                        ("Such orderUnit already exists! orderId: " + orderUnit.getOrder().getOrderId() +
                                " dish: id: " + orderUnit.getDish().getDishId() +
                                        " name: " + orderUnit.getDish().getDishName());
        } catch (NoSuchEntityRestaurantException ex) {
            return (Integer) sessionFactory.getCurrentSession().save(orderUnit);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(OrderUnit orderUnit) {
        Order order = orderUnit.getOrder();
        List<OrderUnit> currentUnits = order.getUnits();
        List<OrderUnit> updatedUnits = new ArrayList<>();
        for (OrderUnit currentuUnit : currentUnits) {
            if (!currentuUnit.equals(orderUnit)) {
                updatedUnits.add(currentuUnit);
            }
        }
        order.setUnits(updatedUnits);
        orderDao.update(order);
        sessionFactory.getCurrentSession().remove(orderUnit);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(OrderUnit orderUnit) {
        sessionFactory.getCurrentSession().update(orderUnit);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public OrderUnit load(Order order, Dish dish) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT ou FROM OrderUnit ou " +
                        "WHERE ou.order.id = :orderId AND ou.dish.id = :dishId");
        query.setParameter("orderId", order.getOrderId());
        query.setParameter("dishId", dish.getDishId());
        OrderUnit orderUnit;
        try{
            orderUnit = (OrderUnit) query.getSingleResult();
        }catch (NoResultException ex) {
            throw new NoSuchEntityRestaurantException
                    ("No existing orderUnit with orderId = " + order.getOrderId() +
                            " and dish = " + dish.getDishName());
        }
        return orderUnit;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public OrderUnit loadById(int id) {
        OrderUnit orderUnit;
        try{
            orderUnit = sessionFactory.getCurrentSession().load(OrderUnit.class, id);
        } catch (ObjectNotFoundException ex) {
            throw new NoSuchEntityRestaurantException("No existing orderUnit with id = " + id);
        }
        return orderUnit;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<OrderUnit> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT ou FROM OrderUnit ou");
        return query.getResultList();
    }
}
