package com.server.uno.controller;

import java.io.IOException;
import java.net.ServerSocket;

import com.server.uno.model.Game;
import com.server.uno.util.CardsManager;

public class Server {

	public final int PORT;

	private ServerSocket serverSocket;
	private SocketsController socketsController;

	private Game game = new Game();
	private CardsManager cardsManager = new CardsManager(game);

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

	public void run() throws Exception {
		while (true) {
			if (game.getPlayersToGo() <= 0 && game.started) {
				game.play();
				if (game.changed) {
					socketsController.sendUpdatesToAllClients();
				}
				continue;
			}
			// Preparation state
			if (game.getPlayersToGo() > 0) { 
				game.changeStatus("inRoom");
				socketsController.sendUpdatesToAllClients();
				while(!game.changed);
			} else if (game.getPlayersToGo() <= 0 && !game.started) {
				game.start();
			} 
		}
	}
}
