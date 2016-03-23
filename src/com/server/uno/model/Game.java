package com.server.uno.model;

import java.util.TreeSet;

import com.server.uno.model.GameTable;
import com.server.uno.model.Player;

public class Game {

	private String status = "inRoom";
	public volatile TreeSet<Player> players = new TreeSet<>(); // TODO MB its's a bad choice
	public GameTable desk = new GameTable();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (!status.equals("inRoom") || !status.equals("inGame") || !status.equals("move")) {
			throw new IllegalArgumentException("Wrong game status: " + status);
		}
		this.status = status;
	}

}
