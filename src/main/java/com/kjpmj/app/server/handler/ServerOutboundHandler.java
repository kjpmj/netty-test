package com.kjpmj.app.server.handler;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ServerOutboundHandler extends ChannelOutboundHandlerAdapter{
	
	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		System.out.println("ServerOutboundHandler > bind");
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		System.out.println("ServerOutboundHandler > connect");
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("ServerOutboundHandler > disconnect");
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("ServerOutboundHandler > close");
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println("ServerOutboundHandler > deregister");
	}

//	@Override
//	public void read(ChannelHandlerContext ctx) throws Exception {
//		System.out.println("ServerOutboundHandler > read");
//	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ServerOutboundHandler > flush");
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println("ServerOutboundHandler > write");
		ByteBuf buf = (ByteBuf) msg;
		
		ctx.writeAndFlush(buf);
	}
}
