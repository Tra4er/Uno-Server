package com.server.uno.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.server.uno.controller.Server;
import com.server.uno.util.RulesManager;
import com.server.uno.util.StepTimer;

public class Game {
	
	public final int PLAYERS_NEEDED_TO_START = 2;
	public final int START_CARDS_NUMBER = 7;
	public final int STEP_TIME = 45;

	public volatile boolean started;

	private volatile String status = "inRoom";
	private volatile Set<Player> players = new HashSet<>();
	private Player mover;
	private volatile int playersToGo = PLAYERS_NEEDED_TO_START;
	private volatile StepTimer timer = new StepTimer(STEP_TIME);
	private GameTable table = new GameTable(); // Change visibility
	
	private RulesManager rulesManager; // TODO

	public void start() {
		changeStatus("inGame");
		table.shuffleDeck();
		boolean isMover = true;
		for (Player player : players) {
			for (int i = 0; i < START_CARDS_NUMBER; i++) {
				player.addCard(table.getCardFromDeck());
			}
			if (isMover) { // Who first connected
				mover = player;
				isMover = false;
			}
		}
		table.openCard();
		timer.start();
		started = true;
		Server.update();
	}

	public void play() {
		// TODO with rulesManager
		if(status.equals("move")) {
//			mover.
			timer.start();
		}
	}
	
	public void makeMove(Player player, Card card) { // TODO
		mover = player;
		mover.removeCard(card);
//		changeStatus("move");
	}

	public String getStatus() {
		return new String(status);
	}

	public void changeStatus(String status) {
		if (!status.equals("inRoom") && !status.equals("inGame"))
			throw new IllegalArgumentException("Wrong game status: " + status);
		if (status.equals("inGame"))
			started = true;
		this.status = status;
		Server.log.info("Game status changed: " + status);
	}

	public List<String> getPlayersNames() {
		ArrayList<String> names = new ArrayList<>();
		for (Player p : players) {
			names.add(p.getName());
		}
		return names;
	}

	public Set<Player> getPlayers() {
		return new HashSet<Player>(players); // TODO Not sure if this is right
	}

	public void addPlayer(Player player) {
		if(player == null) 
			throw new NullPointerException("Wrong Player");
		players.add(player);
		playersToGo--;
	}

	public Player getMover() throws CloneNotSupportedException {
		return mover.clone();
	}

	public void setMover(Player mover) {
		if (mover == null)
			throw new NullPointerException("Wrong Mover");
		this.mover = mover;
	}

	public int getPlayersToGo() {
		return playersToGo;
	}

	public void setPlayersToGo(int playersToGo) {
		if (playersToGo < 0)
			throw new IllegalArgumentException("Wrong playersToGo number");
		this.playersToGo = playersToGo;
	}

	public int getStepTime() {
		return timer.getTime();
	}

	public GameTable getTable() throws CloneNotSupportedException {
		return table.clone();
	}
}
