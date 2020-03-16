package com.kjpmj.app;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;

public class HttpInboudHandler extends SimpleChannelInboundHandler<HttpObject>{
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		HttpRequest req = (HttpRequest) msg;
		System.out.println(req.uri());
		ctx.writeAndFlush("키키키");
		ctx.fireChannelRead("힘들다");
		
//		FullHttpResponse res = new DefaultFullHttpResponse(version, status )
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
