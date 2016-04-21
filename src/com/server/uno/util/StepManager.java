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

	public synchronized boolean makeStep(Player player, Card card) throws Exception { // TODO
		// Multithreading
		if (rulesManager.isRightCard(card)) {
			// Checking if player is catching step
			if (!rulesManager.getMover().equals(player) && game.getTable().getTopOpenCard().getNumber() == card.getNumber()
					&& game.getTable().getTopOpenCard().getColor().equals(card.getColor()) || (player.equals(rulesManager.getPreviousMover()) 
							&& game.getTable().getTopOpenCard().getNumber() == card.getNumber())) {
				rulesManager.setMover(player);
			}
			// If he is having wrong card punish
			else if (!rulesManager.getMover().equals(player) && (!(game.getTable().getTopOpenCard().getNumber() == card.getNumber())
					|| !(game.getTable().getTopOpenCard().getColor().equals(card.getColor())))) {
				rulesManager.punish(player);
			}
			// Making move if he is mover
			if (rulesManager.getMover().equals(player)) { // removed else
				if (rulesManager.getMover().isFirstMove) {
					cardsManager.putCard(card);
					rulesManager.getMover().removeCard(card);
					rulesManager.getMover().isFirstMove = false;
					return true;
				} else if (!rulesManager.getMover().isFirstMove) {
					if (rulesManager.isRightSecondCard(card)) {
						cardsManager.putCard(card);
						rulesManager.getMover().removeCard(card);
						return true;
					} else {
						rulesManager.punish(player);
					}
				}
			}
		} else {
			rulesManager.punish(player);
		}
		return false;
	}

	public RulesManager getRulesManager() {
		return rulesManager;
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
		rulesManager.setNextMover(rulesManager.getPlayersDeque().get(0));
		cardsManager.putCard(card);
		rulesManager.setMover(rulesManager.getPlayersDeque().get(0));

//		rulesManager.giveBonusesToAll(player);
	}
}
