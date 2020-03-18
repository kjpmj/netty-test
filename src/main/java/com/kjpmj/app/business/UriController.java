package com.kjpmj.app.business;

import com.kjpmj.app.netty.constant.NettyConstant;
import com.kjpmj.app.netty.exception.NettyException;
import com.kjpmj.app.netty.model.ProxyRequestVO;

public class UriController {
	public static void uriExternalInfoMapping(ProxyRequestVO vo) {
		if("/api/test".equals(vo.getRequestUri())) {
			vo.setExternalRequestHost("210.108.217.17");
			vo.setExternalRequestPort(8080);
			vo.setExternalRequestPath("/journal");
			
		}else if("/api/allat/approv".equals(vo.getRequestUri())) {
			vo.setExternalRequestHost("tx.allatpay.com");
			vo.setExternalRequestPort(80);
			vo.setExternalRequestPath("/");
		}else {
			throw new NettyException(NettyConstant.BUSINESS_ERROR_004);
		}
	}
}
