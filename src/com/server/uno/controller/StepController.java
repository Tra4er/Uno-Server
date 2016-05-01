package com.server.uno.controller;

import com.server.uno.model.Bonuses;
import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class StepController {

	private Game game;
	private RulesController rulesController;
	private CardsWorker cardsWorker;
	private Bonuses bonuses;
	
	public StepController(Game game, RulesController rulesController) {
		this.game = game;
		this.rulesController = rulesController;
		cardsWorker = new CardsWorker(game);
	}
	
	public synchronized void makeStep(Player player, Card card) {
		if(player.equals(rulesController.getMover())) {
			bonuses = cardsWorker.putCard(card);
			player.removeCard(card);
			rulesController.endMoverMove();
		}
	}
	
	public void makeFirstStep(Game game, Player player, Card card) { // TODO
		game.setTopOpenCard(card);
		game.rulesController.setMover(game.rulesController.getPlayersDeque().get(0));
	}

	public Bonuses getBonuses() {
		return bonuses;
	}
	
}
