package com.server.uno.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesManager {

	private Game game;
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
		stepManager.makeStep(player, card);

		giveBonusesToAll(player);

		checkForMoreSteps(player);
	}
	

	public void giveBonusesToAll(Player player) throws Exception {
		if (isThereBonusCards) {
			int i = 0;
			while (i < cardsManager.getCardsPool().size()) {
				getNextStepPlayer(false).addCard(cardsManager.popFromCardsPool());
			}
			isThereBonusCards = false;
		}
		if (isGap) {
			mover = cardsManager.getNextMover();
			isGap = false;
		}
		if (isReverse) {
			Collections.reverse(playersDeque);
			isReverse = false;
		}
	}
	
	public void checkForMoreSteps(Player player) throws Exception {
		if (mover.equals(player) && !mover.isFirstMove && !isStepsAvailable()) {
			mover.isFirstMove = true;
			mover = getNextStepPlayer(false);
		}
	}

	public boolean isRightCard(Card card) throws Exception {
		Card topCard = game.getTable().getTopOpenCard();
		if (card.getColor().equals(topCard.getColor()) || card.getNumber() == topCard.getNumber()
				|| card.getNumber() > 12) {
			return true;
		}
		return false;
	}

	public Player getNextStepPlayer(boolean isGap) throws Exception {
		if (mover.getPlaceInDeque() > playersDeque.size())
			throw new Exception("Wrong player position");

		int gap = 1;
		if (isGap)
			gap = 2;
		
		Player nextStepMover = null;
		
		if (mover.getPlaceInDeque() == playersDeque.size() - gap)
			nextStepMover = playersDeque.get(0);
		else 
			nextStepMover = playersDeque.get(mover.getPlaceInDeque() + gap);
		
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
	
	public boolean isStepsAvailable() throws Exception {
		for (Card card : mover.getCards()) {
			if (card.getNumber() == game.getTable().getTopOpenCard().getNumber()) {
				return true;
			}
		}
		return false;
	}

	public Player getMover() throws CloneNotSupportedException {
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
