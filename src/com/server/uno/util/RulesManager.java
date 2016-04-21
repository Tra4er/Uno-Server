package com.server.uno.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesManager {

	private Game game;
	private Player previousMover;
	private Player mover;
	private List<Player> playersDeque;
	private CardsManager cardsManager;
	private StepManager stepManager;

	public boolean isThereBonusCards;
	public boolean isGap;
	public boolean isReverse;

	public RulesManager(Game game) {
		this.game = game;
		playersDeque = new ArrayList<>(game.getPlayers());
		cardsManager = new CardsManager(game, this);
		stepManager = new StepManager(game, this, cardsManager);
		givePlayersDeque();
	}

	public synchronized void makeStep(Player player, Card card) throws Exception { // TODO
																					// Multithreading
		System.err.println("Mover " + mover);
		if(stepManager.makeStep(player, card))
			changeMover();

//		giveBonusesToAll(player);
		
		if (!isMoreStepsAvailable(previousMover)) 
			previousMover.isFirstMove = true;
	}

	public void giveBonusesToAll(Player player) throws Exception {
		if (isThereBonusCards) {
			int i = 0;
			while (i < cardsManager.getCardsPool().size()) {
				getNextStepPlayer(mover).addCard(cardsManager.popFromCardsPool());
			}
			isThereBonusCards = false;
		}
		if (isGap) {
			mover = cardsManager.getNextMover();
			isGap = false;
			return;
		}
		if (isReverse) {
			Collections.reverse(playersDeque); // TODO
			isReverse = false;
		}
		if (player.equals(mover)) // TODO || previousMover
			changeMover();
	}

//	public void checkForMoreSteps(Player player) throws Exception {
//		if (mover.equals(player) && !mover.isFirstMove && !isMoreStepsAvailable()) {
//			mover.isFirstMove = true;
//		}
//	}

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

	public void changeMover() throws Exception {
		previousMover = mover;
		mover = getNextStepPlayer(mover);
	}

	public Player getNextStepPlayer(Player player) throws Exception {
		if (player == null)
			player = mover;
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
	
	public void giveNextPlayerCard() throws Exception {
		getNextStepPlayer(mover).addCard(game.getTable().getCardFromDeck());
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

	public Player getPreviousMover() {
		return previousMover;
	}

	public void setPreviousMover(Player previousMover) {
		if (previousMover == null)
			throw new NullPointerException("Wrong mover");
		this.previousMover = previousMover;
	}

	public Player getMover() {
		return mover;
	}

	public void setMover(Player mover) {
		if (mover == null)
			throw new NullPointerException("Wrong mover");
		this.mover = mover;
	}

	public List<Player> getPlayersDeque() {
		return new ArrayList<Player>(playersDeque);
	}

	public StepManager getStepManager() {
		return stepManager;
	}

}
