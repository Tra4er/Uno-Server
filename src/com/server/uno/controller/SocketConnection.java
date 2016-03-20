package com.server.uno.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class SocketConnection extends Thread {

	private Socket socket;

	private Game game;

	public SocketConnection(Socket socket, Game game) {
		this.socket = socket;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			InputStreamReader inStream = new InputStreamReader(socket.getInputStream());
			BufferedReader buffReader = new BufferedReader(inStream);
			PrintStream printStream = new PrintStream(socket.getOutputStream());

			String status = buffReader.readLine();
			String playerName = buffReader.readLine();
			if (status.equals("hello")) {
				Player player = new Player(createId(), playerName);
				game.players.add(player);
				printStream.println(player.id + "\0");
				System.out.println(game.players.toString());
			} else if (status.equals("bye")) {
				socket.close();
				interrupt();
			} else if (Integer.parseInt(status) > 0) {
				game.players.forEach(p -> {
					if (p.id.equals(status)) {
						printStream.println(createJson());
					}
				});
			}
			System.out.println(status);
			System.out.println(playerName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createId() {
		String id = Integer.toHexString((int) (Math.random() * 1000000));
		return id;
	}

	private String createJson() {
		Iterator<Player> iterator = game.players.iterator();
		JsonObject json = new JsonObject();
		JsonArray jarr = new JsonArray();
		int counter = 0;
		json.addProperty("status", game.getStatus());
		if (game.getStatus().equals("inRoom")) {
			while (iterator.hasNext()) {
				jarr.add(iterator.next().getName());
				counter++;
			}
			json.add("players", jarr);
			json.addProperty("needToStart", counter);
			return json.toString();
		} else if (game.getStatus().equals("inGame")) {
			return json.toString();
		}
		return "Json Error";
	}
}
