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

			String message = buffReader.readLine();
			System.out.println(message);

			if (message.equals("hello")) {
				message = waitForMessage(buffReader);
				Player player = new Player(createId(), message);
				game.players.add(player);
				// printStream.println("hello");
				printStream.print(player.id + STRING_TERMINATOR);
				System.out.println(game.players.toString());

				while (!socket.isClosed()) {
					message = waitForMessage(buffReader);
					if (message.equals("id")) {
						System.out.println("id");
						message = waitForMessage(buffReader);
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

					message = waitForMessage(buffReader);
					if (message.equals("bye")) {
						System.out.println("Client Bye");
						printStream.print("bye");
						socket.close();
						interrupt();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createId() {
		String id = Integer.toHexString((int) (Math.random() * 1000000));
		return id;
	}

	private String waitForMessage(BufferedReader buffReader) throws IOException {
		try {
			while(!buffReader.ready()) {}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffReader.readLine();
	}

}
