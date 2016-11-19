package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.OrderUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.Order;
import net.freedomserg.restaurant.core.model.entity.OrderUnit;
import net.freedomserg.restaurant.core.model.entity.Status;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class HorderUnitDao implements OrderUnitDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(OrderUnit orderUnit) {
        sessionFactory.getCurrentSession().save(orderUnit);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(OrderUnit orderUnit) {
        orderUnit.setStatus(Status.DELETED);
        sessionFactory.getCurrentSession().update(orderUnit);
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
                        "WHERE ou.order.id = :orderId AND ou.dish.id = :dishId AND ou.status = :status");
        query.setParameter("orderId", order.getOrderId());
        query.setParameter("dishId", dish.getDishId());
        query.setParameter("status", Status.ACTUAL);
        return (OrderUnit) query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<OrderUnit> loadAll() {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT ou FROM OrderUnit ou WHERE ou.status = :status");
        query.setParameter("status", Status.ACTUAL);
        return query.getResultList();
    }
}
