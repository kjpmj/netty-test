package com.kjpmj.app.server;

import com.kjpmj.app.server.handler.ServerInboundHandler;
import com.kjpmj.app.server.handler.ServerOutboundHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<Channel>{
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("codec", new HttpServerCodec());
		pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
		pipeline.addLast("serverOutboundHandler", new ServerOutboundHandler());
		pipeline.addLast("serverInboundHandler", new ServerInboundHandler());
	}
}
