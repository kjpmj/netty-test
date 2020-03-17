package com.kjpmj.app;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpInboudHandler extends SimpleChannelInboundHandler<FullHttpMessage>{
	
	ChannelFuture future;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ServerHandler			channelActive는 연결대상과 연결이 만들어지면 호출 된다.");
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) throws Exception {
		System.out.println("ServerHandler			channelRead0은 연결대상으로부터 메시지를 수신하면 호출 된다.");
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class)
			.handler(new ClientInitializer());
		
		bootstrap.group(ctx.channel().eventLoop());
		
		future = bootstrap.connect(new InetSocketAddress("localhost", 8080));
		future.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("future isSuccess: " + future.isSuccess());
			}
		});
		
		HttpRequest req = null;
		HttpContent content = null;
		
		if(msg instanceof HttpRequest) {
			req = (HttpRequest) msg;
		}
		
		if(msg instanceof HttpContent) {
			content = (HttpContent) msg;
		}
		
		System.out.println("ServerHandler			" + content.content().toString(CharsetUtil.UTF_8));
				
		System.out.println("ServerHandler			" + req.uri());
		
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer("success", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "null");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_MAX_AGE, "3600");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Accept, X-Requested-With, remember-me");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		response.headers().set(HttpHeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS, "Access-Control-Allow-Origin,Access-Control-Allow-Credentials");
		
		ctx.write(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
