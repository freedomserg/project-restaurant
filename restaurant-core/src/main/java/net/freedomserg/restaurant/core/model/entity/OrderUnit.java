package net.freedomserg.restaurant.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "order_unit")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class OrderUnit {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "dish_quan")
    private int quantity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTUAL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderUnit orderUnit = (OrderUnit) o;
        if (quantity != orderUnit.quantity) return false;
        if (order != null ? !order.equals(orderUnit.order) : orderUnit.order != null) return false;
        return dish != null ? dish.equals(orderUnit.dish) : orderUnit.dish == null;

    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (dish != null ? dish.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "OrderUnit{" +
                "id=" + id +
                ", dish=" + dish +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}