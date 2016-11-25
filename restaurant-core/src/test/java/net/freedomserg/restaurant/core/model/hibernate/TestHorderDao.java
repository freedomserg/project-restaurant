package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.*;
import net.freedomserg.restaurant.core.model.entity.*;
import net.freedomserg.restaurant.core.model.exception.IllegalOperationRestaurantException;
import net.freedomserg.restaurant.core.model.exception.InvalidOrderDateRestaurantException;
import net.freedomserg.restaurant.core.model.exception.NoSuchEntityRestaurantException;
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
                "classpath:application-context-test.xml",
                "classpath:hibernate-context-test.xml"
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHorderDao {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderUnitDao orderUnitDao;

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

    private Waiter waiter;
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
    public void createWaiterAndSaveToDB() {
        Waiter waiterInstance = new Waiter();
        waiterInstance.setName(TEST_WAITER_NAME);
        waiterInstance.setPosition(TEST_EMPLOYEE_POSITION);
        int id = employeeDao.save(waiterInstance);
        waiter = employeeDao.loadWaiterById(id);
    }

    @Transactional
    @After
    public void removeTestEntities() {
        employeeDao.remove(waiter);
    }

    private Order createOrderEntity() {
        Order orderInstance = new Order();
        orderInstance.setTableNumber(TEST_TABLE_NUMBER);
        Date orderDate = null;
        try {
            orderDate = DATE_FORMAT.parse(TEST_ORDER_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderInstance.setOrderDate(orderDate);
        orderInstance.setWaiter(waiter);
        return orderInstance;
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<Order> orders = orderDao.loadAll();

        assertTrue(orders.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        int id = orderDao.save(createOrderEntity());
        List<Order> orders = orderDao.loadAll();

        assertFalse(orders.isEmpty());

        Order extracted = orders.get(0);

        assertEquals(id, extracted.getOrderId());
        assertEquals(OrderStatus.OPENED, extracted.getStatus());
        assertEquals(waiter, extracted.getWaiter());
        assertEquals(TEST_TABLE_NUMBER, extracted.getTableNumber());
        assertEquals(TEST_ORDER_DATE, DATE_FORMAT.format(extracted.getOrderDate()));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllOneEntity() {
        orderDao.save(createOrderEntity());
        List<Order> orders = orderDao.loadAll();

        assertTrue(orders.size() == 1);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = orderDao.save(createOrderEntity());
        Order extracted = orderDao.loadById(id);

        assertNotNull(extracted);
        assertEquals(id, extracted.getOrderId());
        assertEquals(OrderStatus.OPENED, extracted.getStatus());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadByIdAsNoSuchId() {
        int id = orderDao.save(createOrderEntity());
        int errorShift = 1;
        orderDao.loadById(id + errorShift);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByDate() {
        int id = orderDao.save(createOrderEntity());
        List<Order> orders = orderDao.loadByDate(TEST_ORDER_DATE);

        assertFalse(orders.isEmpty());

        Order extracted = orders.get(0);

        assertEquals(id, extracted.getOrderId());
        assertEquals(OrderStatus.OPENED, extracted.getStatus());
        assertEquals(TEST_ORDER_DATE, DATE_FORMAT.format(extracted.getOrderDate()));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByDateEmptyList() {
        orderDao.save(createOrderEntity());
        String dateWithShift = "2016-11-30";
        List<Order> orders = orderDao.loadByDate(dateWithShift);

        assertTrue(orders.isEmpty());
    }

    @Test(expected = InvalidOrderDateRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadByDateAsInvalidDateFormat() {
        orderDao.save(createOrderEntity());
        String invalidDate = "2016/11/25";
        orderDao.loadByDate(invalidDate);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByWaiter() {
        int id = orderDao.save(createOrderEntity());
        List<Order> orders = orderDao.loadByWaiter(waiter);

        assertFalse(orders.isEmpty());

        Order extracted = orders.get(0);

        assertEquals(id, extracted.getOrderId());
        assertEquals(OrderStatus.OPENED, extracted.getStatus());
        assertEquals(waiter, extracted.getWaiter());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByTable() {
        int id = orderDao.save(createOrderEntity());
        List<Order> orders = orderDao.loadByTable(TEST_TABLE_NUMBER);

        assertFalse(orders.isEmpty());

        Order extracted = orders.get(0);

        assertEquals(id, extracted.getOrderId());
        assertEquals(OrderStatus.OPENED, extracted.getStatus());
        assertEquals(TEST_TABLE_NUMBER, extracted.getTableNumber());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByTableEmptyList() {
        orderDao.save(createOrderEntity());
        int unExistingTable = Integer.MAX_VALUE;
        List<Order> orders = orderDao.loadByTable(unExistingTable);

        assertTrue(orders.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = orderDao.save(createOrderEntity());
        Order extracted = orderDao.loadById(id);
        int updatedTableNumber = 11;
        extracted.setTableNumber(updatedTableNumber);
        orderDao.update(extracted);
        extracted = orderDao.loadById(id);

        assertEquals(updatedTableNumber, extracted.getTableNumber());
    }

    @Test(expected = IllegalOperationRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedUpdateAsOrderIsClosed() {
        int id = orderDao.save(createOrderEntity());
        Order extracted = orderDao.loadById(id);

        extracted.setStatus(OrderStatus.CLOSED);
        orderDao.update(extracted);

        extracted = orderDao.loadById(id);
        int updatedTableNumber = 11;
        extracted.setTableNumber(updatedTableNumber);
        orderDao.update(extracted);
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = orderDao.save(createOrderEntity());
        Order orderInstance = orderDao.loadById(id);
        OrderUnit orderUnitInstance = createOrderUnitAndGetFromDB(orderInstance);
        orderInstance.setUnits(Arrays.asList(orderUnitInstance));
        orderDao.update(orderInstance);

        List<Order> orders = orderDao.loadAll();
        List<OrderUnit> orderUnits = orderUnitDao.loadAll();

        assertTrue(orders.size() == 1);
        assertTrue(orderUnits.size() == 1);

        orderDao.remove(orderInstance);

        orders = orderDao.loadAll();
        orderUnits = orderUnitDao.loadAll();

        assertTrue(orders.isEmpty());
        assertTrue(orderUnits.isEmpty());
    }

    @Transactional
    private OrderUnit createOrderUnitAndGetFromDB(Order orderInstance) {
        OrderUnit orderUnit = new OrderUnit();
        orderUnit.setOrder(orderInstance);
        orderUnit.setDish(createDishAndGetFromDB());
        orderUnit.setQuantity(TEST_ORDERUNIT_QUANTITY);
        int id = orderUnitDao.save(orderUnit);
        return orderUnitDao.loadById(id);
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

    @Test(expected = IllegalOperationRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedRemoveAsOrderIsClosed() {
        int id = orderDao.save(createOrderEntity());
        Order extracted = orderDao.loadById(id);
        extracted.setStatus(OrderStatus.CLOSED);
        orderDao.update(extracted);
        extracted = orderDao.loadById(id);

        orderDao.remove(extracted);
    }

}