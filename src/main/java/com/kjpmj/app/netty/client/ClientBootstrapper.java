package com.kjpmj.app.netty.client;

import java.net.InetSocketAddress;

import com.kjpmj.app.netty.model.ClientToProxyRequestVO;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientBootstrapper {
	private ChannelHandlerContext channelHandlerContext;
	private ClientToProxyRequestVO clientToProxyRequestVO;
	
	public ClientBootstrapper(ChannelHandlerContext channelHandlerContext, ClientToProxyRequestVO clientToProxyRequestVO) {
		this.channelHandlerContext = channelHandlerContext;
		this.clientToProxyRequestVO = clientToProxyRequestVO;
	}

	public void init() {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class)
			.group(channelHandlerContext.channel().eventLoop())
			.handler(new ClientInitializer(channelHandlerContext, clientToProxyRequestVO));
		
		// 아 이상하다
		
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
