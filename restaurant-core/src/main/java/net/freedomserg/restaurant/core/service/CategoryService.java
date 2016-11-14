package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.CategoryDao;
import net.freedomserg.restaurant.core.model.entity.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CategoryService {

    private CategoryDao categoryDao;

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional
    public void add(Category category) {
        categoryDao.save(category);
    }

    @Transactional
    public void delete(Category category) {
        categoryDao.remove(category);
    }

    @Transactional
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Transactional
    public Category getByName(String name) {
        return categoryDao.loadByName(name);
    }

    @Transactional
    public List<Category> getAll() {
        return categoryDao.loadAll();
    }
}
