package com.server.uno.model;

public class GameTable {
	
	public static final int FULL_DECK = 108;
	
	private volatile int cardsInDeck = FULL_DECK;
	private volatile int lastCardOnTable; // TODO change type
	
	public int getCardsInDeck() {
		return cardsInDeck;
	}
	public void setCardsInDeck(int cardsInDeck) {
		if(cardsInDeck < 0 || cardsInDeck > 108) 
			throw new IllegalArgumentException("Wrong number of cards: " + cardsInDeck);
		this.cardsInDeck = cardsInDeck;
	}
	public int getLastCardOnTable() {
		return lastCardOnTable;
	}
	public void setLastCardOnTable(int lastCartOnTable) { // TODO 
		this.lastCardOnTable = lastCartOnTable;
	}
}
