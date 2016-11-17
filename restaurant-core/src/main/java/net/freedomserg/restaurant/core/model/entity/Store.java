package net.freedomserg.restaurant.core.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "store")
public class Store {

    @Id
    @Column(name = "ingredient_id")
    private int ingredientId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        if (ingredientId != store.ingredientId) return false;
        return quantity == store.quantity;

    }

    @Override
    public int hashCode() {
        int result = ingredientId;
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "Store{" +
                "ingredientId=" + ingredientId +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
