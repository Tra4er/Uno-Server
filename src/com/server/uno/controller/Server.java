package com.server.uno.controller;

import java.io.IOException;
import java.net.ServerSocket;

import com.server.uno.model.Game;

public class Server {

	public final int PORT;

	private ServerSocket serverSocket;
	private static SocketsController socketsController;

	private static Game game = new Game();

	public Server(int port) {
		PORT = port;
		try {
			serverSocket = new ServerSocket(PORT);
			socketsController = new SocketsController(serverSocket, game);
			socketsController.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void update() {
		if (game.getPlayersToGo() <= 0 && game.started) {
			game.play();
			if (game.changed) {
				socketsController.sendUpdatesToAllClients();
			}
		}
		// Preparation state
		if (game.getPlayersToGo() > 0) {
			game.changeStatus("inRoom");
			socketsController.sendUpdatesToAllClients();
			while (!game.changed)
				;
		} else if (game.getPlayersToGo() <= 0 && !game.started) {
			game.start();
		}
	}
}
