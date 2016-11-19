package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
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
    public void add(DishUnit dishUnit) {
        dishUnitDao.save(dishUnit);
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
    public DishUnit getById(int id) {
        return dishUnitDao.loadById(id);
    }

    @Transactional
    public DishUnit get(Ingredient ingredient, int quantity) {
        return dishUnitDao.load(ingredient, quantity);
    }

    @Transactional
    public List<DishUnit> getAll() {
        return dishUnitDao.loadAll();
    }
}
