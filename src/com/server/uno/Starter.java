package com.server.uno;

import java.net.SocketException;
import java.util.TreeSet;

import com.server.uno.controller.Server;
import com.server.uno.model.Player;

public class Starter {

	public static void main(String[] args) throws SocketException {
		MainFrame f = new MainFrame();
		f.setVisible(true);
		Server server = new Server(10060);
		server.run();
	}
	
	public static void test() {
		TreeSet<Player> set = new TreeSet<>();
		Player p = new Player("1", "Hogi");
		set.add(p);
		p = new Player("2", "Hogi1");
		set.add(p);
		System.out.println(set);
		
	}

}
