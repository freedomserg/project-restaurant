package net.freedomserg.restaurant.core.main;

import net.freedomserg.restaurant.core.service.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {

    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml",
                "hibernate-context.xml");
        Bootstrap bootstrap = applicationContext.getBean("bootstrap", Bootstrap.class);
        bootstrap.start();

    }

    private void start() {
        //hEmployeeController.printEmployees();
        employeeService.getAll().forEach(System.out::println);

    }
}
