package com.server.uno.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class GameTable implements Cloneable {

	public static final int FULL_DECK = 108;

	private volatile int cardsInDeck = FULL_DECK;
	private volatile Deque<Card> deck = new ArrayDeque<>(FULL_DECK);
	private volatile Card topOpenCard;

	public GameTable() {
		generateDeck();
	}

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
		ArrayList<Card> temp = new ArrayList<>(deck);
		Collections.shuffle(temp);
		deck = new ArrayDeque<>(temp);
	}

	public Deque<Card> getDeck() {
		return deck;
	}

	public void setDeck(Deque<Card> deck) {
		if (deck == null)
			throw new NullPointerException("Wrong cards array");
		this.deck = deck;
	}

	public void generateDeck() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 2; j++) {
				if ((j == 0 && i == 0) || i == 13 || i == 14)
					continue;
				deck.addLast(new Card("red", i));
				deck.addLast(new Card("yellow", i));
				deck.addLast(new Card("green", i));
				deck.addLast(new Card("blue", i));
			}
			if (i > 12) {
				for (int c = 0; c < 4; c++) {
					deck.addLast(new Card("black", i));
				}
			}
		}

	}
	
	public void openCard() {
		topOpenCard = deck.pop();
	}

	public Card getTopOpenCard() throws CloneNotSupportedException {
		return topOpenCard.clone();
	}

	public void setTopOpenCard(Card topCard) {
		if (topCard == null)
			throw new NullPointerException("Wrong top card");
		this.topOpenCard = topCard;
	}
	
	@Override
	public GameTable clone() throws CloneNotSupportedException {
		return (GameTable) super.clone();
	}
}
