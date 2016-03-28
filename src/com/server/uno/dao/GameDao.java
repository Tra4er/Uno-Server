package com.server.uno.dao;

import com.server.uno.model.Player;

public interface GameDao {
	public Player getPlayerById(int id);
	public void setPlayer(Player player);
}
