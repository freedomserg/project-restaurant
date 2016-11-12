package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Ingredient;

import java.util.List;

public interface IngredientDao {

    void save(Ingredient ingredient);

    void remove(Ingredient ingredient);

    void update(Ingredient ingredient);

    Ingredient loadByName(String name);

    List<Ingredient> loadAll();
}
