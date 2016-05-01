package com.server.uno.controller;

import com.server.uno.model.Bonuses;
import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class StepController {

	private Game game;
	private RulesController rulesController;
	private CardsWorker cardsWorker;
	private Bonuses bonuses = new Bonuses();
	
	public StepController(Game game, RulesController rulesController) {
		this.game = game;
		this.rulesController = rulesController;
		cardsWorker = new CardsWorker(game);
	}
	
	public synchronized void makeStep(Player player, Card card) {
		if(player.equals(rulesController.getMover())) {
			cardsWorker.putCard(card, bonuses);
			player.removeCard(card);
			rulesController.endMoverMove();
		}
	}
	
	public void makeFirstStep(Game game, Player player, Card card) { // TODO
		rulesController.setPrevMover(rulesController.getPlayersDeque().get(rulesController.getPlayersDeque().size() - 1));
		game.setTopOpenCard(card);
		rulesController.setMover(game.rulesController.getPlayersDeque().get(0));
	}

	public Bonuses getBonuses() {
		return bonuses;
	}
	
}
