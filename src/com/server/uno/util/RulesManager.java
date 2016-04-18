package com.server.uno.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesManager {

	private Game game;
	private Player mover;
	private List<Player> playersDeque; // TODO
	private CardsManager cardsManager;
	private Deque<Card> cardsPool = new ArrayDeque<>();

	public boolean isThereBonus;

	public RulesManager(Game game) {
		this.game = game;
		playersDeque = new ArrayList<>(game.getPlayers());
		cardsManager = new CardsManager(game, this);
		givePlayersDeque();
	}

	public synchronized void makeStep(Player player, Card card) throws Exception { // TODO
																					// Multithreading

		if (isRightCard(card)) {
			mover = player;

			cardsManager.putCard(card);
			mover.removeCard(card);
		} else { // Punishment
			player.addCard(game.getTable().getCardFromDeck());
		}

		if (isThereBonus) {
			if (!cardsPool.isEmpty()) {
				int i = 0;
				while (i < cardsPool.size()) {
					getNextStepPlayer().addCard(cardsPool.pop());
				}
			}
			// if() TODO
			isThereBonus = false;
		}
		// TODO
	}

	public void makeFirstStep(Card card) throws Exception {
		if (card.getColor().equals("black")) {
			int color = (int) (Math.random() * 4);
			System.out.println("************     " + color);
			switch (color) {
			case 0:
				card.setColor("red");
				break;
			case 1:
				card.setColor("yellow");
				break;
			case 2:
				card.setColor("green");
				break;
			case 3:
				card.setColor("blue");
				break;
			}
		}
		cardsManager.putCard(card);
	}

	private boolean isRightCard(Card card) throws Exception {
		Card topCard = game.getTable().getTopOpenCard();
		if (card.getColor().equals(topCard.getColor()) || card.getNumber() == topCard.getNumber()
				|| card.getColor().equals("black")) {
			return true;
		}
		return false;
	}

	private Player getNextStepPlayer() throws Exception {
		if (mover.getPlaceInDeque() > playersDeque.size())
			throw new Exception("Wrong player position");

		Player nextStepPlayer = null;
		System.out.println("Looking for next player");

		if (mover.getPlaceInDeque() < playersDeque.size() - 1) {
			nextStepPlayer = playersDeque.get(mover.getPlaceInDeque() + 1);
		} else if (mover.getPlaceInDeque() < playersDeque.size()) {
			nextStepPlayer = playersDeque.get(0);
		}
		return nextStepPlayer;
	}

	private void givePlayersDeque() {
		mover = playersDeque.get(0);
		for (int i = 0; i < playersDeque.size(); i++) {
			playersDeque.get(i).setPlaceInDeque(i);
		}
	}

	public List<Card> getCardsPool() {
		return new ArrayList<>(cardsPool);
	}

	public void addCardInToPool(Card card) {
		cardsPool.push(card);
	}

	public Player getMover() throws CloneNotSupportedException {
		return mover.clone();
	}

	public void setMover(Player mover) {
		if (mover == null)
			throw new NullPointerException("Wrong mover");
		this.mover = mover;
	}

}
