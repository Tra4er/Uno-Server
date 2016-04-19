package com.server.uno.model;

import java.util.LinkedList;
import java.util.List;

public class Player implements Comparable<Player>, Cloneable {

	private final static String STANDART_NAME = "Player";

	public final String id;
	private volatile String name;
	private volatile List<Card> cards = new LinkedList<>();
	private volatile int placeInDeque;
	
	public volatile boolean isFirstMove = true;;
	public volatile boolean tamer; // TODO 
	
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
		if(card == null || !cards.contains(card))
			throw new IllegalArgumentException("Wrong card");
		cards.remove(card);
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
	
//	@Override
//	public String toString() {
//		return "Player [id=" + id + ", name=" + name + "]";
//	}

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

	public int getPlaceInDeque() {
		return placeInDeque;
	}

	public void setPlaceInDeque(int placeInDeque) {
		if(placeInDeque < 0) 
			throw new IllegalArgumentException("Wrong placeInDeque: " + placeInDeque);
		this.placeInDeque = placeInDeque;
	}

}
