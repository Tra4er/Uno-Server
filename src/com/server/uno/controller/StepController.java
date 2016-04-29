package com.server.uno.controller;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class StepController {

	private RulesController rulesController;
	
	public StepController(RulesController rulesController) {
		this.rulesController = rulesController;
	}
	
	public synchronized void makeStep(Player player, Card card) {
		if(player.equals(rulesController.getMover())) {
			
		}
	}
	
	public void makeFirstStep(Game game, Player player, Card card) { // TODO
		game.setTopOpenCard(card);
		game.rulesController.setMover(game.rulesController.getPlayersDeque().get(0));
	}
	
}
