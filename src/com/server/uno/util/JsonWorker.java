package com.server.uno.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class JsonWorker {

	private JSONObject request;
	private JSONObject response;
	private Game game;
	private Player player;

	public JsonWorker(Game game) {
		this.game = game;
	}

	public void parseToNewJson(String json) throws Exception {
		if (json == null)
			throw new NullPointerException("Wrong json");
		request = new JSONObject(json);
	}

	public String getRequestStatus() throws JSONException {
		return request.getString("request");
	}

	public String getPlayerName() throws JSONException {
		return request.getString("name");
	}

	public String generateNewConnectionResponse() throws JSONException {
		response = new JSONObject();
		response.accumulate("response", "connectionAcquired");
		response.accumulate("id", player.id);
		return response.toString();
	}

	public String generateGameData() throws JSONException {
		response = new JSONObject();
		
		if (game.getStatus().equals("game")) {
			response.accumulate("status", "inGame");
			response.accumulate("players", game.getPlayers());
			response.accumulate("cards", player.getCards());
			response.accumulate("currentPlayerName", player.getName());
			response.accumulate("topCard", game.getDesk().getTopOpenCard());
			response.accumulate("timeToMoveEnd", game.getTimeToMakeMove());
			return response.toString();
		}
		if (game.getStatus().equals("room")) {
			JSONObject temp = new JSONObject();
			temp.accumulate("players", game.getPlayersNames());
			temp.accumulate("playersToGo", game.getPlayersToGo());
			response.accumulate("response", temp);
			System.out.println(response);
			return response.toString();
		}
		if (game.getStatus().equals("move")) {
			response.accumulate("status", "move");
//			outputJson.accumulate("card", player.giveCard()); // TODO after implementation of method 
			return response.toString();
		}
		System.out.println("Sent null");
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
		return "JsonWorker [jsonObject=" + request + "]";
	}
}
