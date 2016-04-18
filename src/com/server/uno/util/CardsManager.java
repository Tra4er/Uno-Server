package com.server.uno.util;

import java.util.ArrayDeque;
import java.util.Deque;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class CardsManager {

	private Game game;
	private RulesManager rulesManager;
	private Deque<Card> cardsPool = new ArrayDeque<>();

	public CardsManager(Game game, RulesManager rulesManager) {
		this.game = game;
		this.rulesManager = rulesManager;
	}

	public void putCard(Card card) throws Exception {
		if (card == null)
			throw new IllegalArgumentException("Wrong player or card");
		
		Player mover = rulesManager.getMover();

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
				mover.tamer = true;
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
				cardsPool.push(game.getTable().getCardFromDeck());
				cardsPool.push(game.getTable().getCardFromDeck());
				rulesManager.isThereBonus = true;
			}
				break;
			case 14: {
				for (int i = 0; i < 4; i++) {
					cardsPool.push(game.getTable().getCardFromDeck());
				}
				rulesManager.isThereBonus = true;
			}
				break;
		}
		mover.isFirstMove = false;
		game.setTopOpenCard(card);
	}

	public Deque<Card> getBonusCards() {
		// TODO with removing cards from list(Deque)
		return null;
	}

	public Deque<Card> getCardsPool() {
		return new ArrayDeque<Card>(cardsPool);
	}
	
	public Card popFromCardsPool() {
		return cardsPool.pop();
	}
}
