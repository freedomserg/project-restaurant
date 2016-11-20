package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.CategoryDao;
import net.freedomserg.restaurant.core.model.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(locations =
        {
                "classpath:application-context-test.xml",
                "classpath:hibernate-context-test.xml"
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class HcategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    @Transactional
    @Rollback
    public void testAddCategory() {
        Category category = new Category();
        category.setCategoryName("Cakes");

        categoryDao.save(category);
        List<Category> categories = categoryDao.loadAll();
        Assert.assertEquals(1, categories.size());
        Assert.assertEquals(category.getCategoryName(), categories.get(0).getCategoryName());
    }

}