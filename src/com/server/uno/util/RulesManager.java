package com.server.uno.util;

import java.util.ArrayList;
import java.util.List;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesManager {

	private Game game;
	private Player mover;
	private List<Player> playersDeque; // TODO
	private CardsManager cardsManager;
	private List<Card> cardsPool = new ArrayList<>();

	public boolean isThereBonus;

	public RulesManager(Game game) {
		this.game = game;
		playersDeque = new ArrayList<>(game.getPlayers());
		cardsManager = new CardsManager(game, this);
		givePlayersDeque();
	}

	public void makeStep(Player player, Card card) throws Exception {
		if (!mover.equals(player)) {
			player.addCard(game.getTable().getCardFromDeck()); // TODO
																// punishment
			return;
		}
		mover = player;
		cardsManager.putCard(mover, card);
		mover.removeCard(card);
		if (isThereBonus) {
			if (!cardsPool.isEmpty()) {
				for (Card tempCard : cardsPool) {
					getNextStepPlayer().addCard(tempCard);
				}
			}
//			if() TODO
		}
		// TODO
	}

	private Player getNextStepPlayer() throws Exception {
		if (mover.getPlaceInDeque() > playersDeque.size())
			throw new Exception("Wrong player position");

		Player nextStepPlayer = null;

		if (mover.getPlaceInDeque() < playersDeque.size()) {
			nextStepPlayer = playersDeque.get(mover.getPlaceInDeque() + 1);
		} else if (mover.getPlaceInDeque() == playersDeque.size()) {
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
		cardsPool.add(card);
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
