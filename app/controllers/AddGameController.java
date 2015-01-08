package controllers;

import utils.GameUtils;
import views.html.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import models.Game;
import play.data.Form;
import play.db.ebean.Transactional;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.Cookie;

import com.fasterxml.jackson.databind.JsonNode;

import database.GameOnDAO;

/**
 * The controller for the 'Add a Title' feature of the 'Game-On' app. The
 * functionality of this controller is mainly to search for a given game name
 * against a Microsoft webservice, return a list of results for a user to choose
 * from, then allow them to save their choosen game to the database.
 * 
 * Upon successful save to the database, a cookie will be stashed on the user's
 * browser so they cannot vote or add another game until the next day (12:01am).
 * A vote will also be added to their game.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
public class AddGameController extends Controller {

    private static final String FORM_SEARCH_TERM = "searchTerm";
    private static final String REDIRECT_GAMES = "/games";
    private static final String REDIRECT_GAMES_SEARCH = "/games/search";
    
    private static final String FLASH_ADD_STATUS = "add_status";
    private static final String FLASH_SEARCH_ERROR = "search_error";
    private static final String FLASH_ADD_STATUS_VOTE_ERROR_MESSAGE = "You can only add or vote once per day, please try again tomorrow.";
    private static final String FLASH_ADD_STATUS_ERROR_MESSAGE = "%s could not be added, please try adding again later";
    private static final String FLASH_ADD_STATUS_MESSAGE = "%s was added";
    private static final String FLASH_SEARCH_ERROR_MESSAGE = "An error in finding your game occurred, please try searching again.";
    private static final String FLASH_SEARCH_EMPTY_ERROR_MESSAGE = "Please enter a game title to search for.";
    private static final String FLASH_SEARCH_EMPTY_RESULT_MESSAGE = "No results were found for '%s', please try another game.";
    private static final String FLASH_VOTE_STATUS_WEEKEND_ERROR = "You cannot vote on Saturday or Sunday, please visit again on Monday.";

    /**
     * The game form that will be used through the adding process
     */
    private static Form<Game> gameForm = Form.form(Game.class);

    /**
     * Will hold the results of the webservice call to be used within the same
     * search period.
     */
    private static List<Game> resultsList;

    /**
     * Default constructor
     */
    public AddGameController() {
	super();
    }

    /**
     * The starting point for the 'Add a Title' functionality. Will render the
     * addTitle page, with a list of empty results.
     * 
     * The addTitle page is reused later with a list of results returned from
     * the webservice call.
     * 
     * @return HTTP 200 - addTitle.scala.html page
     */
    @Transactional
    public static Result index() {
	return ok(addTitle.render(gameForm, null));
    }

    /**
     * This will retrieve the search term from the addTitle page, and call on a
     * Microsoft webservice to get the results that match the given Xbox 360
     * name. It will then display the list of results on the addTitle page for a
     * user to select from.
     * 
     * If an error occurs and no list is given, it will redirect the user back
     * to the addTitle page to all them to search again.
     * 
     * @throws UnsupportedEncodingException bad url encoding occurred
     * @return HTTP 200 - addTitle.scala.html
     */
    @Transactional
    public static Result searchForTitle() throws UnsupportedEncodingException {
	List<Game> returnList = new ArrayList<Game>();
	gameForm = gameForm.bindFromRequest();

	// get search criteria from form
	String searchTerm = gameForm.field(FORM_SEARCH_TERM).value();
	if (searchTerm.equals("")) {
	    flash(FLASH_SEARCH_ERROR, FLASH_SEARCH_EMPTY_ERROR_MESSAGE);
	    return redirect(REDIRECT_GAMES_SEARCH);
	}

	searchTerm = URLEncoder.encode(searchTerm, "UTF-8");

	// call the webservice with the given search term
	JsonNode gameAsJson = GameUtils.callGameWebservice(searchTerm);

	// if there was a response that was valid from the webservice
	if (!GameUtils.isJsonEmpty(gameAsJson)) {

	    // parse for title, detailsUrl, and imageUrl
	    resultsList = GameUtils.parseGameInfo(gameAsJson);

	    return ok(addTitle.render(gameForm, resultsList));
	} else {
	    flash(FLASH_SEARCH_ERROR, String.format(FLASH_SEARCH_EMPTY_RESULT_MESSAGE, searchTerm));
	    return redirect(REDIRECT_GAMES_SEARCH);
	}

    }

    /**
     * This will get the users chosen game from the previously returned list,
     * and give it to the titleSelected page.
     * 
     * If a user tries to manipulate the result of this call, or an error
     * occurs, they will be redirected to the beginning of this process to start
     * searching over again.
     * 
     * @param gameName
     *            the name of the game the user selected
     * @return On Error: HTTP 302 - addTitle.scala.html. On Success: HTTP 200 -
     *         titleSelected.scala.html
     */
    public static Result titleSelected(String gameName) {
	Game game = findGameByName(gameName);

	// if some sort of error and game not found, or if url
	// was manipulated to get here, as results list should be empty
	if (game == null) {
	    flash(FLASH_SEARCH_ERROR, FLASH_SEARCH_ERROR_MESSAGE);
	    return redirect(REDIRECT_GAMES_SEARCH);
	}

	// add the selected game to the form to be displayed on next page
	gameForm = gameForm.fill(game);

	return ok(titleSelected.render(gameForm, game,
		GameOnDAO.getAllCategories()));
    }

    /**
     * This will perform some final checks, and attempt to add the game the user
     * has selected to the database.
     * 
     * If the game is not already in the database, the game will be saved, a
     * vote will be added to it, and a cookie will be set on the user's browser.
     * 
     * @return On Error: HTTP 400 - addTitle.scala.html. On Success: HTTP 200 -
     *         index.scala.html
     */
    @Transactional
    public static Result addTitle() {

	// check to see if they can vote
	Cookie cookie = request().cookie("gameon_vote");
	if (!GameUtils.canVoteOrAdd(cookie) ) {
	    flash(FLASH_ADD_STATUS,
		    FLASH_ADD_STATUS_VOTE_ERROR_MESSAGE);
	    return redirect(REDIRECT_GAMES);
	} else if (GameUtils.isWeekend()) {
	    flash(FLASH_ADD_STATUS, FLASH_VOTE_STATUS_WEEKEND_ERROR);
	    return redirect(REDIRECT_GAMES);
	}

	if (gameForm.hasErrors()) {
	    flash(FLASH_SEARCH_ERROR,
		    "Bad data was found, please try your search again.");
	    return badRequest(addTitle.render(gameForm, null));
	}

	// get the game and chosen category from the form
	Game game = gameForm.get();

	// rebind the request to get any additional data
	gameForm = gameForm.bindFromRequest();

	// get the choosen category and add it to the game
	Long categoryId = Long.parseLong(gameForm.field("categoryId").valueOr(
		"1"));
	game.setCategory(GameOnDAO.findCategoryById(categoryId));

	// save the game
	Game wasAdded = GameOnDAO.addGame(game);

	// if game save was successful, add a vote a cookie to user
	if (wasAdded != null) { // success
	    flash(FLASH_ADD_STATUS,
		    String.format(FLASH_ADD_STATUS_MESSAGE, game.getName()));

	    // add a vote and add cookie
	    GameOnDAO.addGameVote(wasAdded.getId());
	    response().setCookie("gameon_vote", "voted",
		    GameUtils.timeToMidnight());
	} else { // already exists, or some other error
	    flash(FLASH_ADD_STATUS,
		    String.format(FLASH_ADD_STATUS_ERROR_MESSAGE,
			    game.getName()));
	}

	cleanup();

	return redirect(REDIRECT_GAMES);
    }

    /**
     * Determine if the game was previously found in the search results from the
     * webservice, and then return it. Otherwise, return null.
     * 
     * @param gameName
     *            the game name the user wants to add to the database
     * @return the given game that was returned from the webservice, otherwise
     *         null
     */
    private static Game findGameByName(String gameName) {
	for (Game game : resultsList) {
	    if (gameName.equalsIgnoreCase(game.getName())) {
		return game;
	    }
	}
	return null;
    }

    /**
     * Clean up the controller in order for it to be used again without and
     * state being saved from a previously added game.
     */
    private static void cleanup() {
	resultsList = null;
	gameForm = Form.form(Game.class);
    }

}
