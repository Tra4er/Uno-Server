package com.server.uno.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Bonuses {

	private ArrayList<Card> cardsPool = new ArrayList<>();
	private int gaps;
	
	public boolean isCards;
	public boolean isGaps;
	
	public void addCardInToPool(Card card) {
		isCards = true;
		cardsPool.add(card);
	}
	
	public List<Card> takeCards() {
		ArrayList<Card> cards = new ArrayList<>(cardsPool);
		cardsPool.clear();
		return cards;
	}
	
	public int getGaps() {
		return gaps;
	}
	public void addGap() {
		gaps++;
	}
	
	public int getCardsSize() {
		return cardsPool.size();
	}
	
}
