package com.kjpmj.app.client.handler;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
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

@Sharable
public class ClientOutboundHandler extends ChannelOutboundHandlerAdapter{

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		System.out.println("ClientOutboundHandler > connect");
		System.out.println("ClientOutboundHandler > remoteAddress: " + remoteAddress.toString());
		System.out.println("ClientOutboundHandler > SocketAddress: " + localAddress.toString());
		
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/journal");
		
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        request.headers().set(HttpHeaderNames.HOST, "localhost:8080");
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP_DEFLATE);
        
        ctx.write(request);
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("ClientOutboundHandler > write");
		System.out.println("ClientOutboundHandler > remoteAddress: " + ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
	}
	
	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientOutboundHandler > flush");
	}
}
