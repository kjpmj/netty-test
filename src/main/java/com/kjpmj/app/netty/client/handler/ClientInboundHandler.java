package com.kjpmj.app.netty.client.handler;

import com.kjpmj.app.netty.model.ClientToProxyRequestVO;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

public class ClientInboundHandler extends ChannelInboundHandlerAdapter {
	private ChannelHandlerContext channelHandlerContext;
	private ClientToProxyRequestVO clientToProxyRequestVO;

	public ClientInboundHandler(ChannelHandlerContext channelHandlerContext, ClientToProxyRequestVO clientToProxyRequestVO) {
		this.channelHandlerContext = channelHandlerContext;
		this.clientToProxyRequestVO = clientToProxyRequestVO;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientInboundHandler > channelActive");
		ctx.write(clientToProxyRequestVO);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ClientInboundHandler > channelRead");

		FullHttpResponse response = (FullHttpResponse) msg;
//		System.out.println(response.content().toString(CharsetUtil.UTF_8));

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
