package net.freedomserg.restaurant.userLayer.web;

import net.freedomserg.restaurant.core.model.entity.Waiter;
import net.freedomserg.restaurant.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/waiter", method = RequestMethod.GET)
    public List<Waiter> getAllWaiters() {
        return employeeService.getAllWaiters();
    }

}
