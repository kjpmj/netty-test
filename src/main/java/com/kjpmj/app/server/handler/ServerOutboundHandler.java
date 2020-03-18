package com.kjpmj.app.server.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.ReferenceCountUtil;

public class ServerOutboundHandler extends ChannelOutboundHandlerAdapter{

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("ServerOutboundHandler > write");
		
		FullHttpResponse response = null;
		
		if(msg instanceof FullHttpResponse) {
			response = (FullHttpResponse) msg; 
		}
		
		ReferenceCountUtil.release(msg);
		
		ChannelFuture future = ctx.writeAndFlush(response);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				System.out.println("ServerOutboundHandler > isSuccess: " + channelFuture.isSuccess());
				channelFuture.channel().close();
			}
		});

	}
}
