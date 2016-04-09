package com.server.uno.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.json.JSONException;

import com.server.uno.model.Game;
import com.server.uno.model.Player;
import com.server.uno.util.JsonWorker;

public class SocketConnection extends Thread {

	public static final String STRING_TERMINATOR = "";

	private Socket socket;
	private InputStreamReader inStream;
	private BufferedReader buffReader;
	private PrintStream printStream;

	private Game game;
	private Player player;

	private JsonWorker jsonWorker;

	public SocketConnection(Socket socket, Game game) throws IOException {
		this.socket = socket;
		this.game = game;

		jsonWorker = new JsonWorker(game);

		inStream = new InputStreamReader(socket.getInputStream());
		buffReader = new BufferedReader(inStream);
		printStream = new PrintStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		try {
			jsonWorker.parseToNewJson(buffReader.readLine());

			if (jsonWorker.getRequestStatus().equals("newConnection")) {
				player = new Player(createId(), jsonWorker.getPlayerName());
				Server.log.warn("A connection with: " + player + " was established.");
				jsonWorker.setPlayer(player);
				game.addPlayer(player);
				printStream.println(jsonWorker.generateNewConnectionResponse());
				printStream.flush(); // TODO mb i don't need this
				startDialog();
			} else if (jsonWorker.getRequestStatus().equals("reconnect")) {
				String reconnectedPlayerId = jsonWorker.getPlayer().id;
				for (Player temp : game.getPlayers()) {
					if (temp.id.equals(reconnectedPlayerId)) {
						player = new Player(temp.id, temp.getName());
						break;
					}
				}
				Server.log.warn("A connection with: " + player + " was established.");
				printStream.print("hello my old friend"); // TODO
				startDialog();
			} else {
				printStream.print("helloError");
				Server.log.error("Wrong Hello request with: " + player);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Server.log.error(e);
			printStream.print("helloError");
		} finally {
			try {
				printStream.close();
				buffReader.close();
				inStream.close();
				socket.close();
				Server.log.warn("A connection with: " + player + " was closed.");
			} catch (Exception e) {
				e.printStackTrace();
				Server.log.error(e);
			}
		}
	}

	private void startDialog() throws IOException, Exception {
		while (!socket.isClosed()) {
			try {
				Server.update();
				jsonWorker.parseToNewJson(buffReader.readLine());
				System.out.println("Request: " + jsonWorker);
				if (jsonWorker.getRequestStatus().equals("move"))
					game.makeMove(player, jsonWorker.getMoverCard());
				// TODO
			} catch (Exception e) {
				e.printStackTrace();
				Server.log.error(e);
				if(e.getMessage().equals("Connection reset"))
					socket.close();
			}
		}
	}

	public void sendUpdate() {
		try {
			printStream.println(jsonWorker.generateGameData());
			System.out.println("Sent Updates: " + jsonWorker.generateGameData());
		} catch (JSONException e) {
			e.printStackTrace();
			Server.log.error(e);
		}
	}

	private String createId() {
		String id = Integer.toHexString((int) (Math.random() * 1000000));
		return id;
	}
}
