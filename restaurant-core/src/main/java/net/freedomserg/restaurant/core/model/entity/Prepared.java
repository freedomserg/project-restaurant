package net.freedomserg.restaurant.core.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "prepared")
public class Prepared implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "prepared_id")
    private int preparedId;

    @Column(name = "dish_id")
    private int dishId;

    @Column(name = "cook_id")
    private int cookId;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "preparation_date")
    private Date preparationDate;

    public int getPreparedId() {
        return preparedId;
    }

    public void setPreparedId(int preparedId) {
        this.preparedId = preparedId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getCookId() {
        return cookId;
    }

    public void setCookId(int cookId) {
        this.cookId = cookId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getPreparationDate() {
        return preparationDate;
    }

    public void setPreparationDate(Date preparationDate) {
        this.preparationDate = preparationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prepared prepared = (Prepared) o;
        if (preparedId != prepared.preparedId) return false;
        if (dishId != prepared.dishId) return false;
        if (cookId != prepared.cookId) return false;
        if (orderId != prepared.orderId) return false;
        return preparationDate != null ? preparationDate.equals(prepared.preparationDate) : prepared.preparationDate == null;

    }

    @Override
    public int hashCode() {
        int result = preparedId;
        result = 31 * result + dishId;
        result = 31 * result + cookId;
        result = 31 * result + orderId;
        result = 31 * result + (preparationDate != null ? preparationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Prepared{" +
                "preparedId=" + preparedId +
                ", dishId=" + dishId +
                ", cookId=" + cookId +
                ", orderId=" + orderId +
                ", preparationDate=" + preparationDate +
                '}';
    }
}
