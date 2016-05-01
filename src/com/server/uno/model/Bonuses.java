package com.server.uno.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class Bonuses {

	private Deque<Card> cardsPool = new ArrayDeque<>();
	private int gaps;
	
	public void addCardInToPool(Card card) {
		cardsPool.add(card);
	}
	
	public int getGaps() {
		return gaps;
	}
	public void addGap() {
		gaps++;
	}
	
}
