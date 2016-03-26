package com.server.uno.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

import org.json.JSONObject;

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
			System.out.println("First message: " + jsonWorker);

			if (jsonWorker.getStatus().equals("newConnection")) {
				player = new Player(createId(), jsonWorker.getName());
				jsonWorker.setPlayer(player);
				game.getPlayers().add(player);
				System.out.println("Created and added player: " + player);
				System.out.println("Players Data Base: " + game.getPlayers());
				printStream.print(jsonWorker.generateNewConnectionJsone());
				System.out.println("Sent json to player");
				startDialog();
			} else if (jsonWorker.getStatus().equals("reconnect")) { // TODO
																		// impl
//				 player = game.players.getPlayerWithId("id");
				// printStream.print("hello my old friend");
				// startDialog(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			printStream.print("helloError");
		}
	}

	private String createId() {
		String id = Integer.toHexString((int) (Math.random() * 1000000));
		return id;
	}

	private String waitForMessage() throws IOException {
		try {
			while (!buffReader.ready());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffReader.readLine();
	}

	private void startDialog() {
		System.out.println("Started dialog");
		while (!socket.isClosed()) {
			try {
				printStream.print(jsonWorker.generateGameData());
				System.out.println("Sent Game Data");
				
				jsonWorker.parseToNewJson(waitForMessage());

			} catch (Exception e) {
				e.printStackTrace();
				printStream.print("dialogError"); 
			}
		}
	}

}
