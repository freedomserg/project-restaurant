package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.MenuDao;
import net.freedomserg.restaurant.core.model.entity.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MenuService {

    private MenuDao menuDao;

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Transactional
    public void add(Menu menu) {
        menuDao.save(menu);
    }

    @Transactional
    public void delete(Menu menu) {
        menuDao.remove(menu);
    }

    @Transactional
    public void update(Menu menu) {
        menuDao.update(menu);
    }

    @Transactional
    public Menu getByName(String name) {
        return menuDao.loadByName(name);
    }

    @Transactional
    public List<Menu> getAll() {
        return menuDao.loadAll();
    }
}
