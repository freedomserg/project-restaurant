package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations =
        {
                "classpath:core-application-context-test.xml",
                "classpath:core-hibernate-context-test.xml"
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHorderUnitDao {

    @Autowired
    private OrderUnitDao orderUnitDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DishUnitDao dishUnitDao;

    @Autowired
    private IngredientDao ingredientDao;

    private Order order;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final String TEST_DISH_NAME = "Green salad";
    public static final int TEST_DISH_WEIGHT = 300;
    public static final double TEST_DISH_PRICE = 2.5;
    public static final String TEST_CATEGORY_NAME = "Salads";
    public static final String TEST_INGREDIENT_NAME = "Arugula";
    public static final int TEST_DISHUNIT_QUANTITY = 50;
    public static final int TEST_ORDERUNIT_QUANTITY = 2;
    public static final int TEST_TABLE_NUMBER = 1;
    public static final String TEST_ORDER_DATE = "2016-11-25";
    public static final String TEST_WAITER_NAME = "Dan";
    public static final String TEST_EMPLOYEE_POSITION = "waiter";

    @Transactional
    @Before
    public void createOrderAndSaveToDB() {
        Order orderInstance = new Order();
        orderInstance.setWaiter(createWaiterAndGetFromDB());
        orderInstance.setTableNumber(TEST_TABLE_NUMBER);
        Date orderDate = null;
        try {
            orderDate = DATE_FORMAT.parse(TEST_ORDER_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderInstance.setOrderDate(orderDate);
        int id = orderDao.save(orderInstance);
        order = orderDao.loadById(id);
    }

    @Transactional
    private Waiter createWaiterAndGetFromDB() {
        Waiter waiterInstance = new Waiter();
        waiterInstance.setName(TEST_WAITER_NAME);
        waiterInstance.setPosition(TEST_EMPLOYEE_POSITION);
        int id = employeeDao.save(waiterInstance);
        return employeeDao.loadWaiterById(id);
    }

    @Transactional
    @After
    public void removeTestEntities() {
        orderDao.remove(order);
        employeeDao.remove(employeeDao.loadWaiterByName(TEST_WAITER_NAME));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<OrderUnit> orderUnits = orderUnitDao.loadAll();

        assertTrue(orderUnits.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        int id = orderUnitDao.save(createOrderUnitEntity());
        List<OrderUnit> orderUnits = orderUnitDao.loadAll();

        assertFalse(orderUnits.isEmpty());

        OrderUnit extracted = orderUnits.get(0);

        assertEquals(id, extracted.getId());
        assertEquals(order, extracted.getOrder());
        assertEquals(TEST_DISH_NAME, extracted.getDish().getDishName());
        assertEquals(TEST_ORDERUNIT_QUANTITY, extracted.getQuantity());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllOneEntity() {
        orderUnitDao.save(createOrderUnitEntity());
        List<OrderUnit> orderUnits = orderUnitDao.loadAll();

        assertTrue(orderUnits.size() == 1);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = orderUnitDao.save(createOrderUnitEntity());
        OrderUnit extracted = orderUnitDao.loadById(id);

        assertEquals(id, extracted.getId());
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSave() {
        int id = orderUnitDao.save(createOrderUnitEntity());
        OrderUnit extracted = orderUnitDao.loadById(id);
        orderUnitDao.save(extracted);
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadByIdAsNoSuchId() {
        int id = orderUnitDao.save(createOrderUnitEntity());
        int errorShift = 1;
        orderUnitDao.loadById(id + errorShift);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoad() {
        orderUnitDao.save(createOrderUnitEntity());
        OrderUnit extracted = orderUnitDao.load(order, dishDao.loadByName(TEST_DISH_NAME));

        assertEquals(order, extracted.getOrder());
        assertEquals(dishDao.loadByName(TEST_DISH_NAME), extracted.getDish());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadAsNoSuchOrderUnit() {
        orderUnitDao.load(order, dishDao.loadByName(TEST_DISH_NAME));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = orderUnitDao.save(createOrderUnitEntity());
        OrderUnit extracted = orderUnitDao.loadById(id);
        int updatedQuantity = 5;
        extracted.setQuantity(updatedQuantity);
        orderUnitDao.update(extracted);
        extracted = orderUnitDao.loadById(id);

        assertEquals(updatedQuantity, extracted.getQuantity());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = orderUnitDao.save(createOrderUnitEntity());
        OrderUnit extracted = orderUnitDao.loadById(id);
        order.setUnits(Arrays.asList(extracted));
        orderDao.update(order);
        order = orderDao.loadById(order.getOrderId());

        orderUnitDao.remove(extracted);
        order = orderDao.loadById(order.getOrderId());

        List<OrderUnit> orderUnits = orderUnitDao.loadAll();

        assertTrue(orderUnits.isEmpty());
        assertTrue(order.getUnits().isEmpty());
    }


    private OrderUnit createOrderUnitEntity() {
        OrderUnit orderUnit = new OrderUnit();
        orderUnit.setOrder(order);
        orderUnit.setDish(createDishAndGetFromDB());
        orderUnit.setQuantity(TEST_ORDERUNIT_QUANTITY);
        return orderUnit;
    }

    @Transactional
    private Dish createDishAndGetFromDB() {
        Dish dishInstance = new Dish();
        dishInstance.setDishName(TEST_DISH_NAME);
        dishInstance.setWeight(TEST_DISH_WEIGHT);
        dishInstance.setPrice(TEST_DISH_PRICE);
        dishInstance.setCategory(createCategoryAndGetFromDB());
        int id = dishDao.save(dishInstance);
        dishInstance = dishDao.loadById(id);

        DishUnit dishUnitInstance = createDishUnitAndGetFromDB(dishInstance);
        dishInstance.setUnits(Arrays.asList(dishUnitInstance));
        dishDao.update(dishInstance);
        return dishDao.loadById(id);
    }

    @Transactional
    private DishUnit createDishUnitAndGetFromDB(Dish dishInstance) {
        DishUnit dishUnitInstance = new DishUnit();
        dishUnitInstance.setDish(dishInstance);
        dishUnitInstance.setIngredient(createIngredientAndGetFromDB());
        dishUnitInstance.setQuantity(TEST_DISHUNIT_QUANTITY);
        int id = dishUnitDao.save(dishUnitInstance);
        return dishUnitDao.loadById(id);
    }

    @Transactional
    private Ingredient createIngredientAndGetFromDB() {
        Ingredient instantiated = new Ingredient();
        instantiated.setIngredientName(TEST_INGREDIENT_NAME);
        int id = ingredientDao.save(instantiated);
        return ingredientDao.loadById(id);
    }

    @Transactional
    private Category createCategoryAndGetFromDB() {
        Category instance = new Category();
        instance.setCategoryName(TEST_CATEGORY_NAME);
        int id = categoryDao.save(instance);
        return categoryDao.loadById(id);
    }


}