package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Game;

import org.json.Cookie;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class GameUtilsTest {

    private static final String SEARCH_URL = "http://marketplace.xbox.com/en-US/SiteSearch/xbox/?PageSize=10&query=Halo%204";

    @Test
    public void testGetCurrentTimestamp() {
	assertEquals(GameUtils.getCurrentTimestamp(),
		new Timestamp(new Date().getTime()));
    }

}
