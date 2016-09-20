package net.freedomserg.restaurant.core.web;

import net.freedomserg.restaurant.core.model.entity.Employee;
import net.freedomserg.restaurant.core.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class EmployeeController {

    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<Employee> employees(Map<String, Object> model) {
        return employeeService.getAll();
    }
}
