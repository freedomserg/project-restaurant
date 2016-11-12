package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Category;

import java.util.List;

public interface CategoryDao {

    void save(Category category);

    void remove(Category category);

    void update(Category category);

    Category loadByName(String name);

    List<Category> loadAll();
}