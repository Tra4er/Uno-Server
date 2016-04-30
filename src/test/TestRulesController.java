package test;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.server.uno.controller.RulesController;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class TestRulesController {

	private RulesController rulesController = new RulesController(new Game());
	
	private Player zero = new Player("0", "Test_ZERO");
	private Player one = new Player("1", "Test_ONE");
	private Player two = new Player("2", "Test_TWO");
	
	@Before
	public void fillingGame() {
		HashSet<Player> players = new HashSet<>();
		players.add(zero);
		players.add(one);
		players.add(two);
		rulesController.givePlayersDeque(players);
		rulesController.setMover(rulesController.getPlayersDeque().get(1));
	}
	
	@Test
	public void testGoToNextMover() {
		assertEquals(one, rulesController.getMover());
		rulesController.goToNextMover();
		assertEquals(two, rulesController.getMover());
		rulesController.goToNextMover();
		assertEquals(zero, rulesController.getMover());
	}
}
