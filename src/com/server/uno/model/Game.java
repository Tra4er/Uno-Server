package com.server.uno.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.server.uno.controller.RulesController;
import com.server.uno.controller.Server;
import com.server.uno.controller.StepController;
import com.server.uno.util.StepTimer;

public class Game {

	public final int PLAYERS_NEEDED_TO_START = 2;
	public final int START_CARDS_NUMBER = 7;
	public final int STEP_TIME = 45;

	public static final Player ADMIN = new Player(Player.STANDART_ID, Player.STANDART_NAME);

	public volatile boolean started;

	private volatile String status = "inRoom";
	private volatile Set<Player> players = new HashSet<>();
	private volatile int playersToGo = PLAYERS_NEEDED_TO_START;
	private volatile StepTimer timer = new StepTimer(STEP_TIME);
	private GameTable table = new GameTable();

	public RulesController rulesController = new RulesController(this);
	public StepController stepController = new StepController(this, rulesController);

	public void start() throws Exception {
		changeStatus("inGame");
		for (Player player : players) {
			for (int i = 0; i < START_CARDS_NUMBER; i++) {
				player.addCard(table.getCardFromDeck());
			}
		}
		rulesController.givePlayersDeque(players);
		stepController.makeFirstStep(this, rulesController.getPlayersDeque().get(rulesController.getPlayersDeque().size() - 1), new Card("black", 14));
		rulesController.giveNextMoverBonuses(stepController.getBonuses());
		rulesController.setMover(rulesController.getPlayersDeque().get(0));
		timer.start();
		started = true;
		Server.update();
	}

	public void makeMove(Player player, Card card) {
		try {
			Card prevCard = table.getTopOpenCard();
			if (stepController.makeStep(player, card)) {
				if ((prevCard.getNumber() == 12 || prevCard.getNumber() == 14) 
						&& card.getNumber() != prevCard.getNumber())
					rulesController.giveThisMoverBonuses(stepController.getBonuses());
				else
					rulesController.giveNextMoverBonuses(stepController.getBonuses());
				
				if(prevCard.getNumber() == 10)
					rulesController.goToNextMover();
				else if (!player.equals(rulesController.getPrevMover()) || (table.getTopOpenCard().getNumber() == 10 && card.getNumber() == 10)) {
					rulesController.goToNextMover();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Server.log.error(e);
		}
	}

	public Player getMover() {
		return rulesController.getMover();
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

	public void giveCard(Player player) {
		if (player.equals(rulesController.getMover()))
			player.addCard(table.getCardFromDeck());
	}

	public List<String> getPlayersNames() {
		ArrayList<String> names = new ArrayList<>();
		for (Player p : players) {
			names.add(p.getName());
		}
		return names;
	}

	public Set<Player> getPlayers() {
		return new HashSet<Player>(players);
	}

	public void addPlayer(Player player) {
		if (player == null)
			throw new NullPointerException("Wrong Player");
		players.add(player);
		playersToGo--;
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

	public GameTable getTable() {
		return table;
	}

	public void setTopOpenCard(Card topCard) { // TODO Valve
		if (topCard == null)
			throw new NullPointerException("Wrong top card");
		table.setTopOpenCard(topCard);
	}

}
