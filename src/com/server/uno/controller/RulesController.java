package com.server.uno.controller;

import java.util.ArrayList;
import java.util.Set;

import com.server.uno.model.Bonuses;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesController {

	private Player prevMover;
	private Player mover;
	private Player nobody = new Player();
	
	private Game game;
	private ArrayList<Player> playersDeque;
	
	public RulesController(Game game) {
		this.game = game;
	}
	
	public void giveBonuses(Bonuses bonuses) {
		
	}
	
	public void goToNextMover() {
		if(prevMover.getPosition() == playersDeque.size() - 1)
			mover = playersDeque.get(0);
		else
			mover = playersDeque.get(prevMover.getPosition() + 1);
	}
	
	public void endMoverMove() {
		prevMover = mover;
		mover = Game.ADMIN;
	}
	
	public void givePlayersDeque(Set<Player> players) {
		playersDeque = new ArrayList<>(players);
		for(int i = 0; i < playersDeque.size(); i++) {
			playersDeque.get(i).setPosition(i);
		}
	}
	
	public Player getPrevMover() {
		return prevMover;
	}
	public void setPrevMover(Player prevMover) {
		if(prevMover == null) 
			throw new NullPointerException();
		this.prevMover = prevMover;
	}
	public Player getMover() {
		return mover;
	}
	public void setMover(Player mover) {
		if (mover == null)
			throw new NullPointerException();
		this.mover = mover;
	}

	public ArrayList<Player> getPlayersDeque() {
		return new ArrayList<>(playersDeque);
	}
}
