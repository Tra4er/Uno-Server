package com.server.uno.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameTable {
	
	public static final int FULL_DECK = 107;
	
	private volatile int cardsInDeck = FULL_DECK;
	private volatile Deque<Card> deck = new ArrayDeque<>(FULL_DECK);
	private volatile Card topOpenCard;
	
	/**
	 * @return Returns number of cards in deck. Count starts from 0.
	 */
	public int getNumberOfCardsInDeck() {
		return cardsInDeck; 
	}
	
	public Card getCardFromDeck() {
		cardsInDeck--;
		return deck.pop();
	}
	
	public void shuffleDeck() {
		// TODO IMLP
	}

	public Deque<Card> getDeck() {
		return deck;
	}

	public void setDeck(Deque<Card> deck) {
		if(deck == null)
			throw new NullPointerException("Wrong cards array");
		this.deck = deck;
	}

	public Card getTopOpenCard() {
		return topOpenCard;
	}

	public void setTopOpenCard(Card topCard) {
		if(topCard == null) 
			throw new NullPointerException("Wrong top card");
		this.topOpenCard = topCard;
	}
}
