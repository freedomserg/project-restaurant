package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Store;

import java.util.List;

public interface StoreDao {

    Integer save(Store store);

    void remove(Store store);

    void update(Store store);

    Store loadByIngredient(Ingredient ingredient);

    Store loadById(int id);

    List<Store> loadAll();
}
