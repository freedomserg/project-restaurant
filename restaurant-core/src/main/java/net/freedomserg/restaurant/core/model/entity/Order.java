package net.freedomserg.restaurant.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordering")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId")
public class Order {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "waiter_id")
    private Waiter waiter;

    @Column(name = "table_number")
    private int tableNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dish_to_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_state")
    @Enumerated(EnumType.STRING)
    private OrderState state;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (tableNumber != order.tableNumber) return false;
        if (waiter != null ? !waiter.equals(order.waiter) : order.waiter != null) return false;
        if (dishes != null ? !dishes.equals(order.dishes) : order.dishes != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        return state == order.state;

    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (waiter != null ? waiter.hashCode() : 0);
        result = 31 * result + tableNumber;
        result = 31 * result + (dishes != null ? dishes.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order{");
        builder.append("orderId=").append(orderId);
        builder.append(", tableNumber=").append(tableNumber);
        builder.append(", orderDate=").append(new SimpleDateFormat("dd/MM/yyyy").format(orderDate));
        builder.append(", dishes:").append("\n");
        dishes.forEach(dish -> builder.append(dish).append("\n"));
        builder.append(", state=").append(state);
        builder.append("}");
        return builder.toString();
    }
}
