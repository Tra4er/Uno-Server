package com.server.uno.controller;

import java.util.ArrayList;

import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesController {

	private Player prevMover;
	private Player mover;
	
	private Game game;
	private ArrayList<Player> playersDeque = new ArrayList<>();
	
	public RulesController(Game game) {
		this.game = game;
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
}
