package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.server.uno.controller.RulesController;
import com.server.uno.model.Bonuses;
import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class TestRulesController {

	private Game game = new Game();
	private RulesController rulesController = new RulesController(game);
	private Bonuses bonuses = new Bonuses();
	
	private Player zero = new Player("0", "Test_ZERO");
	private Player one = new Player("1", "Test_ONE");
	private Player two = new Player("2", "Test_TWO");
	
	@Before
	public void fillingGame() {
		ArrayList<Player> players = new ArrayList<>();
		players.add(zero);
		one.setPosition(1);
		players.add(one);
		two.setPosition(2);
		players.add(two);
		rulesController.setPlayersDeque(players);
		
		for (Player player : rulesController.getPlayersDeque()) {
			player.addCard(new Card("green", 0));
		}
		
		game.setTopOpenCard(new Card("yellow", 12));
		
		rulesController.setMover(rulesController.getPlayersDeque().get(1));
		
		bonuses.addCardInToPool(new Card("red", 2));
		bonuses.addCardInToPool(new Card("red", 2));
		bonuses.addGap();
	}
	
	@Test
	public void testGiveBonuses() {
		rulesController.endMoverMove();
		rulesController.goToNextMover();
//		FORGET ABOUT THIS PART
		
		assertEquals(1, rulesController.getNextMover().getCards().size()); // mover 0
		
		makeMove(); // next is mover 0
		
		assertEquals(3, rulesController.getMover().getCards().size()); // mover 0
		assertEquals(0, bonuses.getCardsSize());
		
		bonuses.addCardInToPool(new Card("red", 2));
		bonuses.addCardInToPool(new Card("red", 2));
		
		rulesController.getPlayersDeque().get(1).addCard(new Card("blue", 12));
		
		makeMove(); // next is mover 1
		
		bonuses.addCardInToPool(new Card("red", 2));
		bonuses.addCardInToPool(new Card("red", 2));
		
		assertEquals(2, rulesController.getMover().getCards().size()); // mover 1
		assertEquals(4, bonuses.getCardsSize());
		
		makeMove(); // next is mover 2
		System.out.println(bonuses.getCardsSize());

		assertEquals(5, rulesController.getMover().getCards().size()); // mover 1
		assertEquals(0, bonuses.getCardsSize());
	}
	
	@Test
	public void testGoToNextMover() {
		assertEquals(one, rulesController.getMover());
		rulesController.endMoverMove();
		rulesController.goToNextMover();
		assertEquals(two, rulesController.getMover());
		rulesController.endMoverMove();
		rulesController.goToNextMover();
		assertEquals(zero, rulesController.getMover());
	}
	
	@After
	public void cleaning() {
		rulesController = null;
		bonuses = null;
	}
	
	private void makeMove() {
		rulesController.endMoverMove();
		rulesController.giveNextMoverBonuses(bonuses); 
		rulesController.goToNextMover();
	}
}
