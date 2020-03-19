package com.kjpmj.app.netty.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjpmj.app.netty.model.ProxyRequestVO;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
	Logger logger = LoggerFactory.getLogger(ClientOutboundHandler.class);
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		logger.info("ClientOutboundHandler > write");
		ProxyRequestVO clientToProxyRequestVO = null;
		
		if(msg instanceof ProxyRequestVO) {
			clientToProxyRequestVO = (ProxyRequestVO) msg;
		}
		
//		clientToProxyRequestVO.getParameters().forEach(map -> {
//			logger.info(map.get("key") + ": " + map.get("value"));
//		});		
		
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, clientToProxyRequestVO.getExternalRequestPath());
		
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        request.headers().set(HttpHeaderNames.HOST, clientToProxyRequestVO.getExternalRequestHost() + clientToProxyRequestVO.getExternalRequestPort());
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP_DEFLATE);
        
        ChannelFuture future = ctx.writeAndFlush(request);
        future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				logger.info("ClientOutboundHandler > isSuccess: " + channelFuture.isSuccess());
			}
		});
	}
}
