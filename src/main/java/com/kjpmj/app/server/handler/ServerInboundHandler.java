package com.kjpmj.app.server.handler;

import com.kjpmj.app.client.ClientBootstrapper;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

@Sharable
public class ServerInboundHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ServerInboundHandler > channelActive");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ServerInboundHandler > channelRead");
		
		HttpRequest req = null;
		if(msg instanceof HttpRequest) {
			req = (HttpRequest) msg;
		}
		
		System.out.println("ServerInboundHandler > URI: " + req.uri());
//		ReferenceCountUtil.release(msg);
//		ClientBootstrapper clientBootstrapper = new ClientBootstrapper(ctx);
//		clientBootstrapper.init();
		
		ctx.write(Unpooled.copiedBuffer("hi netty", CharsetUtil.UTF_8));
		ctx.fireChannelRead(Unpooled.copiedBuffer("hi netty", CharsetUtil.UTF_8));
//		ctx.close();
	}
}
