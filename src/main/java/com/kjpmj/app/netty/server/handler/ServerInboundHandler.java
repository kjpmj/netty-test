package com.kjpmj.app.netty.server.handler;

import org.codehaus.jackson.map.ObjectMapper;

import com.kjpmj.app.netty.client.ClientBootstrapper;
import com.kjpmj.app.netty.model.ClientToProxyRequestVO;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;

public class ServerInboundHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ServerInboundHandler > channelActive");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ServerInboundHandler > channelRead");
		
		HttpRequest req = null;
		HttpContent reqContent = null;
		
		if(msg instanceof HttpRequest) {
			req = (HttpRequest) msg;
		}
		
		if(msg instanceof HttpContent) {
			reqContent = (HttpContent) msg;
		}
		
		ByteBuf buf = reqContent.content();
		byte[] bytes = ByteBufUtil.getBytes(buf);
		
		ObjectMapper objectMapper = new ObjectMapper();
		ClientToProxyRequestVO vo = objectMapper.readValue(bytes, ClientToProxyRequestVO.class);
		
		// TODO: 이 부분에서 HttpRequest가 유효한지 검증하자 (METHOD는 반드시 POST로만 호출)
		
		// TODO: 이 부분에서 ClientToProxyRequestVO에 URI랑 HOST랑 METHOD를 담자
		
		// TODO: 이 부분에서 ClientToProxyRequestVO에 있는 URI로 분기 로직을 태우자
		// 그그그그그 일단 ClientToProxyRequestVO라는 이름을 바꾸고 Proxy에서 External Server로 요청할 때 필요한 것도 만들자
		
//		System.out.println(vo.getClassName());
//		System.out.println(vo.getMethod());
//		vo.getParameters().forEach(map -> {
//			System.out.println(map.get("key") + ": " + map.get("value"));
//		});
		
		ReferenceCountUtil.release(msg);
		ClientBootstrapper clientBootstrapper = new ClientBootstrapper(ctx, vo);
		clientBootstrapper.init();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
