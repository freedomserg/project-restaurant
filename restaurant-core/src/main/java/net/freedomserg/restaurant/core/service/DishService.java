package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.DishDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DishService {

    private DishDao dishDao;

    public void setDishDao(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    @Transactional
    public Integer add(Dish dish) {
        return dishDao.save(dish);
    }

    @Transactional
    public void delete(Dish dish) {
        dishDao.remove(dish);
    }

    @Transactional
    public void update(Dish dish) {
        dishDao.update(dish);
    }

    @Transactional
    public Dish getByName(String name) {
        return dishDao.loadByName(name);
    }

    @Transactional
    public Dish getById(int id) {
        return dishDao.loadById(id);
    }

    @Transactional
    public List<Dish> getAll() {
        return dishDao.loadAll();
    }
}
