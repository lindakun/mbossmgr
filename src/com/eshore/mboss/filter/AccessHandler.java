package com.eshore.mboss.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.render.RenderFactory;

/**
 * 页面访问控制
 * 
 * @author lindakun
 * 
 */
public class AccessHandler extends Handler {
	private static final RenderFactory renderFactory = RenderFactory.me();

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		String path = request.getServletPath().toUpperCase();
		if (path.endsWith(".JSP") || path.endsWith(".HTML")) {
			renderFactory.getError404Render().setContext(request, response)
					.render();
		}
		nextHandler.handle(target, request, response, isHandled);
	}

}
