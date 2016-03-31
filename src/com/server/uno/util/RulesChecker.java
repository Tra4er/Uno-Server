package com.server.uno.util;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesChecker {

	public static void putAndCheckMoverCard(Player player, Game game, JsonWorker jsonWorker) throws Exception {
		Card card = new Card(jsonWorker.getMoverCard());
		Card topCard = new Card(game.getTable().getTopOpenCard());
		if (card.getColor().equals(topCard.getColor()) || card.getNumber() == topCard.getNumber()) {
			player.givesCard(card);
			game.getTable().setTopOpenCard(card);
		}
//		game.changed = true;
	}
}
