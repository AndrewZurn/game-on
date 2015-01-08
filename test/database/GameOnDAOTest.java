package database;

import static org.junit.Assert.*;
import models.BaseTestCase;
import models.Category;
import models.Game;
import models.Votes;

import org.junit.BeforeClass;
import org.junit.Test;

import utils.GameUtils;

public class GameOnDAOTest extends BaseTestCase {

    private static Category category;
    private static Game game;

    @BeforeClass
    public static void setupGame() {
	category = new Category("Test Category", null);
	category.save();

	game = new Game("Andrew's Other Fake Game", "http://andrewzurn.com",
		"http://andrewzurn.com/image", category, false,
		GameUtils.getCurrentTimestamp());
	game.save();
    }

    @Test
    public void testChangeGameStatus() {
	assertEquals(game.isOwned(), false);

	Game nowOwned = GameOnDAO.changeGameStatus(game.getId());
	assertEquals(nowOwned.isOwned(), true);
    }

    @Test
    public void testAddGameVote() {
	Votes vote = GameOnDAO.addGameVote(game.getId());
	assertNotNull(vote);

	game = GameOnDAO.findGameById(game.getId());
	assertEquals(game.getVotes().size(), 1);
    }

    @Test
    public void testAddGameByGameIsNull() {
	Game newGame = new Game("Andrew's Fake Game Is Null",
		"http://andrewzurn.com", "http://andrewzurn.com/image",
		category, false, GameUtils.getCurrentTimestamp());
	newGame = GameOnDAO.addGame(newGame);
	assertNotNull(newGame);

	Game otherNewGame = new Game("Andrew's Fake Game Is Null",
		"http://andrewzurn.com", "http://andrewzurn.com/image",
		category, false, GameUtils.getCurrentTimestamp());
	otherNewGame = GameOnDAO.addGame(otherNewGame);
	assertNull(otherNewGame);
    }

    @Test
    public void testAddGameByGame() {
	Game newGame = new Game("Andrew's Fake Game", "http://andrewzurn.com",
		"http://andrewzurn.com/image", category, false,
		GameUtils.getCurrentTimestamp());
	newGame = GameOnDAO.addGame(newGame);
	assertNotNull(newGame);

	newGame = GameOnDAO.findGameById(newGame.getId());
	assertNotNull(newGame);
    }

    @Test
    public void testAddGameByParams() {
	Game addedGame = GameOnDAO.addGame("Andrew's New Fake Game",
		"http://andrewzurn.com", "http://andrewzurn.com/image",
		category.getId(), false);
	assertNotNull(addedGame);
    }

    @Test
    public void testAddGameByParamsIsNull() {
	Game addedGame = GameOnDAO.addGame("Andrew's New Fake Game Dup",
		"http://andrewzurn.com", "http://andrewzurn.com/image",
		category.getId(), false);
	assertNotNull(addedGame);

	Game otherAddedGame = GameOnDAO.addGame("Andrew's New Fake Game Dup",
		"http://andrewzurn.com", "http://andrewzurn.com/image",
		category.getId(), false);
	assertNull(otherAddedGame);
    }

}