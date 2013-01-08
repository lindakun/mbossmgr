package com.eshore.mboss.filter;

import com.eshore.mboss.model.SysUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * 基础权限拦截器
 * 
 * @author lindakun
 * 
 */
public class BaseInterceptor implements Interceptor {
	public static final String SS_KEY_USER = "loginUser";

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		SysUser sysUser = controller.getSessionAttr(SS_KEY_USER);
		if (sysUser != null) {
			ai.invoke();
		} else {
			controller.redirect("/toLogin");
		}
	}

}
