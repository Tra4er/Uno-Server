package com.server.uno;

import java.io.IOException;
import java.net.SocketException;

import org.json.JSONException;

import com.server.uno.controller.Server;

public class Starter {

	public static void main(String[] args) throws SocketException, JSONException {
		Server server = new Server(10060);
		try {
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
