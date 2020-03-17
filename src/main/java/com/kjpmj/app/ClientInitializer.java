package com.kjpmj.app;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

@Sharable
public class ClientInitializer extends ChannelInitializer<Channel>{
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(new HttpClientCodec());
		pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
//		pipeline.addLast(new ClientOutboundHandler());
		pipeline.addLast(new ClientInboundHandler());
		
	}
}
