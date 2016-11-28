package net.freedomserg.restaurant.userLayer.web;


import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//@Controller
//@ResponseBody
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public List<Employee> employees(Map<String, Object> model) {
        return employeeService.getAll();
    }

    /*@RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String employees(Map<String, Object> model) {
        model.put("employee", employeeService.getAll());
        return "employees";
    }*/
}
