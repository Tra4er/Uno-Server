package com.server.uno.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesManager {

	private Game game;
	private List<Player> playersDeque;
	private CardsManager cardsManager;
	private StepManager stepManager;

	public MoversManager moversManager;

	public boolean isReverse;

	public RulesManager(Game game) {
		this.game = game;
		playersDeque = new ArrayList<>(game.getPlayers());
		cardsManager = new CardsManager(game, this);
		stepManager = new StepManager(game, this, cardsManager);
		moversManager = new MoversManager(game, this); // TODO is this a right place?
		givePlayersDeque();
	}

	public synchronized void makeStep(Player player, Card card) throws Exception { // TODO
																					// Multithreading
		System.out.println();
		System.out.println("Player trying to makeStep");
		System.out.println(player);
		System.out.println();
		System.out.println("His card");
		System.out.println(card);
		System.out.println();
		System.out.println("*********");
		System.out.println();

		System.out.println("previousMover");
		System.out.println(moversManager.getPreviousMover());
		System.out.println();
		System.out.println("mover");
		System.out.println(moversManager.getMover());
		System.out.println();
		System.out.println("nextMover");
		System.out.println(moversManager.getNextMover());
		System.out.println();

		if (stepManager.makeStep(player, card))
			moversManager.goToNextMover();

		if (!isMoreStepsAvailable(moversManager.getPreviousMover()))
			moversManager.getPreviousMover().isFirstMove = true;
	}

	public boolean isRightCard(Card card) throws Exception {
		Card topCard = game.getTable().getTopOpenCard();
		if (card.getColor().equals(topCard.getColor()) || card.getNumber() == topCard.getNumber()
				|| card.getNumber() > 12) {
			return true;
		}
		return false;
	}

	public boolean isRightSecondCard(Card card) throws Exception {
		return card.getNumber() == game.getTable().getTopOpenCard().getNumber();
	}

	public Player getNextStepPlayer(Player player) throws Exception {
		if (player.getPlaceInDeque() > playersDeque.size())
			throw new Exception("Wrong player position");

		Player nextStepMover = null;

		if (player.getPlaceInDeque() == playersDeque.size() - 1)
			nextStepMover = playersDeque.get(0);
		else
			nextStepMover = playersDeque.get(player.getPlaceInDeque() + 1);

		return nextStepMover;
	}

	public void punish(Player player) throws Exception {
		player.addCard(game.getTable().getCardFromDeck());
	}

	public void givePlayersDeque() {
		for (int i = 0; i < playersDeque.size(); i++) {
			playersDeque.get(i).setPlaceInDeque(i);
		}
	}

	public boolean isMoreStepsAvailable(Player player) throws Exception {
		for (Card card : player.getCards()) {
			if (card.getNumber() == game.getTable().getTopOpenCard().getNumber()) {
				return true;
			}
		}
		return false;
	}

	public List<Player> getPlayersDeque() {
		return new ArrayList<Player>(playersDeque);
	}

	public StepManager getStepManager() {
		return stepManager;
	}
}
