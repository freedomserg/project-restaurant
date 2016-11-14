package net.freedomserg.restaurant.core.main;

import com.sun.xml.internal.bind.v2.TODO;
import net.freedomserg.restaurant.core.model.entity.Category;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Menu;
import net.freedomserg.restaurant.core.service.CategoryService;
import net.freedomserg.restaurant.core.service.EmployeeService;
import net.freedomserg.restaurant.core.service.IngredientService;
import net.freedomserg.restaurant.core.service.MenuService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class Bootstrap {

    private EmployeeService employeeService;
    private CategoryService categoryService;
    private MenuService menuService;
    private IngredientService ingredientService;

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

    //todo test Menu with Dish

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        Bootstrap bootstrap = applicationContext.getBean("bootstrap", Bootstrap.class);
        bootstrap.start();

    }

    private Ingredient create() {
        Ingredient item = new Ingredient();
        item.setIngredientName("garlic");
        return item;
    }

    private Ingredient update() {
        Ingredient item = new Ingredient();
        item.setIngredientId(26);
        item.setIngredientName("best garlic");
        return item;
    }

    private void start() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(26);
        ingredient.setIngredientName("best garlic");
        ingredientService.delete(ingredient);
        ingredientService.getAll().forEach(System.out::println);
    }
}
