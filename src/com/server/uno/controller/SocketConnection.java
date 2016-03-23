package com.server.uno.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import com.server.uno.model.Game;
import com.server.uno.model.Player;
import com.server.uno.util.JsonGenerator;

public class SocketConnection extends Thread {

	public static final String STRING_TERMINATOR = "";

	private Socket socket;
	private InputStreamReader inStream;
	private BufferedReader buffReader;
	private PrintStream printStream;

	private Game game;
	private Player player;

	public SocketConnection(Socket socket, Game game) {
		this.socket = socket;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			inStream = new InputStreamReader(socket.getInputStream());
			buffReader = new BufferedReader(inStream);
			printStream = new PrintStream(socket.getOutputStream());

			String message = buffReader.readLine();
			System.out.println("First message: " + message);

			if (message.equals("hello")) { // First contact
				message = waitForMessage();
				player = new Player(createId(), message);
				game.players.add(player);
				System.out.println("Created and added player: " + game.players.toString());
				printStream.print(player.id + STRING_TERMINATOR);
				System.out.println("Sent id to player");
				startDialog(message);
			} else if(message.equals("reconnect")){ // TODO impl
//				player = game.players.getPlayerWithId("id");
//				printStream.print("hello my old friend");
				startDialog(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createId() {
		String id = Integer.toHexString((int) (Math.random() * 1000000));
		return id;
	}

	private String waitForMessage() throws IOException {
		try {
			while (!buffReader.ready()) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffReader.readLine();
	}
	
	private void startDialog(String message){
		System.out.println("Started dialog");
		while (!socket.isClosed()) {
			try {
				message = waitForMessage();
				if (message.equals("id")) {
					System.out.println("id");
					message = waitForMessage();
					if (Integer.parseInt(message, 16) > 0) {
						for (Player player1 : game.players) {
							if (player1.id.equals(message)) {
								String jsonAnswer = new JsonGenerator(game, player1).getJsonObjectAsString()
										+ STRING_TERMINATOR;
								printStream.print(jsonAnswer);
								System.out.println(jsonAnswer);
							}
						}
					}
				}

				message = waitForMessage();
				if (message.equals("bye")) {
					System.out.println("Client Bye");
					printStream.print("bye");
					socket.close();
					interrupt();
				}
			} catch (Exception e) {
				e.printStackTrace();
				printStream.print("error"); // TODO
			}
		}
	}

}
