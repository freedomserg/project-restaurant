package net.freedomserg.restaurant.core.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "store",
        uniqueConstraints = {@UniqueConstraint(columnNames={"ingredient_id"})})
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "ingredient_id")
    @NotNull
    private Ingredient ingredient;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status = Status.ACTUAL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
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
        if (quantity != store.quantity) return false;
        return ingredient != null ? ingredient.equals(store.ingredient) : store.ingredient == null;

    }

    @Override
    public int hashCode() {
        int result = ingredient != null ? ingredient.hashCode() : 0;
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
