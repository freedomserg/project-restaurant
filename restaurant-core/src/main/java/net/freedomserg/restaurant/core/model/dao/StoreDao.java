package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Store;

import java.util.List;

public interface StoreDao {

    void saveIngredient(Ingredient ingredient);

    void removeIngredient(Ingredient ingredient);

    void updateQuantity(Ingredient ingredient, int quantity);

    Store loadByName(String name);

    List<Store> loadAll();
}
