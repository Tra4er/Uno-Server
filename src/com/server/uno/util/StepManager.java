package com.server.uno.util;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class StepManager {

	private Game game;
	private RulesManager rulesManager;
	private CardsManager cardsManager;

	public StepManager(Game game, RulesManager rulesManager, CardsManager cardsManager) {
		this.game = game;
		this.rulesManager = rulesManager;
		this.cardsManager = cardsManager;
	}

	public synchronized void makeStep(Player player, Card card) throws Exception { // TODO
		// Multithreading
		if (rulesManager.isRightCard(card)) {
			// Checking if player is mover
			if (!rulesManager.getMover().equals(player) && game.getTable().getTopOpenCard().getNumber() == card.getNumber()
					&& game.getTable().getTopOpenCard().getColor().equals(card.getColor())) {
				rulesManager.setMover(player);
			}
			// If he is not mover punish him
			else if (!rulesManager.getMover().equals(player) && (!(game.getTable().getTopOpenCard().getNumber() == card.getNumber())
					|| !(game.getTable().getTopOpenCard().getColor().equals(card.getColor())))) {
				rulesManager.punish(player);
			}
			// Making move if he is mover
			else if (rulesManager.getMover().equals(player)) {
				if (rulesManager.getMover().isFirstMove) {
					cardsManager.putCard(card);
					rulesManager.getMover().removeCard(card);
					rulesManager.getMover().isFirstMove = false;
				} else if (!rulesManager.getMover().isFirstMove) {
					if (isRightSecondStep(card)) {
						cardsManager.putCard(card);
						rulesManager.getMover().removeCard(card);
					} else {
						rulesManager.punish(player);
					}
				}
			}
		} else {
			rulesManager.punish(player);
		}
	}

	public RulesManager getRulesManager() {
		return rulesManager;
	}

	public boolean isRightSecondStep(Card card) throws Exception {
		return card.getNumber() == game.getTable().getTopOpenCard().getNumber();
	}

	public void makeFirstStep(Player player, Card card) throws Exception {
		if (card.getColor().equals("black")) {
			card.setColor("red");
			// int color = (int) (Math.random() * 4);
			// switch (color) {
			// case 0:
			// card.setColor("red");
			// break;
			// case 1:
			// card.setColor("yellow");
			// break;
			// case 2:
			// card.setColor("green");
			// break;
			// case 3:
			// card.setColor("blue");
			// break;
			// }
		}
		rulesManager.setMover(player);
		cardsManager.putCard(card);
		rulesManager.setMover(rulesManager.getPlayersDeque().get(0));

//		rulesManager.giveBonusesToAll(player);
		if (rulesManager.isThereBonusCards) {
			if (!cardsManager.getCardsPool().isEmpty()) {
				int i = 0;
				while (i < cardsManager.getCardsPool().size()) {
					rulesManager.getMover().addCard(cardsManager.popFromCardsPool());
				}
			}
			rulesManager.isThereBonusCards = false;
		}
	}
}
