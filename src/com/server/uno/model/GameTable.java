package com.server.uno.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class GameTable implements Cloneable {

	public static final int FULL_DECK = 108;

	private volatile Deque<Card> deck = new ArrayDeque<>(FULL_DECK);
	private volatile Card topOpenCard;
	private volatile ArrayList<Card> discardPile = new ArrayList<>();

	public GameTable() {
		generateDeck();
	}

	public Card getCardFromDeck() {
		if (deck.size() == 0) {
			if(discardPile.size() == 0)
				return null;
			shuffleDiscardPile();
		}
		return deck.pop();
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
		shuffleNewDeck();
	}

	private void shuffleNewDeck() {
		ArrayList<Card> temp = new ArrayList<>(deck);
		Collections.shuffle(temp);
		deck = new ArrayDeque<>(temp);
	}

	private void shuffleDiscardPile() {
		Collections.shuffle(discardPile);
		System.out.println(discardPile);
		deck = new ArrayDeque<>(discardPile);
	}

	public Deque<Card> getDeck() {
		return new ArrayDeque<Card>(deck);
	}

	public Card getTopOpenCard() {
		return topOpenCard;
	}

	public void setTopOpenCard(Card topCard) {
		if (topCard == null)
			throw new NullPointerException("Wrong top card");

		discardPile.add(topOpenCard);
		topOpenCard = topCard;
	}

	@Override
	public GameTable clone() throws CloneNotSupportedException {
		return (GameTable) super.clone();
	}
}
