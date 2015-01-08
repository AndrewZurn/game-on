package utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Game;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Http.Cookie;

import com.fasterxml.jackson.databind.JsonNode;

import database.GameOnDAO;

/**
 * A utilities class to store common code to be used through the application.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/19/2014
 */
public class GameUtils {

    private static final String COOKIE_VALUE = "voted";
    private static final String XBOX_BASE_URL = "http://marketplace.xbox.com";
    private static final String JSON_KEY_IMAGE_URL = "image";
    private static final String JSON_KEY_DETAILS_URL = "detailsUrl";
    private static final String JSON_KEY_TITLES = "title";
    private static final String JSON_KEY_ENTRIES = "entries";

    /**
     * The base search url against a Microsoft webservice to return a list of
     * known Xbox 360 games.
     */
    private static final String SEARCH_URL = "http://marketplace.xbox.com/en-US/SiteSearch/xbox/?PageSize=10&query=";

    /**
     * Time-out for webservice constant.
     */
    private static final long THIRTY_SECONDS = 30000;

    /**
     * Get the current time
     * 
     * @return the current time
     */
    public static Timestamp getCurrentTimestamp() {
	return new Timestamp(new Date().getTime());
    }

    /**
     * Call the Microsoft webservice with a given searchTerm, which will return
     * a list of found games matching the criteria in a Json format.
     * 
     * @param searchTerm
     *            the game to be searched for
     * @return the results of the search
     */
    public static JsonNode callGameWebservice(String searchTerm) {
	try {
	    F.Promise<WSResponse> wsPromise = WS.url(SEARCH_URL + searchTerm)
		    .get();
	    return wsPromise.get(THIRTY_SECONDS).asJson();
	} catch (RuntimeException re) {
	    // error in calling the webservice, return null
	    return null;
	}
    }

    /**
     * Parse a list of games returned from the webservice, return a list of
     * games.
     * 
     * @param gamesAsJson
     *            the json that was returned from the webservice
     * @return the list of games.
     */
    public static List<Game> parseGameInfo(JsonNode gamesAsJson) {
	List<Game> parsedGames = new ArrayList<Game>();

	JsonNode gameEntries = gamesAsJson.findPath(JSON_KEY_ENTRIES);
	List<String> titles = gameEntries.findValuesAsText(JSON_KEY_TITLES);
	List<String> detailUrls = gameEntries
		.findValuesAsText(JSON_KEY_DETAILS_URL);
	List<String> imageUrls = gameEntries
		.findValuesAsText(JSON_KEY_IMAGE_URL);

	// entries should have the same amount of elements
	if (titles.size() == detailUrls.size()
		&& detailUrls.size() == imageUrls.size()) {
	    
	    for (int i = 0; i < titles.size(); i++) {
		
		    
		Game game = GameOnDAO.findGameByName(titles.get(i));
		if (game != null) { 		//we already have game in database, add it
		    parsedGames.add(game);	//needed to check later if an id exists already
		    continue;			//and prevent duplicate entry
		}
		
		parsedGames.add(new Game(titles.get(i), XBOX_BASE_URL
			+ detailUrls.get(i), imageUrls.get(i), null, GameOnDAO
			.isGameOwned(titles.get(i)), null));
	    }
	}

	return parsedGames;
    }

    /**
     * Given a cookie, will look for the value 'voted', and will return false if
     * found (cannot vote), otherwise will return true (can vote).
     * 
     * @param cookie
     *            the gameon_vote cookie
     * @return false if found and value is voted, else true
     */
    public static boolean canVoteOrAdd(Cookie cookie) {
	if (cookie != null) {
	    if (cookie.value().equalsIgnoreCase(COOKIE_VALUE)) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Will return true if it is a Saturday or Sunday, else false.
     * 
     * @return true if a day in the weekend, otherwise false.
     */
    public static boolean isWeekend() {
	Calendar calendar = Calendar.getInstance();

	int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
	    return true;
	}
	return false;
    }

    /**
     * Will return the time in seconds to midnight
     * 
     * @return integer holding value of seconds to midnight
     */
    public static Integer timeToMidnight() {
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DAY_OF_MONTH, 1);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	Integer howMany = (int) (c.getTimeInMillis() - System
		.currentTimeMillis()) / 1000;

	return howMany;
    }

    /**
     * Will look to see how many entries were returned in our
     * json response, and if total entries is 0, will return true,
     * else it will return false.
     * @param gameAsJson the json response from the webservice.
     * @return true if it is empty, otherwise false.
     */
    public static boolean isJsonEmpty(JsonNode gameAsJson) {
	String totalEntries = gameAsJson.findPath("totalEntries").asText();

	if (totalEntries.equals("0")) {
	    return true;
	}
	return false;
    }

}
