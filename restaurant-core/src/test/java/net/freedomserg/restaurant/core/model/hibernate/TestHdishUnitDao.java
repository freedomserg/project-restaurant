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
                "classpath:core-application-context-test.xml",
                "classpath:core-hibernate-context-test.xml"
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHdishUnitDao {

    @Autowired
    private DishUnitDao dishUnitDao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private IngredientDao ingredientDao;

    private Ingredient ingredient;
    private Dish dish;
    public static final String TEST_INGREDIENT_NAME = "arugula";
    public static final String TEST_CATEGORY_NAME = "salads";
    public static final String TEST_DISH_NAME = "Green salad";
    public static final int TEST_DISH_WEIGHT = 300;
    public static final double TEST_DISH_PRICE = 2.5;
    public static final int TEST_DISHUNIT_QUANTITY = 50;

    @Transactional
    @Before
    public void createDishAndSaveToDB() {
        Dish instance = new Dish();
        instance.setDishName(TEST_DISH_NAME);
        instance.setWeight(TEST_DISH_WEIGHT);
        instance.setPrice(TEST_DISH_PRICE);
        instance.setCategory(createAndGetCategory());
        int id = dishDao.save(instance);
        dish = dishDao.loadById(id);
    }

    @Transactional
    private Category createAndGetCategory() {
        Category instance = new Category();
        instance.setCategoryName(TEST_CATEGORY_NAME);
        int id = categoryDao.save(instance);
        return categoryDao.loadById(id);
    }

    @Transactional
    @Before
    public void createAndSaveIngredientToDB() {
        Ingredient instantiated = new Ingredient();
        instantiated.setIngredientName(TEST_INGREDIENT_NAME);
        int id = ingredientDao.save(instantiated);
        ingredient = ingredientDao.loadById(id);
    }

    private DishUnit createDishUnitEntity() {
        DishUnit dishUnit = new DishUnit();
        dishUnit.setIngredient(ingredient);
        dishUnit.setDish(dish);
        dishUnit.setQuantity(TEST_DISHUNIT_QUANTITY);
        return dishUnit;
    }

    @Transactional
    @After
    public void removeTestEntities() {
        dishDao.remove(dish);
        categoryDao.remove(categoryDao.loadByName(TEST_CATEGORY_NAME));
        ingredientDao.remove(ingredient);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<DishUnit> dishUnits = dishUnitDao.loadAll();

        assertTrue(dishUnits.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        int id = dishUnitDao.save(createDishUnitEntity());
        List<DishUnit> dishUnits = dishUnitDao.loadAll();

        assertFalse(dishUnits.isEmpty());

        DishUnit extracted = dishUnits.get(0);

        assertEquals(id, extracted.getId());
        assertEquals(dish, extracted.getDish());
        assertEquals(ingredient, extracted.getIngredient());
        assertEquals(TEST_DISHUNIT_QUANTITY, extracted.getQuantity());
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSaveWithIdenticalDishAndIngredient() {
        DishUnit instance = createDishUnitEntity();
        dishUnitDao.save(instance);
        dishUnitDao.save(instance);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllOneEntity() {
        dishUnitDao.save(createDishUnitEntity());
        List<DishUnit> dishUnits = dishUnitDao.loadAll();

        assertTrue(dishUnits.size() == 1);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoad() {
        dishUnitDao.save(createDishUnitEntity());
        DishUnit extracted = dishUnitDao.load(dish, ingredient);

        assertEquals(dish, extracted.getDish());
        assertEquals(ingredient, extracted.getIngredient());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadAsNoSuchEntity() {
        dishUnitDao.load(dish, ingredient);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = dishUnitDao.save(createDishUnitEntity());
        DishUnit extracted = dishUnitDao.loadById(id);

        assertEquals(id, extracted.getId());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadByIdAsNoSuchId() {
        int id = dishUnitDao.save(createDishUnitEntity());
        int errorShift = 1;
        dishUnitDao.loadById(id + errorShift);
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSaveWithIdenticalId() {
        int id = dishUnitDao.save(createDishUnitEntity());
        DishUnit extracted = dishUnitDao.loadById(id);
        dishUnitDao.save(extracted);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = dishUnitDao.save(createDishUnitEntity());
        DishUnit extracted = dishUnitDao.loadById(id);
        int updatedQuantity = 2000;
        extracted.setQuantity(updatedQuantity);
        dishUnitDao.update(extracted);
        extracted = dishUnitDao.loadById(id);

        assertEquals(updatedQuantity, extracted.getQuantity());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = dishUnitDao.save(createDishUnitEntity());
        DishUnit extracted = dishUnitDao.loadById(id);
        dish.setUnits(Arrays.asList(extracted));
        dishDao.update(dish);
        dish = dishDao.loadById(dish.getDishId());

        dishUnitDao.remove(extracted);
        List<DishUnit> dishUnits = dishUnitDao.loadAll();

        dish = dishDao.loadById(dish.getDishId());

        assertTrue(dishUnits.isEmpty());
        assertTrue(dish.getUnits().isEmpty());
    }
}
