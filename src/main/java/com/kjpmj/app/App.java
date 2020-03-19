package com.kjpmj.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjpmj.app.netty.server.ServerBootstrapper;
import com.kjpmj.app.netty.util.ConfigManager;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(App.class);
		
		if(args.length < 1) {
			logger.error("Config File Name Not found");
			System.exit(0);
		}
		
		ConfigManager configManager = ConfigManager.getInstance();
		configManager.load(ConfigManager.CONFIG_X, args[0]);
		
		ServerBootstrapper serverBootstrapper = new ServerBootstrapper();
		serverBootstrapper.init();
	}
}
