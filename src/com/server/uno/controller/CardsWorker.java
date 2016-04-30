package com.server.uno.controller;

import java.util.ArrayDeque;
import java.util.Deque;

import com.server.uno.model.Card;
import com.server.uno.model.Game;

public class CardsWorker {
	
	private Game game;
	
	private Deque<Card> cardsPool = new ArrayDeque<>();
	private int gaps;
	
	public CardsWorker(Game game) {
		this.game = game;
	}
	
	public void putCard(Card card) {
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
			gaps++;
		}
			break;
		case 11: {
		}
			break;
		case 12: {
			cardsPool.add(game.getTable().getCardFromDeck());
			cardsPool.add(game.getTable().getCardFromDeck());
		}
			break;
		case 14: {
			for(int i = 0; i < 4; i++) {
				cardsPool.add(game.getTable().getCardFromDeck());
			}
		}
			break;
		}
		game.setTopOpenCard(card);
	}
}
