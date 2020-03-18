package com.kjpmj.app.netty.client.handler;

import com.kjpmj.app.netty.model.ProxyRequestVO;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;

public class ClientInboundHandler extends ChannelInboundHandlerAdapter {
	private ChannelHandlerContext channelHandlerContext;
	private ProxyRequestVO proxyRequestVO;

	public ClientInboundHandler(ChannelHandlerContext channelHandlerContext, ProxyRequestVO proxyRequestVO) {
		this.channelHandlerContext = channelHandlerContext;
		this.proxyRequestVO = proxyRequestVO;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientInboundHandler > channelActive");
		ctx.write(proxyRequestVO);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ClientInboundHandler > channelRead");

		FullHttpResponse response = (FullHttpResponse) msg;

		channelHandlerContext.write(response.retain());
		ChannelFuture future = ctx.close();

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				System.out.println("ClientInboundHandler > channelRead > isSuccess : " + channelFuture.isSuccess());

			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
