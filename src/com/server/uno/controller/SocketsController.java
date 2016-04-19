package com.server.uno.controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.server.uno.model.Game;

public class SocketsController extends Thread {
	
	public static final int CONNECTIONS_NUMBER = 2;

	private Socket socket;
	private ServerSocket serverSocket;
	private Game game;

	private volatile ArrayList<SocketConnection> connections = new ArrayList<>(); // TODO With some locker 
//	private AtomicReferenceArray<SocketConnection> connections = new AtomicReferenceArray<>(CONNECTIONS_NUMBER);
//	private BlockingQueue connections = new BlockingQueue<E>() {
//	};

	public SocketsController(ServerSocket serverSocket, Game game) {
		this.serverSocket = serverSocket;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			while (true) {
				socket = serverSocket.accept();
				SocketConnection newConnection = new SocketConnection(socket, game);
				connections.add(newConnection);
				newConnection.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Server.log.error(e);
		}
	}
	
	public synchronized void sendUpdatesToAllClients() {
		for(SocketConnection conn : connections) {
			conn.sendUpdate();
		}
	}
}
