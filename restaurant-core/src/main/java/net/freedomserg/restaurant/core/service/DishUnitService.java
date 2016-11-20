package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.model.entity.DishUnit;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DishUnitService {

    private DishUnitDao dishUnitDao;

    public void setDishUnitDao(DishUnitDao dishUnitDao) {
        this.dishUnitDao = dishUnitDao;
    }

    @Transactional
    public Integer add(DishUnit dishUnit) {
        return dishUnitDao.save(dishUnit);
    }

    @Transactional
    public void delete(DishUnit dishUnit) {
        dishUnitDao.remove(dishUnit);
    }

    @Transactional
    public void update(DishUnit dishUnit) {
        dishUnitDao.update(dishUnit);
    }

    @Transactional
    public DishUnit get(Dish dish, Ingredient ingredient) {
        return dishUnitDao.load(dish, ingredient);
    }

    @Transactional
    public List<DishUnit> getAll() {
        return dishUnitDao.loadAll();
    }
}
