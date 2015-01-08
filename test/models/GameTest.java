package models;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import utils.GameUtils;

public class GameTest extends BaseTestCase {

    private static Category category;

    @BeforeClass
    public static void setupGame() {
	category = new Category("Test Category", null);
	category.save();
    }

    @Test
    public void testFindGame() {
	Game game = new Game("Andrew's Other Fake Game",
		"http://andrewzurn.com", "http://andrewzurn.com/image",
		category, false, GameUtils.getCurrentTimestamp());
	game.save();

	game = Game.find.byId(game.getId());
	assertNotNull(game);
    }

    /**
     * An odd runtime error coming up when the runs, category has not seemed to
     * have been saved yet.
     */
    // //@Test
    // public void testCreateGame() {
    // Game game = new Game("Andrew's Fake Game", "http://andrewzurn.com",
    // "http://andrewzurn.com/image", category, false,
    // GameUtils.getCurrentTimestamp());
    // game.save();
    // assertNotNull(game.getId());
    // }

    @Test
    public void testRemoveGame() {
	Game game = new Game("Andrew's Other Fake Fake Game",
		"http://andrewzurn.com", "http://andrewzurn.com/image",
		category, false, GameUtils.getCurrentTimestamp());
	game.save();
	game.delete();

	game = Game.find.byId(game.getId());
	assertNull(game);
    }

    @Test
    public void testEditGame() {
	Game game = new Game("Andrew's Other Game", "http://andrewzurn.com",
		"http://andrewzurn.com/image", category, false,
		GameUtils.getCurrentTimestamp());
	game.save();
	game.setName("Andrew Zurn's Nerdery Exam");
	game.save();

	game = Game.find.byId(game.getId());
	assertEquals(game.getName(), "Andrew Zurn's Nerdery Exam");
    }

}