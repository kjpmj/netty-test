package com.kjpmj.app;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

public class ClientOutboundHandler extends ChannelOutboundHandlerAdapter{

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandler : bind");
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandler : connect");
		
		
		
//		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/journal");
//		
//		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
//        request.headers().set(HttpHeaderNames.HOST, "localhost:8080");
//        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP_DEFLATE);
//        
//        ctx.channel().writeAndFlush(request);
		
//		promise.setSuccess();
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandler : disconnect");
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandler : close");
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandler : deregister");
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OutboundHandler : read");
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("OutboundHandler : write");
//		System.out.println(msg.toString());
		HttpRequest request = null;
		
		if(request instanceof HttpRequest) {
			request = (HttpRequest) msg;
		}
		
		System.out.println("request Url : " + request.uri());
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println("OutboundHandler : flush");
		ctx.channel().read();
	}

}
