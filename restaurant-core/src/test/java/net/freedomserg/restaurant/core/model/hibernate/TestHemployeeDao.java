package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.*;
import net.freedomserg.restaurant.core.model.entity.*;
import net.freedomserg.restaurant.core.model.exception.NoSuchEntityRestaurantException;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
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
public class TestHemployeeDao {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderUnitDao orderUnitDao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DishUnitDao dishUnitDao;

    @Autowired
    private IngredientDao ingredientDao;

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
    public static final String TEST_EMPLOYEE_NAME = "Rose";
    public static final String TEST_WAITER_POSITION = "waiter";
    public static final String TEST_EMPLOYEE_POSITION = "administrator";

    private Employee createEmployeeEntity() {
        Employee employee = new Employee();
        employee.setName(TEST_EMPLOYEE_NAME);
        employee.setPosition(TEST_EMPLOYEE_POSITION);
        return employee;
    }

    private Waiter createWaiterEntity() {
        Waiter waiter = new Waiter();
        waiter.setName(TEST_WAITER_NAME);
        waiter.setPosition(TEST_WAITER_POSITION);
        return waiter;
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<Employee> employees = employeeDao.loadAll();

        assertTrue(employees.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveEmployee() {
        int id = employeeDao.save(createEmployeeEntity());
        List<Employee> employees = employeeDao.loadAll();

        assertFalse(employees.isEmpty());

        Employee employee = employees.get(0);

        assertEquals(id, employee.getId());
        assertEquals(Status.ACTUAL, employee.getStatus());
        assertEquals(TEST_EMPLOYEE_NAME, employee.getName());
        assertEquals(TEST_EMPLOYEE_POSITION, employee.getPosition());
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSaveEmployeeWithIdenticalName() {
        Employee employee = createEmployeeEntity();
        employeeDao.save(employee);
        employeeDao.save(employee);
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveWaiter() {
        int id = employeeDao.save(createWaiterEntity());
        List<Employee> employees = employeeDao.loadAll();

        assertFalse(employees.isEmpty());

        Waiter waiter = (Waiter) employees.get(0);

        assertEquals(id, waiter.getId());
        assertEquals(Status.ACTUAL, waiter.getStatus());
        assertEquals(TEST_WAITER_NAME, waiter.getName());
        assertEquals(TEST_WAITER_POSITION, waiter.getPosition());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllTwoEntities() {
        employeeDao.save(createEmployeeEntity());
        employeeDao.save(createWaiterEntity());
        List<Employee> employees = employeeDao.loadAll();

        assertTrue(employees.size() == 2);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllWaitersOneEntity() {
        employeeDao.save(createWaiterEntity());
        List<Waiter> waiters = employeeDao.loadAllWaiters();

        assertTrue(waiters.size() == 1);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = employeeDao.save(createEmployeeEntity());
        Employee employee = employeeDao.loadById(id);

        assertEquals(id, employee.getId());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadByIdAsNoSuchId() {
        int id = employeeDao.save(createEmployeeEntity());
        int errorShift = 1;
        employeeDao.loadById(id + errorShift);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadWaiterById() {
        int id = employeeDao.save(createWaiterEntity());
        Waiter waiter = employeeDao.loadWaiterById(id);

        assertEquals(id, waiter.getId());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedLoadWaiterByIdAsNoSuchId() {
        int id = employeeDao.save(createWaiterEntity());
        int errorShift = 1;
        employeeDao.loadWaiterById(id + errorShift);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByName() {
        employeeDao.save(createEmployeeEntity());
        Employee employee = employeeDao.loadByName(TEST_EMPLOYEE_NAME);

        assertEquals(TEST_EMPLOYEE_NAME, employee.getName());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void tesFailedtLoadByNameAsNoSuchName() {
        employeeDao.save(createEmployeeEntity());
        String errorName = "Mary";
        employeeDao.loadByName(errorName);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadWaiterByName() {
        employeeDao.save(createWaiterEntity());
        Waiter waiter = employeeDao.loadWaiterByName(TEST_WAITER_NAME);

        assertEquals(TEST_WAITER_NAME, waiter.getName());
    }

    @Test(expected = NoSuchEntityRestaurantException.class)
    @Transactional
    @Rollback
    public void tesFailedtLoadWaiterByNameAsNoSuchName() {
        employeeDao.save(createWaiterEntity());
        String errorName = "Brad";
        employeeDao.loadWaiterByName(errorName);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateEmployee() {
        int id = employeeDao.save(createEmployeeEntity());
        String updatedName = "Batty";
        Employee employee = employeeDao.loadById(id);
        employee.setName(updatedName);
        employeeDao.update(employee);
        employee = employeeDao.loadById(id);

        assertEquals(updatedName, employee.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateWaiter() {
        int id = employeeDao.save(createWaiterEntity());
        Waiter waiter = employeeDao.loadWaiterById(id);
        Order order = createOrderAndGetFromDB(waiter);
        waiter.setOrders(Arrays.asList(order));
        employeeDao.update(waiter);
        waiter = employeeDao.loadWaiterById(id);

        assertEquals(order.getOrderId(), waiter.getOrders().get(0).getOrderId());
        removeTestEntities(order);
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveEmployee() {
        int id = employeeDao.save(createEmployeeEntity());
        Employee employee = employeeDao.loadById(id);
        employeeDao.remove(employee);
        List<Employee> employees = employeeDao.loadAll();

        assertTrue(employees.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoveWaiter() {
        int id = employeeDao.save(createWaiterEntity());
        Waiter waiter = employeeDao.loadWaiterById(id);
        Order order = createOrderAndGetFromDB(waiter);
        waiter.setOrders(Arrays.asList(order));
        employeeDao.update(waiter);
        waiter = employeeDao.loadWaiterById(id);
        employeeDao.remove(waiter);

        List<Waiter> waiters = employeeDao.loadAllWaiters();
        List<Order> orders = orderDao.loadAll();

        assertTrue(waiters.isEmpty());
        assertFalse(orders.isEmpty());

        removeTestEntities(orders.get(0));
    }

    @Transactional
    private void removeTestEntities(Order order) {
        orderDao.remove(order);
        dishDao.remove(dishDao.loadByName(TEST_DISH_NAME));
        categoryDao.remove(categoryDao.loadByName(TEST_CATEGORY_NAME));
        ingredientDao.remove(ingredientDao.loadByName(TEST_INGREDIENT_NAME));
    }

    @Transactional
    private Order createOrderAndGetFromDB(Waiter waiter) {
        Order orderInstance = new Order();
        orderInstance.setWaiter(waiter);
        orderInstance.setTableNumber(TEST_TABLE_NUMBER);
        Date orderDate = null;
        try {
            orderDate = DATE_FORMAT.parse(TEST_ORDER_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderInstance.setOrderDate(orderDate);
        int id = orderDao.save(orderInstance);
        orderInstance = orderDao.loadById(id);

        OrderUnit orderUnitInstance = createOrderUnitAndGetFromDB(orderInstance);
        orderInstance.setUnits(Arrays.asList(orderUnitInstance));
        orderDao.update(orderInstance);

        return orderDao.loadById(id);
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

}