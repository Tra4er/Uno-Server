package com.server.uno.controller;

import java.net.ServerSocket;
import java.net.Socket;

import com.server.uno.model.Game;

public class Server {

	public final int PORT;

	private ServerSocket serverSocket;

	private Game game = new Game();
	public Server(int port) {
		PORT = port;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				SocketConnection newConnection = new SocketConnection(socket, game);
				newConnection.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
