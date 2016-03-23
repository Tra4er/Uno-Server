package com.server.uno.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.uno.model.Game;

import com.server.uno.controller.SocketConnection;

public class Server {

	public final int PORT;

//	private Object lock = new Object();
	private ServerSocket serverSocket;
//	private ArrayList<Thread> threadPool = new ArrayList<>();
//	private ExecutorService threadPool = Executors.newCachedThreadPool();

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
//				threadPool.add(newConnection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}
}
