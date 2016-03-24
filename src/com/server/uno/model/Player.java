package com.server.uno.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.server.uno.model.Player;

public class Player implements Comparable<Player> {

	private final static String STANDART_NAME = "Player";

	public final String id;
	private volatile String name;
	private volatile List<Card> cards = new LinkedList<>();
	
	public Player(String id) {
		this(id, STANDART_NAME);
	}

	public Player(String id, String name) {
		this.id = id;
		setName(name);
	}
	
	public void takeCard(Card card) {
		if(card == null) 
			throw new NullPointerException("Wrong card");
		cards.add(card);
	}
	
	public Card giveCard() {
		Card temp = cards.get(1); // !
//		((LinkedList<Card>) cards).contains(o);
		((LinkedList<Card>) cards).removeFirstOccurrence(temp);
		return temp;
//		TODO Continue impl!!!
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
		return cards;
	}

	public void setCards(List<Card> cards) {
		if(cards == null) 
			throw new NullPointerException("Wrong cards list");
		this.cards = cards;
	}
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int compareTo(Player p2) {
		if (id.equals(p2.id))
			return 0;
		else
			return 1;
	}

}
