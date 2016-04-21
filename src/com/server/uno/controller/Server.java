package com.server.uno.controller;

import java.net.ServerSocket;

import org.apache.log4j.Logger;

import com.server.uno.model.Game;

public class Server {

	public final int PORT;

	private ServerSocket serverSocket;
	private static SocketsController socketsController;

	private static Game game = new Game();

	public static Logger log = Logger.getLogger(Server.class);

	public Server(int port) {
		PORT = port;
		try {
			serverSocket = new ServerSocket(PORT);
			socketsController = new SocketsController(serverSocket, game);
			socketsController.start();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	public static void update() throws Exception {
		if (game.getPlayersToGo() <= 0) {
			if (game.started) {
				// TODO
			} else {
				game.start();
			}
		} else {
			game.changeStatus("inRoom");
		}
		socketsController.sendUpdatesToAllClients();
	}
}
