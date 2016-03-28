package com.server.uno;

import com.server.uno.controller.Server;

public class Starter {

	public static void main(String[] args) {
		Server server = new Server(10060);
		try {
			server.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
