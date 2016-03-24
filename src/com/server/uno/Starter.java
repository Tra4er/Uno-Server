package com.server.uno;

import java.io.IOException;
import java.net.SocketException;

import com.server.uno.controller.Server;
import com.server.uno.util.JsonWorker;

public class Starter {

	public static void main(String[] args) throws SocketException {
		JsonWorker j = new JsonWorker();
		Server server = new Server(10060);
		try {
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
