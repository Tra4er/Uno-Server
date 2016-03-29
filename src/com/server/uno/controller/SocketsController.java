package com.server.uno.controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.server.uno.model.Game;

public class SocketsController extends Thread {

	private Socket socket;
	private ServerSocket serverSocket;
	private Game game;

	private volatile ArrayList<SocketConnection> connections = new ArrayList<>();

	public SocketsController(ServerSocket serverSocket, Game game) {
		this.serverSocket = serverSocket;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			while (true) {
				socket = serverSocket.accept();
				System.out.println("Client accepted");
				SocketConnection newConnection = new SocketConnection(socket, game);
				connections.add(newConnection);
				newConnection.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendUpdatesToAllClients() {
		for(SocketConnection conn : connections) {
			conn.sendUpdates();
		}
		game.statusChanged = false;
	}
}
