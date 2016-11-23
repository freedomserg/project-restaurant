package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.CategoryDao;
import net.freedomserg.restaurant.core.model.entity.Category;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.exception.SuchEntityAlreadyExistsRestaurantException;
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

    public static final String TEST_CATEGORY_NAME_1 = "Cakes";
    public static final String TEST_CATEGORY_NAME_2 = "Fish";


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
        Category category = createCategory(TEST_CATEGORY_NAME_1);
        int id = categoryDao.save(category);
        List<Category> categories = categoryDao.loadAll();

        assertFalse(categories.isEmpty());
        assertEquals(id, categories.get(0).getCategoryId());
        assertEquals(category.getCategoryName(), categories.get(0).getCategoryName());
        assertEquals(Status.ACTUAL, categories.get(0).getStatus());
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSaveWithIdenticalCategoryName() {
        Category category = createCategory(TEST_CATEGORY_NAME_1);
        categoryDao.save(category);
        categoryDao.save(category);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllTwoEntities() {
        categoryDao.save(createCategory(TEST_CATEGORY_NAME_1));
        categoryDao.save(createCategory(TEST_CATEGORY_NAME_2));
        List<Category> categories = categoryDao.loadAll();

        assertEquals(2, categories.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByName() {
        categoryDao.save(createCategory(TEST_CATEGORY_NAME_1));
        Category extracted = categoryDao.loadByName(TEST_CATEGORY_NAME_1);

        assertNotNull(extracted);
        assertEquals(TEST_CATEGORY_NAME_1, extracted.getCategoryName());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testFailedLoadByNameAsNoSuchEntity() {
        categoryDao.save(createCategory(TEST_CATEGORY_NAME_1));
        Category extracted = categoryDao.loadByName(TEST_CATEGORY_NAME_2);

        assertNull(extracted);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = categoryDao.save(createCategory(TEST_CATEGORY_NAME_1));
        Category extracted = categoryDao.loadById(id);

        assertNotNull(extracted);
        assertEquals(TEST_CATEGORY_NAME_1, extracted.getCategoryName());
        assertEquals(id, extracted.getCategoryId());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = categoryDao.save(createCategory("Cakes"));
        Category extracted = categoryDao.loadById(id);
        String updatedName = "Taste cakes";
        extracted.setCategoryName(updatedName);
        categoryDao.update(extracted);
        Category reextracted = categoryDao.loadById(id);

        assertEquals(updatedName, reextracted.getCategoryName());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = categoryDao.save(createCategory(TEST_CATEGORY_NAME_1));
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