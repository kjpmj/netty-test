package com.kjpmj.app.server.handler;

import com.kjpmj.app.client.ClientBootstrapper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;

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
		
		ReferenceCountUtil.release(msg);
		ClientBootstrapper clientBootstrapper = new ClientBootstrapper(ctx);
		clientBootstrapper.init();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
