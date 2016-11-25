package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.CategoryDao;
import net.freedomserg.restaurant.core.model.dao.DishDao;
import net.freedomserg.restaurant.core.model.dao.DishUnitDao;
import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.entity.*;
import net.freedomserg.restaurant.core.model.exception.NoSuchEntityRestaurantException;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations =
        {
                "classpath:application-context-test.xml",
                "classpath:hibernate-context-test.xml"
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHdishDao {

    @Autowired
    private DishDao dishDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DishUnitDao dishUnitDao;

    @Autowired
    private IngredientDao ingredientDao;

    private Category category;
    public static final String TEST_DISH_NAME = "Green salad";
    public static final int TEST_DISH_WEIGHT = 300;
    public static final double TEST_DISH_PRICE = 2.5;
    public static final String TEST_CATEGORY_NAME = "Salads";
    public static final String TEST_INGREDIENT_NAME = "Arugula";
    public static final int TEST_DISHUNIT_QUANTITY = 50;

    @Transactional
    @Before
    public void createAndSaveCategoryToDB() {
        Category instantiated = new Category();
        instantiated.setCategoryName(TEST_CATEGORY_NAME);
        int id = categoryDao.save(instantiated);
        category = categoryDao.loadById(id);
    }

    @Transactional
    @After
    public void removeCategory() {
        categoryDao.remove(category);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<Dish> dishes = dishDao.loadAll();

        assertTrue(dishes.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        int id = dishDao.save(createDishEntity());
        List<Dish> dishes = dishDao.loadAll();

        assertFalse(dishes.isEmpty());

        Dish dish = dishes.get(0);

        assertEquals(id, dish.getDishId());
        assertEquals(category, dish.getCategory());
        assertEquals(TEST_DISH_NAME, dish.getDishName());
        assertEquals(TEST_DISH_WEIGHT, dish.getWeight());
        assertEquals(Status.ACTUAL, dish.getStatus());
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSaveWithIdenticalDishName() {
        dishDao.save(createDishEntity());
        dishDao.save(createDishEntity());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllOneEntity() {
        dishDao.save(createDishEntity());
        List<Dish> dishes = dishDao.loadAll();

        assertEquals(1, dishes.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByName() {
        dishDao.save(createDishEntity());
        Dish extracted = dishDao.loadByName(TEST_DISH_NAME);

        assertNotNull(extracted);
        assertEquals(TEST_DISH_NAME, extracted.getDishName());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadByNameAsNoSuchEntity() {
        dishDao.loadByName(TEST_DISH_NAME);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = dishDao.save(createDishEntity());
        Dish extracted = dishDao.loadById(id);

        assertNotNull(extracted);
        assertEquals(id, extracted.getDishId());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadByIdAsNoSuchId() {
        int id = dishDao.save(createDishEntity());
        int errorShift = 1;
        dishDao.loadById(id + errorShift);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = dishDao.save(createDishEntity());
        Dish extracted = dishDao.loadById(id);
        int updatedWeight = 350;
        extracted.setWeight(updatedWeight);
        dishDao.update(extracted);
        Dish reextracted = dishDao.loadById(id);

        assertEquals(updatedWeight, reextracted.getWeight());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int dishId = dishDao.save(createDishEntity());
        Dish extractedDish = dishDao.loadById(dishId);

        int ingredientId = ingredientDao.save(createIngredientEntity());
        Ingredient extractedIngredient = ingredientDao.loadById(ingredientId);

        DishUnit dishUnit = createDishUnitEntity(extractedDish, extractedIngredient);
        int dishUnitId = dishUnitDao.save(dishUnit);
        DishUnit extractedDishUnit = dishUnitDao.loadById(dishUnitId);

        extractedDish.setUnits(Arrays.asList(extractedDishUnit));
        dishDao.update(extractedDish);
        extractedDish = dishDao.loadById(dishId);

        dishDao.remove(extractedDish);
        List<Dish> dishes = dishDao.loadAll();
        List<DishUnit> dishUnits = dishUnitDao.loadAll();

        assertTrue(dishes.isEmpty());
        assertTrue(dishUnits.isEmpty());
    }

    private DishUnit createDishUnitEntity(Dish dish, Ingredient ingredient) {
        DishUnit dishUnit = new DishUnit();
        dishUnit.setDish(dish);
        dishUnit.setIngredient(ingredient);
        dishUnit.setQuantity(TEST_DISHUNIT_QUANTITY);
        return dishUnit;
    }

    private Ingredient createIngredientEntity() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(TEST_INGREDIENT_NAME);
        return ingredient;
    }

    private Dish createDishEntity() {
        Dish dish = new Dish();
        dish.setDishName(TEST_DISH_NAME);
        dish.setWeight(TEST_DISH_WEIGHT);
        dish.setPrice(TEST_DISH_PRICE);
        dish.setCategory(category);
        return dish;
    }
}