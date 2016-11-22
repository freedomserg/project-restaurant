package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.dao.StoreDao;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
import net.freedomserg.restaurant.core.model.entity.Status;
import net.freedomserg.restaurant.core.model.entity.Store;
import org.junit.After;
import org.junit.Before;
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
public class TestHstoreDao {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private IngredientDao ingredientDao;

    private Ingredient ingredient;
    public static final String TEST_INGREDIENT_NAME = "Potato";
    public static final int TEST_INGREDIENT_QUANTITY = 1000;


    @Transactional
    @Before
    public void createAndSaveIngredientInDB() {
        Ingredient instantiated = new Ingredient();
        instantiated.setIngredientName(TEST_INGREDIENT_NAME);
        int id = ingredientDao.save(instantiated);
        ingredient = ingredientDao.loadById(id);
    }

    @Transactional
    @After
    public void removeIngredient() {
        ingredientDao.remove(ingredient);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<Store> stores = storeDao.loadAll();

        assertTrue(stores.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        storeDao.save(createStoreEntity());
        List<Store> stores = storeDao.loadAll();
        Store extracted = stores.get(0);

        assertEquals(ingredient, extracted.getIngredient());
        assertEquals(TEST_INGREDIENT_QUANTITY, extracted.getQuantity());
        assertEquals(Status.ACTUAL, stores.get(0).getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllOneEntity() {
        storeDao.save(createStoreEntity());
        List<Store> stores = storeDao.loadAll();

        assertEquals(1, stores.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByIngredient() {
        storeDao.save(createStoreEntity());
        Store extracted = storeDao.loadByIngredient(ingredient);

        assertNotNull(extracted);
        assertEquals(ingredient, extracted.getIngredient());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = storeDao.save(createStoreEntity());
        Store extracted = storeDao.loadById(id);
        int updatedQuantity = 5000;
        extracted.setQuantity(updatedQuantity);
        storeDao.update(extracted);
        Store reextracted = storeDao.loadById(id);

        assertEquals(updatedQuantity, reextracted.getQuantity());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = storeDao.save(createStoreEntity());
        Store extracted = storeDao.loadById(id);
        storeDao.remove(extracted);
        List<Store> stores = storeDao.loadAll();

        assertTrue(stores.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = storeDao.save(createStoreEntity());
        Store extracted = storeDao.loadById(id);

        assertNotNull(extracted);
        assertEquals(id, extracted.getId());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    private Store createStoreEntity() {
        Store store = new Store();
        store.setIngredient(ingredient);
        store.setQuantity(TEST_INGREDIENT_QUANTITY);
        return store;
    }

}