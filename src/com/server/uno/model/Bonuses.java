package com.server.uno.model;

import java.util.ArrayList;
import java.util.List;

public class Bonuses {

	private ArrayList<Card> cardsPool = new ArrayList<>();
	private int gaps;
	
	public boolean isCards;
	
	public void addCardInToPool(Card card) {
		isCards = true;
		cardsPool.add(card);
	}
	
	public List<Card> takeCards() {
		ArrayList<Card> cards = new ArrayList<>(cardsPool);
		cardsPool.clear();
		return cards;
	}
	
	public int takeGaps() {
		int temp = gaps;
		gaps = 0;
		return temp;
	}
	public void addGap() {
		gaps++;
	}
	public void removeGap() {
		gaps--;
	}
	
	public int getCardsSize() {
		return cardsPool.size();
	}
	
}
