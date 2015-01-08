package controllers;

import views.html.*;
import database.GameOnDAO;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * The controller for the 'Change Status' functionality of the 'Game-On' app.
 * 
 * This controller will allow a user to change the current status of a game from
 * not owned to owned and vice versa.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
public class ChangeGameStatusController extends Controller {

    private static final String REDIRECT_GAMES_STATUS = "/games/status";

    /**
     * Default constructor
     */
    public ChangeGameStatusController() {
	super();
    }

    /**
     * The starting point for the 'Change Status' functionality of controller.
     * 
     * Will display a list of all 'unowned' games, and owned games
     * at the bottom of the page.
     * 
     * @return HTTP 200 - changeStatus.scala.html
     */
    @Transactional
    public static Result index() {
	return ok(changeStatus.render(GameOnDAO.getAllUnownedGames(), GameOnDAO.getAllOwnedGames()));
    }

    /**
     * Will flip the current 'OWNED' status of the game with the given gameId
     * that matches GAME_ID.
     * 
     * @param gameId the gameId of a given game
     * @return HTTP 200 - changeStatus.scala.html
     */
    @Transactional
    public static Result changeGameStatus(Long gameId) {
	GameOnDAO.changeGameStatus(gameId);
	return redirect(REDIRECT_GAMES_STATUS);
    }

}
