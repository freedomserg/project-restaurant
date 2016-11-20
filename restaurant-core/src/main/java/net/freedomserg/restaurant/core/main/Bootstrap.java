package net.freedomserg.restaurant.core.main;

import net.freedomserg.restaurant.core.model.entity.*;
import net.freedomserg.restaurant.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bootstrap {

    private EmployeeService employeeService;
    private CategoryService categoryService;
    private MenuService menuService;
    private IngredientService ingredientService;
    private DishService dishService;
    private StoreService storeService;
    private OrderService orderService;
    private DishUnitService dishUnitService;
    private OrderUnitService orderUnitService;
    private final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setDishUnitService(DishUnitService dishUnitService) {
        this.dishUnitService = dishUnitService;
    }

    public void setOrderUnitService(OrderUnitService orderUnitService) {
        this.orderUnitService = orderUnitService;
    }

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        Bootstrap bootstrap = applicationContext.getBean("bootstrap", Bootstrap.class);
        bootstrap.start();

    }

    private void createMeatUnit(Dish dish) {
        Ingredient meat = ingredientService.getByName("meat");
        DishUnit meatUnit = new DishUnit();
        meatUnit.setDish(dish);
        meatUnit.setIngredient(meat);
        int quantity = 400;
        meatUnit.setQuantity(quantity);
        dishUnitService.add(meatUnit);
    }

    private void createSpiceUnit(Dish dish) {
        Ingredient spice = ingredientService.getByName("spice");
        DishUnit spiceUnit = new DishUnit();
        spiceUnit.setDish(dish);
        spiceUnit.setIngredient(spice);
        int quantity = 50;
        spiceUnit.setQuantity(quantity);
        dishUnitService.add(spiceUnit);
    }

    private void createOrderUnit(int id) {
        OrderUnit orderUnit = new OrderUnit();
        Dish dish = dishService.getByName("Caesar");
        orderUnit.setDish(dish);
        orderUnit.setQuantity(2);
        Order order = orderService.getById(id);
        orderUnit.setOrder(order);
        orderUnitService.add(orderUnit);
    }



    private void start() {
        Waiter waiter1 = employeeService.getWaiterByName("Jacob");
        Waiter waiter2 = employeeService.getWaiterById(1);

        System.out.println(waiter1);
        System.out.println(waiter2);

        employeeService.getAllWaiters().forEach(System.out::println);

    }
}
