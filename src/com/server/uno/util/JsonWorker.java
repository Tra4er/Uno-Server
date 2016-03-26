package com.server.uno.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class JsonWorker {

	private JSONObject inputJson;
	private JSONObject outputJson;
	private Game game;
	private Player player;

	public JsonWorker(Game game) {
		this.game = game;
	}

	public void parseToNewJson(String json) throws Exception {
		if (json == null)
			throw new NullPointerException("Wrong json");
		inputJson = new JSONObject(json);
	}

	public String getStatus() throws JSONException {
		return inputJson.getString("status");
	}

	public String getName() throws JSONException {
		return inputJson.getString("name");
	}

	public String generateNewConnectionJsone() throws JSONException {
		outputJson = new JSONObject();
		outputJson.append("status", "newConnection");
		outputJson.append("id", player.id);
		return outputJson.toString();
	}

	public String generateGameData() throws JSONException {
		outputJson = new JSONObject();
		
		if (game.getStatus().equals("inGame")) {
			outputJson.accumulate("status", "inGame");
			outputJson.accumulate("players", game.getPlayers());
			outputJson.accumulate("cards", player.getCards());
			outputJson.accumulate("currentPlayerName", player.getName());
			outputJson.accumulate("topCard", game.getDesk().getTopOpenCard());
			outputJson.accumulate("timeToMoveEnd", game.getTimeToMakeMove());
			return outputJson.toString();
		}
		if (game.getStatus().equals("inRoom")) {
			outputJson.accumulate("status", "inRoom");
			outputJson.accumulate("players", game.getPlayers());
			outputJson.accumulate("playersToGo", game.getPlayersToGo());
			return outputJson.toString();
		}
		if (game.getStatus().equals("move")) {
			outputJson.accumulate("status", "move");
//			outputJson.accumulate("card", player.giveCard()); // TODO after implementation of method 
			return outputJson.toString();
		}

		return null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		if (player == null)
			throw new NullPointerException("Wrong player");
		this.player = player;
	}

	@Override
	public String toString() {
		return "JsonWorker [jsonObject=" + inputJson + "]";
	}
}
