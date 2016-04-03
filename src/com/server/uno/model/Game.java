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
	private volatile Set<Player> players = new HashSet<>(); // Change visibility or meth with final
	private Player mover;
	private volatile int playersToGo = PLAYERS_NEEDED_TO_START;
	private volatile StepTimer timer = new StepTimer(STEP_TIME);
	private GameTable table = new GameTable(); // Change visibility
	
//	public RulesManager rulesManager;

	public void start() {
		changeStatus("inGame");
		table.shuffleDeck();
		boolean mover1 = true;
		for (Player player : players) {
			for (int i = 0; i < START_CARDS_NUMBER; i++) {
				player.addCard(table.getCardFromDeck());
			}
			if (mover1) { // Who first connected
				mover = player;
				mover1 = false;
			}
		}
		timer.start();
		started = true;
	}

	public void play() {
		// TODO with rulesManager
		if(status.equals("move")) {
//			mover.
			timer.start();
		}
	}
	
	public void makeMove(Player player, Card card) {
		mover = player;
		changeStatus("move");
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
		return mover;
	}

	public void setPlayerGoesNow(Player playerGoesNow) {
		if (playerGoesNow == null)
			throw new NullPointerException("Wrong playerGoesNow");
		this.mover = playerGoesNow;
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
}
