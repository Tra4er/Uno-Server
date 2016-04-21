package com.server.uno.util;

import java.util.ArrayDeque;
import java.util.Deque;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class CardsManager {

	private Game game;
	private RulesManager rulesManager;

	public CardsManager(Game game, RulesManager rulesManager) {
		this.game = game;
		this.rulesManager = rulesManager;
	}

	public void putCard(Card card) throws Exception {
		if (card == null)
			throw new IllegalArgumentException("Wrong card");

		int gaps = 0;
		
		switch (card.getNumber()) {
		case 0: {
			// TODO
		}
			break;
		case 5: {
			// TODO
		}
			break;
		case 7: {
			// TODO
		}
			break;
		case 10: {
			rulesManager.setNextMover(rulesManager.getNextStepPlayer(rulesManager.getNextMover()));
			rulesManager.isGap = true;
		}
			break;
		case 11: {
			rulesManager.isReverse = true;
		}
			break;
		case 12: {
			rulesManager.giveNextPlayerCard();
			rulesManager.giveNextPlayerCard();
		}
			break;
		case 14: {
			for (int i = 0; i < 4; i++) {
				rulesManager.giveNextPlayerCard();
			}
		}
			break;
		}
		game.setTopOpenCard(card);
	}
}
