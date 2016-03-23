package com.server.uno.model;

import java.util.LinkedList;
import java.util.List;

public class Player implements Comparable<Player> {

	private final static String STANDART_NAME = "Player";

	public final String id;
	private volatile String name;
	private volatile LinkedList<String> cards;
	private volatile int timeToMove;

	public Player(String id) {
		this(id, STANDART_NAME);
	}

	public Player(String id, String name) {
		this.id = id;
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			name = STANDART_NAME;
		} else {
			this.name = name;
		}
	}

	public LinkedList<String> getCards() {
		return cards;
	}

	public void setCards(List<String> cards) {
		this.cards = (LinkedList<String>) cards;
	}
	
	public void giveCard(String card) {
		if(cards == null) {
			cards = new LinkedList<>();
		}
		cards.add(card); // Valid? 
	}

	public int getTimeToMove() {
		return timeToMove;
	}

	public void setTimeToMove(int timeToMove) {
		this.timeToMove = timeToMove;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", timeToMove=" + timeToMove + "]";
	}

	@Override
	public int compareTo(Player p2) {
		if (id.equals(p2.id))
			return 0;
		else
			return 1;
	}

}
