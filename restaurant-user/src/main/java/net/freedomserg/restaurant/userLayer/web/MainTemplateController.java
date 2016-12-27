package net.freedomserg.restaurant.userLayer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainTemplateController {

    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public String getStaff() {
        return "staff";
    }

    @RequestMapping(value = "/schema", method = RequestMethod.GET)
    public String getSchema() {
        return "schema";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String getContacts() {
        return "contacts";
    }
}
