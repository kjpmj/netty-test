package com.kjpmj.app.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

public class ClientInboundHandler extends ChannelInboundHandlerAdapter{
	private ChannelHandlerContext channelHandlerContext;

	public ClientInboundHandler(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientInboundHandler > channelActive");
		ctx.write("go".getBytes(CharsetUtil.UTF_8));
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ClientInboundHandler > channelRead");
		
		FullHttpResponse response = (FullHttpResponse) msg;
		System.out.println(response.content().toString(CharsetUtil.UTF_8));
		
		channelHandlerContext.write(response.retain());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
