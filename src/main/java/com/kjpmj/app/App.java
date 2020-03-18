package com.kjpmj.app;

import com.kjpmj.app.netty.server.ServerBootstrapper;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		System.out.println("Port : " + port);
		
		ServerBootstrapper serverBootstrapper = new ServerBootstrapper(port);
		serverBootstrapper.init();
	}
}
