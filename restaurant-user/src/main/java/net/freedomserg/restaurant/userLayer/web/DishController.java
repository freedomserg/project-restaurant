package net.freedomserg.restaurant.userLayer.web;

import net.freedomserg.restaurant.core.model.entity.Dish;
import net.freedomserg.restaurant.core.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping(value = "/dishes", method = RequestMethod.GET)
    public List<Dish> getAllDishes() {
        return dishService.getAll();
    }
}
