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
			if (game.statusChanged) {
				socketsController.sendUpdatesToAllClients();
			}
		}
	}
}
