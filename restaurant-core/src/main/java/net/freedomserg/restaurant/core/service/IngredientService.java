package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class IngredientService {

    private IngredientDao ingredientDao;

    public void setIngredientDao(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @Transactional
    public Integer add(Ingredient ingredient) {
        return ingredientDao.save(ingredient);
    }

    @Transactional
    public void delete(Ingredient ingredient) {
        ingredientDao.remove(ingredient);
    }

    @Transactional
    public void update(Ingredient ingredient) {
        ingredientDao.update(ingredient);
    }

    @Transactional
    public Ingredient getByName(String name) {
        return ingredientDao.loadByName(name);
    }

    @Transactional
    public List<Ingredient> getAll() {
        return ingredientDao.loadAll();
    }
}
