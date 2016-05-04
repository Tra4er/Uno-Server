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

	public synchronized boolean makeStep(Player player, Card card) {
		Card topCard = game.getTable().getTopOpenCard();
		if (player.equals(rulesController.getMover())) {
			if (card.getColor().equals(topCard.getColor()) || card.getNumber() == topCard.getNumber() || card.getNumber() > 12) {
				player.removeCard(card);
				cardsWorker.putCard(card, bonuses);
				return true;
			}
		}
		if (player.equals(rulesController.getPrevMover())) {
			if (card.getNumber() == game.getTable().getTopOpenCard().getNumber()) {
				if (card.getNumber() == 10)
					bonuses.removeGap();
				player.removeCard(card);
				cardsWorker.putCard(card, bonuses);
				return true;
			}
		}
		return false;
	}

	public void makeFirstStep(Game game, Player player, Card card) {
		if(card.getColor().equals("black")) {
			int color = (int) (Math.random() * 4);
			switch (color) {
			case 0:
				card.setColor("red");
				break;
			case 1:
				card.setColor("yellow");
				break;
			case 2:
				card.setColor("green");
				break;
			case 3:
				card.setColor("blue");
				break;
			}
		}
		rulesController.setMover(player);
		cardsWorker.putCard(card, bonuses);
//		game.setTopOpenCard(card);
		game.setTopOpenCard(new Card("green", 6));
	}

	public Bonuses getBonuses() {
		return bonuses;
	}

}
