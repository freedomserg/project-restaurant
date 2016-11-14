package net.freedomserg.restaurant.core.main;

import com.sun.xml.internal.bind.v2.TODO;
import net.freedomserg.restaurant.core.model.entity.Category;
import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.model.entity.Menu;
import net.freedomserg.restaurant.core.service.CategoryService;
import net.freedomserg.restaurant.core.service.EmployeeService;
import net.freedomserg.restaurant.core.service.MenuService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class Bootstrap {

    private EmployeeService employeeService;
    private CategoryService categoryService;
    private MenuService menuService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    //todo test Menu with Dish

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        Bootstrap bootstrap = applicationContext.getBean("bootstrap", Bootstrap.class);
        bootstrap.start();

    }

    private Menu createMenu() {
        Menu menu = new Menu();
        menu.setMenuName("dinner menu");
        return menu;
    }

    private Employee updateEmployee() {
        Employee employee = new Employee();
        employee.setId(9);
        employee.setName("Peter");
        employee.setBirthday(new Date());
        employee.setCell("000");
        employee.setPosition("owner");
        employee.setSalary(50000);
        return employee;
    }

    private void start() {
        Employee employee = new Employee();
        employee.setId(9);
        employee.setName("Peter");
        employee.setBirthday(new Date());
        employee.setCell("000");
        employee.setPosition("owner");
        employee.setSalary(50000);

        employeeService.delete(employee);
        employeeService.getAll().forEach(System.out::println);
    }
}
