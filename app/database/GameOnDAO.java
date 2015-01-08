package database;

import java.util.Collections;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSqlBuilder;

import play.db.ebean.*;
import utils.GameUtils;
import models.Category;
import models.Game;
import models.Votes;

/**
 * This class offers common database functionalities. It should be used in place
 * of making your own calls against the database in the controller classes.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
public class GameOnDAO {

    /**
     * Find a game in the database by id that matches the GAME_ID
     * 
     * @param id
     *            the game id to be found
     * @return the game with the given id
     */
    public static Game findGameById(Long id) {
	return Game.find.byId(id);
    }

    /**
     * Find a game in the database by name that matches the GAME_NAME
     * 
     * @param name
     *            the game name to be found
     * @return the game with the given name
     */
    public static Game findGameByName(String name) {
	return Game.find.where().ilike("GAME_NAME", name).findUnique();
    }

    /**
     * Return all the games currently in the database.
     * 
     * @return a list of all games in the database.
     */
    public static List<Game> getAllGames() {
	return Game.find.all();
    }

    /**
     * Return all the unowned games in the database
     * 
     * @return a list of all unowned games in the database
     */
    public static List<Game> getAllUnownedGames() {
	List<Game> unownedGames = Game.find.where().ieq("OWNED", "0").findList();
	Collections.sort(unownedGames);
	Collections.reverse(unownedGames);
	return unownedGames;
    }
    
    /**
     * Return a list of all the owned games in the database, and
     * order them ascending by GAME_NAME.
     * 
     * @return a list of all owned games in ascending GAME_NAME order
     */
    public static List<Game> getAllOwnedGames() {
	return Game.find.where().ieq("OWNED", "1").order("GAME_NAME" + " asc")
		.findList();
    }

    /**
     * Find a vote by a given id value that matches the VOTE_ID.
     * 
     * @param id
     *            the id for the vote
     * @return the vote with the given id
     */
    public static Votes findVotesById(Long id) {
	return Votes.find.byId(id);
    }

    /**
     * Find a category by a given id value that matches the CATEGORY_ID.
     * 
     * @param id
     *            the id for the category
     * @return the category with the given id
     */
    public static Category findCategoryById(Long id) {
	return Category.find.byId(id);
    }

    /**
     * Find all categories within the database.
     * 
     * @return a list of all the categories in the database
     */
    public static List<Category> getAllCategories() {
	return Category.find.all();
    }

    /**
     * Will flip a given game's OWNED status in the database.
     * 
     * @param id
     *            the game whose OWNED status should be flipped.
     * @return return the game whose OWNED status
     */
    public static Game changeGameStatus(Long id) {
	Game game = findGameById(id);
	if (game != null) { // game exists, flip it's status
	    game.setOwned(!game.isOwned());
	    game.save();
	}
	return game;
    }

    /**
     * Return the OWNED status of a game if it exists, otherwise return false.
     * 
     * @param name
     *            the name of the game whose OWNED property is to be found
     * @return true if the game is owned, false if it is not owned or if it
     *         doesn't exist
     */
    public static boolean isGameOwned(String name) {
	Game game = findGameByName(name);
	if (game != null) {
	    return game.isOwned();
	} else {
	    return false;
	}
    }

    /**
     * Add a vote to the given game with id matching the GAME_ID
     * 
     * @param id
     *            the id of the game to recieve a vote
     * @return the vote that was saved in the database
     */
    public static Votes addGameVote(Long id) {
	Game game = findGameById(id);
	if (game != null) { // game exists, add the vote
	    Votes vote = new Votes(GameUtils.getCurrentTimestamp(), game);
	    vote.save();
	    return vote;
	}
	return null;
    }

    /**
     * Add a game to the database if the game does not already exist by the
     * given name
     * 
     * @param name
     *            the name of the new game
     * @param detailsUrl
     *            the detailsUrl of the new game
     * @param imageUrl
     *            the imageUrl of the new game
     * @param categoryId
     *            the categoryId of the new game
     * @param owned
     *            the owned status of the new game
     * @return the game if it is saved, otherwise null
     */
    public static Game addGame(String name, String detailsUrl, String imageUrl,
	    Long categoryId, boolean owned) {
	Game game = findGameByName(name);
	if (game == null) { // does not already exist
	    game = new Game(name, detailsUrl, imageUrl,
		    findCategoryById(categoryId), owned,
		    GameUtils.getCurrentTimestamp());
	    game.save();
	    return game;
	}
	return null;
    }

    /**
     * Add a game to the database if the game does not already exist by the
     * given name
     * 
     * @param game
     *            the game object of the new game
     * @return the game if it is saved, otherwise null
     */
    public static Game addGame(Game game) {
	Game duplicateGame = findGameByName(game.getName());
	if (duplicateGame == null) {
	    game.setTimestamp(GameUtils.getCurrentTimestamp());
	    game.save();
	    return game;
	}
	return null;
    }
    
}
