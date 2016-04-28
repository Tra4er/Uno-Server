package com.server.uno.controller;

import com.server.uno.model.Card;
import com.server.uno.model.Player;

public class StepController {

	private RulesController rulesController;
	
	public StepController(RulesController rulesController) {
		this.rulesController = rulesController;
	}
	
	public synchronized void makeStep(Player player, Card card) {
		
	}
	
}
