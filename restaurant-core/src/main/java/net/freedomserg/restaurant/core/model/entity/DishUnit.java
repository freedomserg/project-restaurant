package net.freedomserg.restaurant.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "dish_unit",
        uniqueConstraints = {@UniqueConstraint(columnNames={"dish_id", "ingredient_id"})})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class DishUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    @NotNull
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    @NotNull
    private Ingredient ingredient;

    @Column(name = "ingredient_quan")
    @NotNull
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

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishUnit dishUnit = (DishUnit) o;
        if (quantity != dishUnit.quantity) return false;
        if (dish != null ? !dish.equals(dishUnit.dish) : dishUnit.dish != null) return false;
        return ingredient != null ? ingredient.equals(dishUnit.ingredient) : dishUnit.ingredient == null;

    }

    @Override
    public int hashCode() {
        int result = dish != null ? dish.hashCode() : 0;
        result = 31 * result + (ingredient != null ? ingredient.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DishUnit{");
        builder.append("id=").append(id);
        builder.append(", ingredient=").append(ingredient);
        builder.append(", quantity=").append(quantity);
        builder.append(", status=").append(status);
        builder.append("}");
        return builder.toString();
    }

}
