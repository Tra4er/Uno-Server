package com.server.uno.model;

import java.util.LinkedList;
import java.util.List;

public class Player implements Comparable<Player>, Cloneable {

	public final static String STANDART_NAME = "Player";
	public final static String STANDART_ID = "-1";

	public final String id;
	private volatile String name;
	private volatile List<Card> cards = new LinkedList<>();
	private volatile int placeInDeque;
	
	public volatile boolean isFirstMove = true;
	public volatile boolean tamer; // TODO 
	
	public Player() {
		id = STANDART_ID;
	}
	
	public Player(String id) {
		this(id, STANDART_NAME);
	}

	public Player(String id, String name) {
		this.id = id;
		setName(name);
	}
	
	public void addCard(Card card) {
		if(card == null)  
			return;
		cards.add(card);
	}
	
	public void removeCard(Card card) {
		String temp = card.getColor();
		if(card.getNumber() > 12) { // TODO remove Valve
			card.setColor("black");
		}
		if(card == null || !cards.contains(card))
			throw new IllegalArgumentException("Wrong card");
		cards.remove(card);
		card.setColor(temp);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			throw new NullPointerException("Wrong name");
		this.name = name;
	}

	public List<Card> getCards() {
		return new LinkedList<Card>(cards);
	}
	
	public void setCards(List<Card> cards) {
		if(cards == null) 
			throw new NullPointerException("Wrong cards list");
		this.cards = cards;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", placeInDeque=" + placeInDeque + ", isFirstMove=" + isFirstMove + "]";
	}

	@Override
	public int compareTo(Player p2) {
		if (id.equals(p2.id))
			return 0;
		else
			return 1;
	}
	
	@Override
	public Player clone() throws CloneNotSupportedException {
		return (Player) super.clone();
	}

	/**
	 * 
	 * @return position starting from 0
	 */
	public int getPlaceInDeque() {
		return placeInDeque;
	}

	public void setPlaceInDeque(int placeInDeque) {
		if(placeInDeque < 0) 
			throw new IllegalArgumentException("Wrong placeInDeque: " + placeInDeque);
		this.placeInDeque = placeInDeque;
	}

}
