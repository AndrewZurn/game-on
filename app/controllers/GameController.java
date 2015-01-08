package controllers;

import java.util.Iterator;

import utils.GameUtils;
import views.html.*;
import models.Game;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Http.Cookie;
import play.mvc.Result;
import database.GameOnDAO;

/**
 * The controller for the 'Vote' feature of the 'Game-On' app. This controller
 * will display the voting page of the application, and add a new vote the
 * user's chosen game.
 * 
 * If the user can vote, a cookie will be stashed on the user's browser so they
 * cannot vote or add another game until the next day (12:01am).
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
public class GameController extends Controller {

    private static final String REDIRECT_GAMES = "/games";

    private static final String COOKIE_VALUE = "voted";
    private static final String COOKIE_NAME = "gameon_vote";

    private static final String FLASH_VOTE_STATUS = "vote_status";
    private static final String FLASH_VOTE_STATUS_SUCCESS = "Thanks for voting for %s, please vote again tomorrow.";
    private static final String FLASH_VOTE_STATUS_ERROR = "You cannot vote more than once per day, or please visit again tomorrow.";
    private static final String FLASH_VOTE_STATUS_WEEKEND_ERROR = "You cannot vote on Saturday or Sunday, please visit again on Monday.";

    /**
     * Default constructor
     */
    public GameController() {
	super();
    }

    /**
     * Will return a user to the Home page, where info on the application is
     * displayed
     * 
     * @return HTTP 200 - home.scala.html
     */
    public static Result index() {
	return ok(home.render());
    }

    /**
     * The starting point for the 'Vote' functionality of controller.
     * 
     * Will display a list of all 'unowned' games, and owned games
     * at the bottom of the page.
     * 
     * @return HTTP 200 - index.scala.html
     */
    @Transactional
    public static Result voteIndex() {
	return ok(vote.render(GameOnDAO.getAllUnownedGames(), GameOnDAO.getAllOwnedGames()));
    }

    /**
     * If a user can vote, add a vote to the game with the given gameId.
     * 
     * @param gameId
     *            matching GAME_ID
     * @return HTTP 200 - index.scala.html
     */
    @Transactional
    public static Result addGameVote(Long gameId) {

	// check to see if they can vote
	Cookie cookie = request().cookie(COOKIE_NAME);
	if (!GameUtils.canVoteOrAdd(cookie)) {
	    flash(FLASH_VOTE_STATUS, FLASH_VOTE_STATUS_ERROR);
	    return redirect(REDIRECT_GAMES);
	} else if (GameUtils.isWeekend()) {
	    flash(FLASH_VOTE_STATUS, FLASH_VOTE_STATUS_WEEKEND_ERROR);
	    return redirect(REDIRECT_GAMES);
	}

	// add a vote to the given game and add cookie
	flash(FLASH_VOTE_STATUS,
		String.format(FLASH_VOTE_STATUS_SUCCESS,
			GameOnDAO.findGameById(gameId).getName()));
	GameOnDAO.addGameVote(gameId);
	response().setCookie(COOKIE_NAME, COOKIE_VALUE,
		GameUtils.timeToMidnight());

	return redirect(REDIRECT_GAMES);
    }

}
