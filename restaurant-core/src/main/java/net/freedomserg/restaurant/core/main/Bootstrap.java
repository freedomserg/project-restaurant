package net.freedomserg.restaurant.core.main;

import net.freedomserg.restaurant.core.model.entity.Category;
import net.freedomserg.restaurant.core.service.CategoryService;
import net.freedomserg.restaurant.core.service.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {

    private EmployeeService employeeService;
    private CategoryService categoryService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        Bootstrap bootstrap = applicationContext.getBean("bootstrap", Bootstrap.class);
        bootstrap.start();

    }

    private Category createCategory() {
        Category category = new Category();
        category.setCategoryName("fish");
        return category;
    }

    private Category updateCategory() {
        Category category = new Category();
        category.setCategoryId(4);
        category.setCategoryName("sea fish");
        return category;
    }

    private void start() {
        Category category = new Category();
        category.setCategoryId(4);
        category.setCategoryName("sea fish");

        categoryService.delete(category);

        //Category category = categoryService.getByName("sea fish");
        //System.out.println(category);

        categoryService.getAll().forEach(System.out::println);
    }
}
