package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.*;
import net.freedomserg.restaurant.core.model.entity.*;
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
public class TestHmenuDao {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private DishUnitDao dishUnitDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private IngredientDao ingredientDao;

    private Dish dish;
    public static final String TEST_INGREDIENT_NAME = "arugula";
    public static final String TEST_CATEGORY_NAME = "salads";
    public static final String TEST_DISH_NAME = "Green salad";
    public static final int TEST_DISH_WEIGHT = 300;
    public static final double TEST_DISH_PRICE = 2.5;
    public static final int TEST_DISHUNIT_QUANTITY = 50;
    public static final String TEST_MENU_NAME = "Standard menu";


    @Transactional
    @Before
    public void createAndSaveDishToDB() {
        Dish dishInstance = new Dish();
        dishInstance.setDishName(TEST_DISH_NAME);
        dishInstance.setWeight(TEST_DISH_WEIGHT);
        dishInstance.setPrice(TEST_DISH_PRICE);
        dishInstance.setCategory(createAndGetCategoryFromDB());
        int id = dishDao.save(dishInstance);
        dishInstance = dishDao.loadById(id);

        DishUnit dishUnit = createAndGetDishFromDB(dishInstance);
        dishInstance.setUnits(Arrays.asList(dishUnit));
        dishDao.update(dishInstance);
        dish = dishDao.loadById(id);
    }

    @Transactional
    private DishUnit createAndGetDishFromDB(Dish dishInstance) {
        DishUnit dishUnit = new DishUnit();
        dishUnit.setDish(dishInstance);
        dishUnit.setIngredient(createAndGetIngredientFromDB());
        dishUnit.setQuantity(TEST_DISHUNIT_QUANTITY);
        int id = dishUnitDao.save(dishUnit);
        return dishUnitDao.loadById(id);
    }

    @Transactional
    private Category createAndGetCategoryFromDB() {
        Category instance = new Category();
        instance.setCategoryName(TEST_CATEGORY_NAME);
        int id = categoryDao.save(instance);
        return categoryDao.loadById(id);
    }

    @Transactional
    public Ingredient createAndGetIngredientFromDB() {
        Ingredient instantiated = new Ingredient();
        instantiated.setIngredientName(TEST_INGREDIENT_NAME);
        int id = ingredientDao.save(instantiated);
        return ingredientDao.loadById(id);
    }

    @Transactional
    @After
    public void removeTestEntities() {
        dishDao.remove(dish);
        categoryDao.remove(categoryDao.loadByName(TEST_CATEGORY_NAME));
        ingredientDao.remove(ingredientDao.loadByName(TEST_INGREDIENT_NAME));
    }

    private Menu createMenuEntity() {
        Menu menu = new Menu();
        menu.setMenuName(TEST_MENU_NAME);
        menu.setDishes(Arrays.asList(dish));
        return menu;
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<Menu> menus = menuDao.loadAll();

        assertTrue(menus.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        int id = menuDao.save(createMenuEntity());
        List<Menu> menus = menuDao.loadAll();

        assertFalse(menus.isEmpty());

        Menu extracted = menus.get(0);

        assertEquals(id, extracted.getMenuId());
        assertEquals(Status.ACTUAL, extracted.getStatus());
        assertArrayEquals(new Dish[]{dish}, extracted.getDishes().toArray());
        assertEquals(TEST_MENU_NAME, extracted.getMenuName());
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSaveWithIdenticalDishAndIngredient() {
        Menu menu = createMenuEntity();
        menuDao.save(menu);
        menuDao.save(menu);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllOneEntity() {
        menuDao.save(createMenuEntity());
        List<Menu> menus = menuDao.loadAll();

        assertTrue(menus.size() == 1);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByName() {
        menuDao.save(createMenuEntity());
        Menu extracted = menuDao.loadByName(TEST_MENU_NAME);

        assertNotNull(extracted);
        assertEquals(TEST_MENU_NAME, extracted.getMenuName());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testFailedLoadAsNoSuchEntity() {
        Menu extracted = menuDao.loadByName(TEST_MENU_NAME);

        assertNull(extracted);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = menuDao.save(createMenuEntity());
        Menu extracted = menuDao.loadById(id);

        assertNotNull(extracted);
        assertEquals(id, extracted.getMenuId());
        assertEquals(TEST_MENU_NAME, extracted.getMenuName());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = menuDao.save(createMenuEntity());
        Menu extracted = menuDao.loadById(id);
        String updatedName = "Regular menu";
        extracted.setMenuName(updatedName);
        menuDao.update(extracted);
        extracted = menuDao.loadById(id);

        assertEquals(updatedName, extracted.getMenuName());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = menuDao.save(createMenuEntity());
        Menu extracted = menuDao.loadById(id);
        menuDao.remove(extracted);
        List<Menu> menus = menuDao.loadAll();

        assertTrue(menus.isEmpty());
    }
}

