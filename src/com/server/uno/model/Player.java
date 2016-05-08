package com.server.uno.model;

import java.util.LinkedList;
import java.util.List;

public class Player implements Comparable<Player>, Cloneable {

	public final static String STANDART_NAME = "Player";
	public final static String STANDART_ID = "-1";

	public final String id;
	private volatile String name;
	private volatile List<Card> cards = new LinkedList<>();
	private volatile int position;
	private volatile int score;
	
	public volatile boolean isFirstMove = true;
	
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
	
	public void removeAllCards() {
		cards.clear();
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
		return "Player [id=" + id + ", name=" + name + ", position=" + position + "]";
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

//	TODO equals() with id
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		if(position < 0)
			throw new IllegalArgumentException("Wrong position");
		this.position = position;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if (score < 0)
			throw new IllegalArgumentException("Wrong score");
		this.score = score;
	}
	
	public void addPoints(int points) {
		score += points;
	}

}
