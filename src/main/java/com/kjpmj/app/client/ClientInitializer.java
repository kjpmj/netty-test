package com.kjpmj.app.client;

import com.kjpmj.app.client.handler.ClientInboundHandler;
import com.kjpmj.app.client.handler.ClientOutboundHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class ClientInitializer extends ChannelInitializer<Channel>{
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(new HttpClientCodec());
		pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
		pipeline.addLast(new ClientInboundHandler());
//		pipeline.addLast(new ClientOutboundHandler());
	}
}
