package com.server.uno.controller;

import java.io.IOException;
import java.net.ServerSocket;

import com.server.uno.model.Game;

public class Server {

	public final int PORT;

	private ServerSocket serverSocket;
	private SocketsController socketsController;

	private Game game = new Game();

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
			if(game.getStatus().equals("inRoom") && game.getPlayersToGo() <= 0){
				game.start();
			} else if(game.getPlayersToGo() >= 0 && game.getStatus().equals("inGame")) {
//				game.wait(); // TODO If players disconnected and the game has not enough players
			}
			if (game.changed) {
				socketsController.sendUpdatesToAllClients();
			}
		}
	}
}
