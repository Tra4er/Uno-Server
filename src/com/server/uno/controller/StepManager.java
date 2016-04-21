package com.server.uno.controller;

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
			if (!rulesManager.moversManager.getMover().equals(player)
					&& game.getTable().getTopOpenCard().getNumber() == card.getNumber()
					&& game.getTable().getTopOpenCard().getColor().equals(card.getColor())) {
				rulesManager.moversManager.setMover(player);
			}
			
			// If catcher is previousMover
			else if (player.equals(rulesManager.moversManager.getPreviousMover())
					&& game.getTable().getTopOpenCard().getNumber() == card.getNumber()) {
				rulesManager.moversManager.setMover(player);
			}
			
			// If catcher is having wrong card punish
			else if (!rulesManager.moversManager.getMover().equals(player)
					&& (game.getTable().getTopOpenCard().getNumber() != card.getNumber()
							|| !(game.getTable().getTopOpenCard().getColor().equals(card.getColor())))) {
				rulesManager.punish(player);
			}
			
			// Making move if he is mover
			if (rulesManager.moversManager.getMover().equals(player)) { // removed else
				if (rulesManager.moversManager.getMover().isFirstMove) {
					cardsManager.putCard(card);
					rulesManager.moversManager.getMover().removeCard(card);
					rulesManager.moversManager.getMover().isFirstMove = false;
					return true;
				} else if (!rulesManager.moversManager.getMover().isFirstMove) {
					if (rulesManager.isRightSecondCard(card)) {
						cardsManager.putCard(card);
						rulesManager.moversManager.getMover().removeCard(card);
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
		// Init giglet for meth makeStep()
		rulesManager.moversManager.setPreviousMover(player);
		
		rulesManager.moversManager.setMover(player);
		rulesManager.moversManager.setNextMover(player);
		cardsManager.putCard(card);
		rulesManager.moversManager.setMover(rulesManager.getPlayersDeque().get(0));

		// rulesManager.giveBonusesToAll(player);
	}

	public RulesManager getRulesManager() {
		return rulesManager;
	}
}
