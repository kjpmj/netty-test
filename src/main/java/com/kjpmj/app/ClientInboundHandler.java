package com.kjpmj.app;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class ClientInboundHandler extends SimpleChannelInboundHandler<FullHttpMessage>{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientHandler			channelActive는 연결대상과 연결이 만들어지면 호출 된다.");
		
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/journal");
		
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        request.headers().set(HttpHeaderNames.HOST, "localhost:8080");
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP_DEFLATE);
        
//        ctx.channel().writeAndFlush(request);
        ctx.writeAndFlush(request);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) throws Exception {
		System.out.println("ClientHandler			channelRead0은 연결대상으로부터 메시지를 수신하면 호출 된다.");
		HttpResponse res = null;
		HttpContent content = null;
		
		if(msg instanceof HttpResponse) {
			res = (HttpResponse) msg;
		}	
		
		if(msg instanceof HttpContent) {
			content = (HttpContent) msg;
		}
		
		System.out.println(content.content().toString(CharsetUtil.UTF_8));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		cause.printStackTrace();
		ctx.close();
	}
}
