package com.kjpmj.app;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		System.out.println("Port : " + port);
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new HttpCodecInitializer());
			
			ChannelFuture future = bootstrap.bind(new InetSocketAddress(port));
			future.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture channelFuture) throws Exception {
					if(channelFuture.isSuccess()) {
						System.out.println("Channel bound");
					} else {
						System.err.println("Bind attempt failed");
						channelFuture.cause().printStackTrace();
					}
					
				}
			});
		} finally {
			
		}
	}
}
