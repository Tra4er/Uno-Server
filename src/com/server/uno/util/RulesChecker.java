package com.server.uno.util;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesChecker {

	public static boolean checkStep(Game game, Card card) {
		Card topCard = new Card(game.getTable().getTopOpenCard());
		if (card.getColor().equals(topCard.getColor()) || card.getNumber() == topCard.getNumber()
				|| card.getNumber() == 13 || card.getNumber() == 14) {
			return true;
		}
		return false;
	}
}
