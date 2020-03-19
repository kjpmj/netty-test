package com.kjpmj.app.netty.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjpmj.app.business.DefineCode;
import com.kjpmj.app.netty.util.ConfigManager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ServerBootstrapper {
	Logger logger = LoggerFactory.getLogger(ServerBootstrapper.class);
	
	public void init() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		ConfigManager configManager = ConfigManager.getInstance();
		int port = Integer.parseInt(configManager.getValue(DefineCode.LISTEN_PORT));
		String listenType = configManager.getValue(DefineCode.LISTEN_TYPE);
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ServerInitializer(listenType));
			
			serverBootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
			
			ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(port)).sync();
			future.channel().closeFuture().sync();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}