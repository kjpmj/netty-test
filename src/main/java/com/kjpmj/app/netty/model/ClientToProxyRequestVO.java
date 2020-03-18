package com.kjpmj.app.netty.model;

import java.util.List;
import java.util.Map;

public class ClientToProxyRequestVO {
	private String className;
	private String method;
	private List<Map<String, String>> parameters;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<Map<String, String>> getParameters() {
		return parameters;
	}
	public void setParameters(List<Map<String, String>> parameters) {
		this.parameters = parameters;
	}
}
