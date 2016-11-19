package net.freedomserg.restaurant.core.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "dish_unit")
public class DishUnit {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "ingredient_quan")
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
        DishUnit dishUnit = (DishUnit) o;
        if (id != dishUnit.id) return false;
        if (quantity != dishUnit.quantity) return false;
        return ingredient != null ? ingredient.equals(dishUnit.ingredient) : dishUnit.ingredient == null;

    }

    @Override
    public int hashCode() {
        int result = id;
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
