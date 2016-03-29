package com.server.uno.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
	
	public final int PLAYERS_NEEDED_TO_START = 2;
	public final int START_CARDS_NUMBER = 7;

	private volatile String status = "room";
	public volatile boolean statusChanged;
	
	private volatile Set<Player> players = new HashSet<>();
	private Player playerGoesNow;
	private volatile int playersToGo = PLAYERS_NEEDED_TO_START;
	private volatile int timeToMakeMove; // bad name?
	private GameTable table = new GameTable(); // Change visibility 

	public void start() {
		changeStatus("inGame");
		table.shuffleDeck();
		boolean mover = true;
		for(Player player : players){
			for(int i = 0; i < START_CARDS_NUMBER; i++) {
				player.addCard(table.getCardFromDeck());
			}
			if(mover) { // TODO Who first connected
				playerGoesNow = player;
				mover = false;
			}
		}
		
	}

	public String getStatus() {
		return status;
	}

	public void changeStatus(String status) {
		if (!status.equals("inRoom") || !status.equals("inGame") || !status.equals("move"))
			throw new IllegalArgumentException("Wrong game status: " + status);
		this.status = status;
		statusChanged = true;
	}
	
	public List<String> getPlayersNames() {
		ArrayList<String> names = new ArrayList<>();
		for(Player p : players) {
			names.add(p.getName());
		}
		return names;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		players.add(player);
		playersToGo--;
	}

	public Player getPlayerGoesNow() {
		return playerGoesNow;
	}

	public void setPlayerGoesNow(Player playerGoesNow) {
		if (playerGoesNow == null)
			throw new NullPointerException("Wrong playerGoesNow");
		this.playerGoesNow = playerGoesNow;
	}

	public int getPlayersToGo() {
		return playersToGo;
	}

	public void setPlayersToGo(int playersToGo) {
		if (playersToGo < 0)
			throw new IllegalArgumentException("Wrong playersToGo number");
		this.playersToGo = playersToGo;
	}

	public int getTimeToMakeMove() {
		return timeToMakeMove;
	}

	public void setTimeToMakeMove(int timeToMakeMove) {
		if (timeToMakeMove < 0)
			throw new IllegalArgumentException("Wrong timeToMakeMove");
		this.timeToMakeMove = timeToMakeMove;
	}

	public GameTable getTable() {
		return table;
	}

	public void setTable(GameTable desk) { // Maybe i don't need this meth
		if (desk == null)
			throw new NullPointerException("Wrong desk");
		this.table = desk;
	}

}
