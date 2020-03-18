package com.kjpmj.app.client.handler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class ClientOutboundHandler extends ChannelOutboundHandlerAdapter{

//	@Override
//	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
//			ChannelPromise promise) throws Exception {
//		System.out.println("ClientOutboundHandler > connect");
//		System.out.println("ClientOutboundHandler > remoteAddress: " + remoteAddress.toString());
//		System.out.println("ClientOutboundHandler > SocketAddress: " + localAddress.toString());
//		
//		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/journal");
//		
//		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
//        request.headers().set(HttpHeaderNames.HOST, "localhost:8080");
//        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP_DEFLATE);
//        
////        ctx.write(request);
//        ctx.fireChannelActive();
////        ctx.connect(new InetSocketAddress("localhost", 1234));
//	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("ClientOutboundHandler > write");
		
//		ctx.pipeline().names().forEach(name -> System.out.println(name));
		
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/journal");
		
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        request.headers().set(HttpHeaderNames.HOST, "localhost:8080");
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP_DEFLATE);
        
        ChannelFuture future = ctx.writeAndFlush(request);
        future.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				System.out.println(channelFuture.isSuccess());
//				channelFuture.channel().close();
			}
		});
	}
}
