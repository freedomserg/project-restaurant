package net.freedomserg.restaurant.core.model.hibernate;

import net.freedomserg.restaurant.core.model.dao.IngredientDao;
import net.freedomserg.restaurant.core.model.entity.Ingredient;
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
public class TestHingredientDao {

    @Autowired
    private IngredientDao ingredientDao;

    public static final String TEST_INGREDIENT_NAME_1 = "Salmon";
    public static final String TEST_INGREDIENT_NAME_2 = "Garlic";

    @Test
    @Transactional
    @Rollback
    public void testLoadAllEmptyDB() {
        List<Ingredient> ingredients = ingredientDao.loadAll();

        assertTrue(ingredients.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        Ingredient ingredient = createIngredient(TEST_INGREDIENT_NAME_1);
        ingredientDao.save(ingredient);
        List<Ingredient> ingredients = ingredientDao.loadAll();

        assertEquals(1, ingredients.size());
        assertEquals(ingredient.getIngredientName(), ingredients.get(0).getIngredientName());
        assertEquals(Status.ACTUAL, ingredients.get(0).getStatus());
    }

    @Test(expected = SuchEntityAlreadyExistsRestaurantException.class)
    @Transactional
    @Rollback
    public void testFailedSaveWithIdenticalName() {
        Ingredient ingredient = createIngredient(TEST_INGREDIENT_NAME_1);
        ingredientDao.save(ingredient);
        ingredientDao.save(ingredient);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadAllTwoEntities() {
        ingredientDao.save(createIngredient(TEST_INGREDIENT_NAME_1));
        ingredientDao.save(createIngredient(TEST_INGREDIENT_NAME_2));
        List<Ingredient> ingredients = ingredientDao.loadAll();

        assertEquals(2, ingredients.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadByName() {
        ingredientDao.save(createIngredient(TEST_INGREDIENT_NAME_1));
        Ingredient extracted = ingredientDao.loadByName(TEST_INGREDIENT_NAME_1);

        assertNotNull(extracted);
        assertEquals(TEST_INGREDIENT_NAME_1, extracted.getIngredientName());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testFailedLoadByNameAsNoSuchEntity() {
        Ingredient extracted = ingredientDao.loadByName(TEST_INGREDIENT_NAME_1);

        assertNull(extracted);
    }

    @Test
    @Transactional
    @Rollback
    public void testLoadById() {
        int id = ingredientDao.save(createIngredient(TEST_INGREDIENT_NAME_1));
        Ingredient extracted = ingredientDao.loadById(id);

        assertNotNull(extracted);
        assertEquals(TEST_INGREDIENT_NAME_1, extracted.getIngredientName());
        assertEquals(id, extracted.getIngredientId());
        assertEquals(Status.ACTUAL, extracted.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() {
        int id = ingredientDao.save(createIngredient(TEST_INGREDIENT_NAME_1));
        Ingredient extracted = ingredientDao.loadById(id);
        String updatedName = "Red fish";
        extracted.setIngredientName(updatedName);
        ingredientDao.update(extracted);
        Ingredient reextracted = ingredientDao.loadById(id);

        assertEquals(updatedName, reextracted.getIngredientName());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        int id = ingredientDao.save(createIngredient(TEST_INGREDIENT_NAME_1));
        Ingredient extracted = ingredientDao.loadById(id);
        ingredientDao.remove(extracted);
        List<Ingredient> ingredients = ingredientDao.loadAll();

        assertTrue(ingredients.isEmpty());
    }

    private Ingredient createIngredient(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(name);
        return ingredient;
    }
}