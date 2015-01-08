package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest extends BaseTestCase {

    @Test
    public void testCreateCategory() {
	Category category = new Category("Andrew's Category", null);
	category.save();

	category = Category.find.byId(category.getId());
	assertNotNull(category.getId());
    }

    @Test
    public void testFindCategory() {
	Category category = new Category("Andrew's Other Category", null);
	category.save();

	category = Category.find.byId(category.getId());
	assertNotNull(category.getId());
    }

    @Test
    public void testRemoveCategory() {
	Category category = new Category("Andrew's Other Category Category",
		null);
	category.save();
	category.delete();

	category = Category.find.byId(category.getId());
	assertNull(category);
    }

    @Test
    public void testEditCategory() {
	Category category = new Category("Andrew's Category", null);
	category.save();
	category.setName("Role Playing Game");
	category.save();

	category = Category.find.byId(category.getId());
	assertEquals(category.getName(), "Role Playing Game");
    }
}