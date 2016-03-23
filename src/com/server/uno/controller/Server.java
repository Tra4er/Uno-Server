package com.server.uno.controller;

import java.io.IOException;
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

	public void run() throws IOException {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				socket = serverSocket.accept();
				SocketConnection newConnection = new SocketConnection(socket, game);
				newConnection.run(); // TODO notify for all my connections
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}
}
