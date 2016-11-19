package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.DishUnit;
import net.freedomserg.restaurant.core.model.entity.Ingredient;

import java.util.List;

public interface DishUnitDao {

    void save(DishUnit dishUnit);

    void remove(DishUnit dishUnit);

    void update(DishUnit dishUnit);

    DishUnit loadById(int id);

    DishUnit load(Ingredient ingredient, int quantity);

    List<DishUnit> loadAll();
}
