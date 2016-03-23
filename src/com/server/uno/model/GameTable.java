package com.server.uno.model;

public class GameTable {
	
	public static final int FULL_DECK = 108;
	
	private volatile int cardsInDeck = FULL_DECK;
	private volatile int cardsOnTable;
	private volatile int lastCardOnTable;
	

	public int getCardsInDeck() {
		return cardsInDeck;
	}
	public void setCardsInDeck(int cardsInDesk) {
		this.cardsInDeck = cardsInDesk;
	}
	public int getCardsOnTable() {
		return cardsOnTable;
	}
	public void setCardsOnTable(int cardsOnTable) {
		this.cardsOnTable = cardsOnTable;
	}
	public int getLastCardOnTable() {
		return lastCardOnTable;
	}
	public void setLastCardOnTable(int lastCartOnTable) {
		this.lastCardOnTable = lastCartOnTable;
	}
}
