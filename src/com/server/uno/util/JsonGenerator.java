package com.server.uno.util;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class JsonGenerator {

	private JsonObject json;

	private Game game;
	private Player player;

	public JsonGenerator(Game game, Player player) {
		if (game.getStatus().equals("inRoom")) {
			this.game = game;
			createInRoomJson();
		} else if (game.getStatus().equals("inGame")) {
			this.game = game;
			createInGameJson();
		}
		if (player != null) {
			this.player = player;
		}
	}

	public JsonObject getJsonObject() {
		return json;
	}

	public String getJsonObjectAsString() {
		return json.toString();
	}

	private void createInRoomJson() {
		json = new JsonObject();
		JsonArray jarr = new JsonArray();

		Iterator<Player> iterator = game.players.iterator();
		int counter = 0;

		json.addProperty("status", game.getStatus());

		while (iterator.hasNext()) {
			jarr.add(iterator.next().getName());
			counter++;
		}
		json.add("players", jarr);
		json.addProperty("needToStart", counter);
	}

	private void createInGameJson() {
		json = new JsonObject();
		JsonArray jsonCards = new JsonArray();

		json.addProperty("status", game.getStatus());
		for (String cart : player.getCards()) {
			jsonCards.add(cart);                             // returns Strins and he wanted int
		}
		json.add("cards", jsonCards);
		json.addProperty("currentPlayer", player.getName()); // TODO ID is better choice
		json.addProperty("timeToMove", player.getTimeToMove()); // Changed key
		json.addProperty("cardsInDeck", game.desk.getCardsInDeck());
		json.addProperty("cardsOnTable", game.desk.getCardsOnTable());
		json.addProperty("lastCardOnTable", game.desk.getLastCardOnTable());
	}
}
