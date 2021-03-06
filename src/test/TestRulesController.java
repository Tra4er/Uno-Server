package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
	private Player three = new Player("3", "Test_THREE");
	private Player four = new Player("4", "Test_FOUR");

	@Before
	public void fillingGame() {
		ArrayList<Player> players = new ArrayList<>();
		players.add(zero);
		one.setPosition(1);
		players.add(one);
		two.setPosition(2);
		players.add(two);
		// three.setPosition(3);
		// players.add(three);
		// four.setPosition(4);
		// players.add(four);
		rulesController.setPlayersDeque(players);

		rulesController.setMover(rulesController.getPlayersDeque().get(1));

		for (Player player : rulesController.getPlayersDeque()) {
			player.addCard(new Card("green", 0));
		}

		game.setTopOpenCard(new Card("yellow", 12));

		rulesController.setMover(rulesController.getPlayersDeque().get(2));

	}

	@Test
	public void testGiveBonuses() {
		bonuses.addCardInToPool(new Card("red", 2));
		bonuses.addCardInToPool(new Card("red", 2));

		assertEquals(1, rulesController.getNextPlayer().getCards().size()); // mover
																			// 0
		makeMove(); // next is mover 0

		assertEquals(3, rulesController.getMover().getCards().size()); // mover
																		// 0
		assertEquals(0, bonuses.getCardsSize());

		bonuses.addCardInToPool(new Card("red", 2));
		bonuses.addCardInToPool(new Card("red", 2));

		rulesController.getPlayersDeque().get(1).addCard(new Card("blue", 12));

		makeMove(); // next is mover 1

		bonuses.addCardInToPool(new Card("red", 2));
		bonuses.addCardInToPool(new Card("red", 2));

		assertEquals(2, rulesController.getMover().getCards().size()); // mover
																		// 1
		assertEquals(4, bonuses.getCardsSize());

		makeMove(); // next is mover 2

		assertEquals(5, rulesController.getMover().getCards().size()); // mover
																		// 1
		assertEquals(0, bonuses.getCardsSize());
	}

	//
	// @Test
	// public void testGap() {
	// bonuses.addGap();
	//
	// assertEquals(two, rulesController.getMover());
	//
	// makeMove();
	//
	// assertEquals(one, rulesController.getMover());
	// }
	//
	@Test
	public void testGoToNextMover() {
		assertEquals(two, rulesController.getMover());
		rulesController.goToNextMover();
		assertEquals(zero, rulesController.getMover());
		rulesController.goToNextMover();
		assertEquals(one, rulesController.getMover());
		bonuses.addGap();
		rulesController.giveNextMoverBonuses(bonuses);
		rulesController.goToNextMover();
		assertEquals(zero, rulesController.getMover());
	}

	private void makeMove() {
		rulesController.giveNextMoverBonuses(bonuses);
		rulesController.goToNextMover();
	}
}
