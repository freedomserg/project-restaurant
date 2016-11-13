package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Store;

import java.util.List;

public interface StoreDao {

    void save(Store store);

    void remove(Store store);

    void update(Store store);

    Store loadByName(String name);

    List<Store> loadAll();
}
