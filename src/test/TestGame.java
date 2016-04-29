package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class TestGame {

	public Game game;

	@Before
	public void fillingGame() {
		game = new Game();
		game.addPlayer(new Player("11", "Test_ONE"));
		game.addPlayer(new Player("22", "Test_TWO"));
	}

	@Test
	public void testStartingGame() {
		try {
			game.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("inGame", game.getStatus());
		assertEquals(2, game.rulesController.getPlayersDeque().size());
		assertNotEquals(new LinkedList<Card>(), game.rulesController.getPlayersDeque().get(0).getCards());
		try {
			assertNotEquals(null, game.getTable().getTopOpenCard());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotEquals(null, game.getMover());
	}
}
