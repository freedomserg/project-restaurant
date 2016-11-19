package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.OrderDao;
import net.freedomserg.restaurant.core.model.entity.Order;
import net.freedomserg.restaurant.core.model.entity.OrderStatus;
import net.freedomserg.restaurant.core.model.entity.Waiter;
import net.freedomserg.restaurant.core.model.exception.InvalidOrderDateRestaurantException;
import net.freedomserg.restaurant.core.model.exception.IllegalOperationRestaurantException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class HorderDao implements OrderDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Integer save(Order order) {
        return (Integer) sessionFactory.getCurrentSession().save(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(Order order) {
        if (order.getStatus().equals(OrderStatus.CLOSED)) {
            throw new IllegalOperationRestaurantException("Order is closed! Cannot remove or modify.");
        }
        sessionFactory.getCurrentSession().delete(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Order order) {
        if (order.getStatus().equals(OrderStatus.CLOSED)) {
            throw new IllegalOperationRestaurantException("Order is closed! Cannot remove or modify.");
        }
        sessionFactory.getCurrentSession().update(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Order loadById(int id) {
        return sessionFactory.getCurrentSession().load(Order.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order o").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> loadByDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date inputDate;
        try {
            inputDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new InvalidOrderDateRestaurantException("Invalid input date format!");
        }
        Date sqlDate = new Date(inputDate.getTime());
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT o FROM Order o WHERE o.orderDate = :date");
        query.setParameter("date", sqlDate);
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> loadByWaiter(Waiter waiter) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT o FROM Order o WHERE o.waiter = :waiter");
        query.setParameter("waiter", waiter);
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> loadByTable(int tableNumber) {
        Query query = sessionFactory.getCurrentSession().createQuery
                ("SELECT o FROM Order o WHERE o.tableNumber = :tableNumber");
        query.setParameter("tableNumber", tableNumber);
        return query.getResultList();
    }
}
