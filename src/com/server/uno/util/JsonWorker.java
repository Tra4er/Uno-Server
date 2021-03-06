package com.server.uno.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.server.uno.controller.Server;
import com.server.uno.model.Card;
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

	public synchronized void parseToNewJson(String json) throws Exception {
		if (json == null)
			throw new NullPointerException("Wrong json");
		request = new JSONObject(json);
	}

	public synchronized String getRequestStatus() throws JSONException {
		return request.getString("request");
	}

	public synchronized String getPlayerName() throws JSONException {
		return request.getString("name");
	}

	public synchronized Card getMoverCard() throws JSONException {
		JSONObject temp = request.getJSONObject("card");
		return new Card(temp.getString("color"), temp.getInt("value"));
	}

	public synchronized String generateNewConnectionResponse() throws JSONException {
		response = new JSONObject();
		response.put("response", "connectionAcquired");
		response.put("id", player.id);
		return response.toString();
	}

	public synchronized String generateGameData() throws JSONException {
		response = new JSONObject();
		try {
			if (game.getStatus().equals("inGame")) {
				JSONObject data = new JSONObject();
				data.put("state", "inGame");
				
				JSONArray players = new JSONArray();
				for (Player temp : game.getPlayers()) {
					JSONObject playersJson = new JSONObject();
					playersJson.put("name", temp.getName());
					playersJson.put("cardsNumber", temp.getCards().size());
					players.put(playersJson);
				}
				data.put("players", players);
				
				JSONArray cards = new JSONArray();
				for (Card card : player.getCards()) {
					JSONObject cardsJson = new JSONObject();
					cardsJson.put("color", card.getColor());
					cardsJson.put("value", card.getNumber());
					cards.put(cardsJson);
				}
				data.put("cards", cards);
				
				data.put("currentPlayerName", player.getName());
				
				JSONObject topCard = new JSONObject();
				topCard.put("color", game.getTable().getTopOpenCard().getColor());
				topCard.put("value", game.getTable().getTopOpenCard().getNumber());
				data.put("topCard", topCard);
				
				data.put("timeToMoveEnd", game.getStepTime());
				data.put("mover", game.getMover().getName());
				response.put("response", data);
			} else if (game.getStatus().equals("inRoom")) {
				JSONObject data = new JSONObject();
				data.put("players", game.getPlayersNames());
				data.put("playersToGo", game.getPlayersToGo());
				data.put("state", "inRoom");
				response.put("response", data);
				Server.log.info(response);
			} else if (game.getStatus().equals("endGame")) {
				JSONObject data = new JSONObject();
				data.put("state", "endGame");
				
				data.put("currentPlayerName", player.getName());
				
				JSONArray players = new JSONArray();
				for (Player temp : game.getPlayers()) {
					JSONObject playersJson = new JSONObject();
					playersJson.put("name", temp.getName());
					playersJson.put("score", temp.getScore());
					players.put(playersJson);
				}
				data.put("players", players);
				
				response.put("response", data);
				Server.log.info(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Server.log.error(e);
		}
		return response.toString();
	}

	public synchronized Player getPlayer() {
		return player;
	}

	public synchronized void setPlayer(Player player) {
		if (player == null)
			throw new NullPointerException("Wrong player");
		this.player = player;
	}

	@Override
	public String toString() {
		return "JsonWorker [jsonObject=" + request + "]";
	}
}
