package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;

import org.junit.BeforeClass;
import org.junit.Test;

import utils.GameUtils;

public class VotesTest extends BaseTestCase {

    private static Game game;
    private static Category category;

    @BeforeClass
    public static void setupVotes() {
	category = new Category("Test Category", null);
	category.save();

	game = new Game("Test Game", "noUrl", "noUrl", category, false,
		GameUtils.getCurrentTimestamp());
	game.save();
    }

    @Test
    public void testCreateVotes() {
	Votes votes = new Votes(GameUtils.getCurrentTimestamp(), game);
	votes.save();
	assertNotNull(votes.getId());
    }

    @Test
    public void testRemoveVotes() {
	Votes votes = new Votes(GameUtils.getCurrentTimestamp(), game);
	votes.save();
	votes.delete();

	votes = Votes.find.byId(votes.getId());
	assertNull(votes);
    }

    @Test
    public void testEditVotes() {
	Votes votes = new Votes(GameUtils.getCurrentTimestamp(), game);
	votes.save();

	votes = Votes.find.all().get(0);
	Timestamp newTime = GameUtils.getCurrentTimestamp();
	votes.setCreated(newTime);
	votes.save();

	votes = Votes.find.byId(votes.getId());
	assertEquals(votes.getCreated(), newTime);
    }

}
