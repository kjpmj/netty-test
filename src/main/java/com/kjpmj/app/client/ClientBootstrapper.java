package com.kjpmj.app.client;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientBootstrapper {
	private ChannelHandlerContext channelHandlerContext;
	
	public ClientBootstrapper(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}

	public void init() {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class)
			.group(channelHandlerContext.channel().eventLoop())
			.handler(new ClientInitializer(channelHandlerContext));
		
		ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8080));
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				System.out.println("Client future isDone: " + channelFuture.isDone());
				System.out.println("Client future isSuccess: " + channelFuture.isSuccess());
				
				if(!channelFuture.isSuccess()) {
					channelHandlerContext.close();
				}
			}
		});
	}
}
