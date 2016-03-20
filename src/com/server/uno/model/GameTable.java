package com.server.uno.model;

public class GameTable {
	
	public static final int FULL_DESK = 108;
	
	private volatile int cardsInDesk = FULL_DESK;
	private volatile int cardsOnTable;
	private volatile int lastCartOnTable;
	

	public int getCardsInDesk() {
		return cardsInDesk;
	}
	public void setCardsInDesk(int cardsInDesk) {
		this.cardsInDesk = cardsInDesk;
	}
	public int getCardsOnTable() {
		return cardsOnTable;
	}
	public void setCardsOnTable(int cardsOnTable) {
		this.cardsOnTable = cardsOnTable;
	}
	public int getLastCartOnTable() {
		return lastCartOnTable;
	}
	public void setLastCartOnTable(int lastCartOnTable) {
		this.lastCartOnTable = lastCartOnTable;
	}
}
