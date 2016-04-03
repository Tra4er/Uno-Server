package com.server.uno.util;

import java.util.ArrayDeque;
import java.util.Deque;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class CardsManager {

	private Game game;
	private Deque<Card> bonusCards = new ArrayDeque<>();

	public CardsManager(Game game) {
		this.game = game;
	}

	public void putCard(Player player, Card card) {
		if (player == null || card == null)
			throw new IllegalArgumentException("Wrong player or card");

//		if (RulesManager.checkStep(game, card)) {
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
				player.tamer = true;
			}
				break;
			case 10: {
				// TODO
			}
				break;
			case 11: {
				// TODO
			}
				break;
			case 12: {
				bonusCards.add(game.getTable().getCardFromDeck());
				bonusCards.add(game.getTable().getCardFromDeck());
			}
				break;
			case 14: {
				for (int i = 0; i < 4; i++) {
					bonusCards.add(game.getTable().getCardFromDeck());
				}
			}
				break;
//			}
//			player.removeCard(card);
//			game.getTable().setTopOpenCard(card);
//			game.changed = true;
		}
	}

	public Deque<Card> getBonusCards() {
		// TODO with removing cards from list(Deque)
		return null;
	}
}
