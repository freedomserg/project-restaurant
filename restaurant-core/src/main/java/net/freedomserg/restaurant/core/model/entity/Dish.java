package net.freedomserg.restaurant.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "dish")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "dishId")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "dish_id")
    private int dishId;

    @Column(name = "dish_name")
    private String dishName;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @OneToMany(mappedBy = "dish", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<DishUnit> units;

    @Column(name = "price")
    private double price;

    @Column(name = "weight")
    private int weight;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTUAL;

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<DishUnit> getUnits() {
        return units;
    }

    public void setUnits(List<DishUnit> units) {
        this.units = units;
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
        Dish dish = (Dish) o;
        if (Double.compare(dish.price, price) != 0) return false;
        if (weight != dish.weight) return false;
        if (dishName != null ? !dishName.equals(dish.dishName) : dish.dishName != null) return false;
        if (category != null ? !category.equals(dish.category) : dish.category != null) return false;
        return units != null ? units.equals(dish.units) : dish.units == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = dishName != null ? dishName.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (units != null ? units.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Dish{");
        builder.append("dishId=").append(dishId);
        builder.append(", dishName=").append(dishName);
        builder.append(", category=").append(category);
        builder.append(", units:").append("\n");
        units.forEach(ingredient -> builder.append(ingredient).append("\n"));
        builder.append(", price=").append(price);
        builder.append(", weight=").append(weight);
        builder.append(", status=").append(status);
        builder.append("}");
        return builder.toString();
    }
}
