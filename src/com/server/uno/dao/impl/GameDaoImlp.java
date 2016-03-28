package com.server.uno.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.server.uno.dao.GameDao;
import com.server.uno.model.Player;

public class GameDaoImlp implements GameDao {
	
	private final String URL = "jdbc:mysql://localhost:3306/uno_game";
	private final String USER = "root";
	private final String PASSWORD = "";
	
	private Connection conn;
	private Statement statement;
	private ResultSet result;
	
	public GameDaoImlp() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Player getPlayerById(int id) {
//		try {
//			result = statement.executeQuery("SELECT name, id_card");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return null;
	}

	@Override
	public void setPlayer(Player player) {
		
	}

}
