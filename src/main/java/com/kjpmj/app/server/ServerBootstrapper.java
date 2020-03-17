package com.kjpmj.app.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ServerBootstrapper {
	private final int port;
	
	public ServerBootstrapper(int port) {
		this.port = port;
	}
	
	public void init() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ServerInitializer());
			
			ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(port));
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture channelFuture) throws Exception {
					System.out.println("Server future isDone: " + channelFuture.isDone());
					System.out.println("Server future isSuccess: " + channelFuture.isSuccess());
					
					if(!channelFuture.isSuccess()) {
						
					}
				}
			});

		} finally {
//			bossGroup.shutdownGracefully();
//			workerGroup.shutdownGracefully();
		}
	}
}
