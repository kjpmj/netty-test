package com.kjpmj.app;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

@Sharable
public class HttpCodecInitializer extends ChannelInitializer<Channel>{
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("codec", new HttpServerCodec());
		pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
		pipeline.addLast(new HttpInboudHandler());
//		pipeline.addLast(new ChannelDuplexHandler());
	}
	
}
