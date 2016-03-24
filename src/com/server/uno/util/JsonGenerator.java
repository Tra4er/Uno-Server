package com.server.uno.util;

import java.util.Iterator;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class JsonGenerator {

	private JsonObject json;
	private Gson g = new Gson();

	private Game game;
	private Player player;

	public JsonGenerator(Game game, Player player) {
		if (game.getStatus().equals("inRoom")) {
			this.game = game;
			createInRoomJson();
		} else if (game.getStatus().equals("inGame")) {
			this.game = game;
			createInGameJson();
		} else if (game.getStatus().equals("move")){
			this.game = game;
			createMoveJson();
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
			jsonCards.add(cart);
		}
		json.add("cards", jsonCards);
		json.addProperty("currentPlayer", player.getName()); 
		json.addProperty("timeToMove", player.getTimeToMove()); 
		json.addProperty("cardsInDeck", game.desk.getNumberOfCardsInDeck());
		json.addProperty("lastCardOnTable", game.desk.getLastCardOnTable());
	}
	
	private void createMoveJson() {
		// TODO Add imlp
	}
}
