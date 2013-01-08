package com.eshore.mboss.controller;

import java.util.List;

import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.filter.BaseInterceptor;
import com.eshore.mboss.model.SysUser;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;

/**
 * 系统默认处理类
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("rawtypes")
public class DefaultController extends Controller {
	/**
	 * 默认处理
	 */
	public void index() {
		render("index.jsp");
	}

	@ClearInterceptor(ClearLayer.ALL)
	public void toLogin() {
		render("login.jsp");
	}

	public void logout() {
		removeSessionAttr(BaseInterceptor.SS_KEY_USER);
		render("login.jsp");
	}

	@ClearInterceptor(ClearLayer.ALL)
	public void login() {
		try {
			String userName = getPara(SysUser.SYS_USER_NAME);
			String userPw = getPara(SysUser.SYS_USER_PW);
			SysUser sysUser = null;
			if (StringKit.notBlank(new String[] { userName, userPw })) {
				sysUser = new SysUser();
				sysUser.set(SysUser.SYS_USER_NAME, userName);
				sysUser.set(SysUser.SYS_USER_PW, userPw);
			} else {
				setAttr("loginErr", "用户名或者密码不能为空！");
				render("login.jsp");
				return;
			}
			Object[] paramValues = new Object[sysUser.getAttrNames().length];
			List list = SysUser.dao.find(
					"SELECT * "
							+ SqlGenerator.generateFromModel(sysUser,
									TableNames.MM_SYS_USER, paramValues),
					paramValues);
			if (list == null || list.isEmpty()) {
				setAttr("loginErr", "用户名不存在或者密码错误!");
				render("login.jsp");
			} else {
				setSessionAttr(BaseInterceptor.SS_KEY_USER, list.get(0));
				redirect("?" + SysUser.SYS_USER_NAME + "=" + userName, true);
			}
		} catch (Exception e) {
			setAttr("loginErr", "登陆错误！");
			render("login.jsp");
		}
	}
}
