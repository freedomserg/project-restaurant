package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Dish;

import java.util.List;

public interface DishDao {

    Integer save(Dish dish);

    void remove(Dish dish);

    void update(Dish dish);

    Dish loadByName(String name);

    Dish loadById(int id);

    List<Dish> loadAll();
}
