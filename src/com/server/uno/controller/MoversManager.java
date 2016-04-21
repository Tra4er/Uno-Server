package com.server.uno.controller;

import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class MoversManager {
	
	private Player previousMover;
	private Player mover;
	private Player nextMover;
	
	private Game game;
	private RulesManager rulesManager;
	
	public MoversManager(Game game, RulesManager rulesManager) {
		this.game = game;
		this.rulesManager = rulesManager;
	}
	
	public void goToNextMover() throws Exception {
		previousMover = mover;
		mover = nextMover;
		setNextMover(rulesManager.getNextStepPlayer(mover));
	}
	

	public void giveNextPlayerCard() throws Exception {
		rulesManager.getNextStepPlayer(mover).addCard(game.getTable().getCardFromDeck());
	}
	
	public void missNextMover() throws Exception {
		nextMover = rulesManager.getNextStepPlayer(nextMover);
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
	public void setMover(Player mover) throws Exception {
		if (mover == null)
			throw new NullPointerException("Wrong mover");
		this.mover = mover;
		nextMover = rulesManager.getNextStepPlayer(mover);
	}
	
	public Player getNextMover() {
		return nextMover;
	}
	public void setNextMover(Player nextMover) {
		if (nextMover == null)
			throw new NullPointerException("Wrong mover");
		this.nextMover = nextMover;
	}
}
