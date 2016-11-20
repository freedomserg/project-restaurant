package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.DishUnit;
import net.freedomserg.restaurant.core.model.entity.Ingredient;

import java.util.List;

public interface DishUnitDao {

    Integer save(DishUnit dishUnit);

    void remove(DishUnit dishUnit);

    void update(DishUnit dishUnit);

    DishUnit load(Dish dish, Ingredient ingredient);

    List<DishUnit> loadAll();
}
