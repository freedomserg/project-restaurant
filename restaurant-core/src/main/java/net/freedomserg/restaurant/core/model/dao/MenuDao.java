package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.Menu;

import java.util.List;

public interface MenuDao {

    Integer save(Menu menu);

    void remove(Menu menu);

    void update(Menu menu);

    Menu loadByName(String name);

    Menu loadById(int id);

    List<Menu> loadAll();
}
