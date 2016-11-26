package net.freedomserg.restaurant.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordering")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "orderId")
@Proxy(lazy = false)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @OneToMany(mappedBy = "order",
                fetch = FetchType.EAGER,
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<OrderUnit> units;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status = OrderStatus.OPENED;

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

    public List<OrderUnit> getUnits() {
        return units;
    }

    public void setUnits(List<OrderUnit> units) {
        this.units = units;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (orderId != order.orderId) return false;
        if (tableNumber != order.tableNumber) return false;
        if (waiter != null ? !waiter.equals(order.waiter) : order.waiter != null) return false;
        if (units != null ? !units.equals(order.units) : order.units != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        return status == order.status;

    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (waiter != null ? waiter.hashCode() : 0);
        result = 31 * result + tableNumber;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order{");
        builder.append("orderId=").append(orderId);
        builder.append(", tableNumber=").append(tableNumber);
        builder.append(", orderDate=").append(new SimpleDateFormat("dd/MM/yyyy").format(orderDate));
        builder.append(", units:").append("\n");
        if (units != null) {
            units.forEach(dish -> builder.append(dish).append("\n"));
        }
        builder.append(", status=").append(status);
        builder.append("}");
        return builder.toString();
    }
}
