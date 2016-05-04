package com.server.uno.controller;

import java.util.ArrayList;
import java.util.List;

import com.server.uno.model.Bonuses;
import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class CardsWorker {
	
	private Game game;
	
	public CardsWorker(Game game) {
		this.game = game;
	}
	
	public void putCard(Card card, Bonuses bonuses) {
		switch (card.getNumber()) {
		case 0: {
			List<Card> tempCards = game.rulesController.getPlayersDeque().get(game.rulesController.getPlayersDeque().size() - 1).getCards();
			for (Player player : game.rulesController.getPlayersDeque()) {
				List<Card> tempCards1 = new ArrayList<>();
				
				tempCards1 = player.getCards();
				player.setCards(tempCards);
				tempCards = tempCards1;
			}
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
			bonuses.addGap();
		}
			break;
		case 11: {
			game.rulesController.reversePlayersDeque();
		}
			break;
		case 12: {
			bonuses.addCardInToPool(game.getTable().getCardFromDeck());
			bonuses.addCardInToPool(game.getTable().getCardFromDeck());
		}
			break;
		case 14: {
			for(int i = 0; i < 4; i++) {
				bonuses.addCardInToPool(game.getTable().getCardFromDeck());
			}
		}
			break;
		}
		game.setTopOpenCard(card);
	}
}
