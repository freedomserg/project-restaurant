package net.freedomserg.restaurant.core.model.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "menu",
        uniqueConstraints = {@UniqueConstraint(columnNames={"menu_name"})})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "menu_id")
    private int menuId;

    @Column(name = "menu_name")
    @NotNull
    private String menuName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dish_to_menu",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status = Status.ACTUAL;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
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
        Menu menu = (Menu) o;
        if (menuName != null ? !menuName.equals(menu.menuName) : menu.menuName != null) return false;
        return dishes != null ? dishes.equals(menu.dishes) : menu.dishes == null;

    }

    @Override
    public int hashCode() {
        int result = menuName != null ? menuName.hashCode() : 0;
        result = 31 * result + (dishes != null ? dishes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Menu{");
        builder.append("menuId=").append(menuId);
        builder.append(", menuName=").append(menuName);
        builder.append(", dishes:").append("\n");
        dishes.forEach(dish -> builder.append(dish).append("\n"));
        builder.append(", status=").append(status);
        builder.append("}");
        return builder.toString();
    }
}
