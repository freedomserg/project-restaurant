package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Ingredient;

import java.util.List;

public interface IngredientDao {

    Integer save(Ingredient ingredient);

    void remove(Ingredient ingredient);

    void update(Ingredient ingredient);

    Ingredient loadByName(String name);

    Ingredient loadById(int id);

    List<Ingredient> loadAll();
}
