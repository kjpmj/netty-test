package com.kjpmj.app.netty.client;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjpmj.app.netty.model.ProxyRequestVO;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientBootstrapper {
	Logger logger = LoggerFactory.getLogger(ClientBootstrapper.class);
	
	private ChannelHandlerContext channelHandlerContext;
	private ProxyRequestVO proxyRequestVO;
	
	public ClientBootstrapper(ChannelHandlerContext channelHandlerContext, ProxyRequestVO proxyRequestVO) {
		this.channelHandlerContext = channelHandlerContext;
		this.proxyRequestVO = proxyRequestVO;
	}

	public void init() {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class)
			.group(channelHandlerContext.channel().eventLoop())
			.handler(new ClientInitializer(channelHandlerContext, proxyRequestVO));
		
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(proxyRequestVO.getExternalRequestHost(), proxyRequestVO.getExternalRequestPort()));
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				logger.info("Client future isDone: " + channelFuture.isDone());
				logger.info("Client future isSuccess: " + channelFuture.isSuccess());
				
				if(!channelFuture.isSuccess()) {
					channelHandlerContext.close();
				}
			}
		});
	}
}
