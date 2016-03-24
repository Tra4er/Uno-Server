package com.server.uno.model;

import java.util.Set;
import java.util.TreeSet;

import com.server.uno.model.GameTable;
import com.server.uno.model.Player;

public class Game {

	private volatile String status = "inRoom";
	private volatile TreeSet<Player> players = new TreeSet<>(); // TODO MB its's a bad choice
	private Player playerGoesNow;
	private volatile int playersToGo;
	private volatile int timeToMakeMove; // bad name?
	private GameTable desk = new GameTable();
	
	public void start() {
		desk.shuffleDeck();
		// TODO imlp
	}

	public String getStatus() {
		return status;
	}

	public void changeStatus(String status) {
		if (!status.equals("inRoom") || !status.equals("inGame") || !status.equals("move")) { // TODO make other statuses 
			throw new IllegalArgumentException("Wrong game status: " + status);
		}
		this.status = status;
	}
	
	public Set<Player> getPlayers() {
		return players;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}

	public Player getPlayerGoesNow() {
		return playerGoesNow;
	}

	public void setPlayerGoesNow(Player playerGoesNow) {
		if(playerGoesNow == null)
			throw new NullPointerException("Wrong playerGoesNow");
		this.playerGoesNow = playerGoesNow;
	}

	public int getPlayersToGo() {
		return playersToGo;
	}

	public void setPlayersToGo(int playersToGo) {
		if(playersToGo < 0)
			throw new IllegalArgumentException("Wrong playersToGo number");
		this.playersToGo = playersToGo;
	}

}
