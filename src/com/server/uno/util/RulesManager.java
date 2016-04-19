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
			if (!mover.equals(player) && game.getTable().getTopOpenCard().getNumber() == card.getNumber()) {
				mover = player;
			} else if (!mover.equals(player) && !(game.getTable().getTopOpenCard().getNumber() == card.getNumber())) {
				punish(player);
			} else {
				System.err.println(mover.isFirstMove);
				if (mover.isFirstMove) {
					System.err.println("Puting first time");
					cardsManager.putCard(card);
					mover.removeCard(card);
				} else {
					if (isRightSecondStep(card)) {
						System.err.println("Puting second time");
						cardsManager.putCard(card);
						mover.removeCard(card);
					} else {
						punish(player);
					}
				}
			}
		} else {
			punish(player);
		}

		if (isThereBonus) {
			if (!cardsManager.getCardsPool().isEmpty()) {
				int i = 0;
				while (i < cardsManager.getCardsPool().size()) {
					getNextStepPlayer().addCard(cardsManager.popFromCardsPool());
				}
			}
			isThereBonus = false;
		}
		if (!isStepsAvailable()) {
			mover = getNextStepPlayer();
		}
	}

	private boolean isRightCard(Card card) throws Exception {
		Card topCard = game.getTable().getTopOpenCard();
		if (card.getColor().equals(topCard.getColor()) || card.getNumber() == topCard.getNumber()
				|| card.getNumber() > 12) {
			return true;
		}
		return false;
	}

	private boolean isRightSecondStep(Card card) throws Exception {
		return card.getNumber() == game.getTable().getTopOpenCard().getNumber() /*|| card.getColor().equals(game.getTable().getTopOpenCard().getColor())*/;
	}

	public void makeFirstStep(Card card) throws Exception {
		if (card.getColor().equals("black")) {
			int color = (int) (Math.random() * 4);
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

	private Player getNextStepPlayer() throws Exception {
		if (mover.getPlaceInDeque() > playersDeque.size())
			throw new Exception("Wrong player position");

		Player nextStepPlayer = null;

		if (mover.getPlaceInDeque() < playersDeque.size() - 1) {
			nextStepPlayer = playersDeque.get(mover.getPlaceInDeque() + 1);
		} else if (mover.getPlaceInDeque() < playersDeque.size()) {
			nextStepPlayer = playersDeque.get(0);
		}
		return nextStepPlayer;
	}

	private boolean isStepsAvailable() throws Exception {
		for (Card card : mover.getCards()) {
			if (card.getNumber() == game.getTable().getTopOpenCard().getNumber()) {
				return true;
			}
		}
		return false;
	}

	private void punish(Player player) throws Exception {
		player.addCard(game.getTable().getCardFromDeck());
	}

	private void givePlayersDeque() {
		mover = playersDeque.get(0);
		for (int i = 0; i < playersDeque.size(); i++) {
			playersDeque.get(i).setPlaceInDeque(i);
		}
	}

	public Player getMover() throws CloneNotSupportedException {
		return mover;
	}

	public void setMover(Player mover) {
		if (mover == null)
			throw new NullPointerException("Wrong mover");
		this.mover = mover;
	}

}
