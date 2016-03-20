package com.server.uno.model;

import java.util.TreeSet;

public class Game {

	private String status = "inRoom";
	public volatile TreeSet<Player> players = new TreeSet<>();
	public GameTable desk = new GameTable();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (status.equals("inRoom") || status.equals("inGame")) {
			this.status = status;
		}
	}

}
