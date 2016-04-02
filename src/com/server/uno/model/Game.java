package com.server.uno.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.server.uno.util.StepTimer;

public class Game {

	public final int PLAYERS_NEEDED_TO_START = 2;
	public final int START_CARDS_NUMBER = 7;
	public final int STEP_TIME = 45;

	public volatile boolean started;
	public volatile boolean changed;

	private volatile String status = "inRoom";
	private volatile Set<Player> players = new HashSet<>(); // Change visibility
	private Player playerGoesNow;
	private volatile int playersToGo = PLAYERS_NEEDED_TO_START;
	private volatile StepTimer timer = new StepTimer(STEP_TIME);
	private GameTable table = new GameTable(); // Change visibility

	public void start() {
		changeStatus("inGame");
		table.shuffleDeck();
		boolean mover = true;
		for (Player player : players) {
			for (int i = 0; i < START_CARDS_NUMBER; i++) {
				player.addCard(table.getCardFromDeck());
			}
			if (mover) { // TODO Who first connected
				playerGoesNow = player;
				mover = false;
			}
		}
		timer.start();
		started = true;
		play();
	}

	public void play() {
		// TODO
	}

	public String getStatus() {
		return status;
	}

	public void changeStatus(String status) {
		if (!status.equals("inRoom") && !status.equals("inGame") && !status.equals("move"))
			throw new IllegalArgumentException("Wrong game status: " + status);
		if (status.equals("inGame"))
			started = true;
		changed = true;
		this.status = status;
	}

	public List<String> getPlayersNames() {
		ArrayList<String> names = new ArrayList<>();
		for (Player p : players) {
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
		changed = true;
	}

	public Player getPlayerGoesNow() {
		return playerGoesNow;
	}

	public void setPlayerGoesNow(Player playerGoesNow) {
		if (playerGoesNow == null)
			throw new NullPointerException("Wrong playerGoesNow");
		this.playerGoesNow = playerGoesNow;
		changed = true;
	}

	public int getPlayersToGo() {
		return playersToGo;
	}

	public void setPlayersToGo(int playersToGo) {
		if (playersToGo < 0)
			throw new IllegalArgumentException("Wrong playersToGo number");
		this.playersToGo = playersToGo;
		changed = true;
	}

	public int getStepTime() {
		return timer.getTime();
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
