package com.kjpmj.app.netty.client;

import com.kjpmj.app.netty.client.handler.ClientInboundHandler;
import com.kjpmj.app.netty.client.handler.ClientOutboundHandler;
import com.kjpmj.app.netty.model.ProxyRequestVO;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class ClientInitializer extends ChannelInitializer<Channel>{
	private ChannelHandlerContext channelHandlerContext;
	private ProxyRequestVO proxyRequestVO;
	
	public ClientInitializer(ChannelHandlerContext channelHandlerContext, ProxyRequestVO proxyRequestVO) {
		this.channelHandlerContext = channelHandlerContext;
		this.proxyRequestVO = proxyRequestVO;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast("httpClientCodec", new HttpClientCodec());
		pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(512 * 1024));
		pipeline.addLast("clientOutboundHandler", new ClientOutboundHandler());
		pipeline.addLast("clientInboundHandler", new ClientInboundHandler(channelHandlerContext, proxyRequestVO));
	}
}
