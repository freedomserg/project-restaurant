package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.CategoryDao;
import net.freedomserg.restaurant.core.model.entity.Category;
import net.freedomserg.restaurant.core.model.entity.Status;
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
public class TestHcategoryDao {

    @Autowired
    private CategoryDao categoryDao;


    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<Category> categories = categoryDao.loadAll();

        assertTrue(categories.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        Category category = createCategory("Cakes");
        categoryDao.save(category);
        List<Category> categories = categoryDao.loadAll();

        assertEquals(1, categories.size());
        assertEquals(category.getCategoryName(), categories.get(0).getCategoryName());
        assertEquals(Status.ACTUAL, categories.get(0).getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllTwoEntities() {
        categoryDao.save(createCategory("Cakes"));
        categoryDao.save(createCategory("Fish"));
        List<Category> categories = categoryDao.loadAll();

        assertEquals(2, categories.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByName() {
        categoryDao.save(createCategory("Cakes"));
        Category extracted = categoryDao.loadByName("Cakes");

        assertNotNull(extracted);
        assertEquals("Cakes", extracted.getCategoryName());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = categoryDao.save(createCategory("Cakes"));
        Category extracted = categoryDao.loadById(id);

        assertNotNull(extracted);
        assertEquals("Cakes", extracted.getCategoryName());
        assertEquals(id, extracted.getCategoryId());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = categoryDao.save(createCategory("Cakes"));
        Category extracted = categoryDao.loadById(id);
        extracted.setCategoryName("Taste cakes");
        categoryDao.update(extracted);
        Category reextracted = categoryDao.loadById(id);

        assertEquals("Taste cakes", reextracted.getCategoryName());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = categoryDao.save(createCategory("Cakes"));
        Category extracted = categoryDao.loadById(id);
        categoryDao.remove(extracted);
        List<Category> categories = categoryDao.loadAll();

        assertTrue(categories.isEmpty());
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setCategoryName(name);
        return category;
    }
}