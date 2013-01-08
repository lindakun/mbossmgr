package com.eshore.mboss.config;

import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.controller.DefaultController;
import com.eshore.mboss.controller.HostController;
import com.eshore.mboss.controller.HostDBController;
import com.eshore.mboss.controller.HostMainController;
import com.eshore.mboss.controller.HostMainServController;
import com.eshore.mboss.controller.HostMainToolController;
import com.eshore.mboss.controller.HostMidWareController;
import com.eshore.mboss.controller.HostUserController;
import com.eshore.mboss.filter.AccessHandler;
import com.eshore.mboss.filter.BaseInterceptor;
import com.eshore.mboss.model.Host;
import com.eshore.mboss.model.HostDB;
import com.eshore.mboss.model.HostMain;
import com.eshore.mboss.model.HostMainServ;
import com.eshore.mboss.model.HostMainTool;
import com.eshore.mboss.model.HostMidWare;
import com.eshore.mboss.model.HostUser;
import com.eshore.mboss.model.SysUser;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * 系统初始化配置类
 * 
 * @author lindakun
 * 
 */
public class SystemConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants constants) {
		constants.setDevMode(true);
		constants.setViewType(ViewType.JSP);
	}

	@Override
	public void configHandler(Handlers handler) {
		handler.add(new ContextPathHandler());
		handler.add(new AccessHandler());
	}

	@Override
	public void configInterceptor(Interceptors ic) {
		// 添加全局拦截器
		ic.add(new BaseInterceptor());
	}

	@Override
	public void configPlugin(Plugins plugins) {
		// 1.添加C3P0连接池支持
		loadPropertyFile("jdbc.properties");
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbc.url"),
				getProperty("jdbc.username"), getProperty("jdbc.password"),
				getProperty("jdbc.driverClass"));
		plugins.add(c3p0Plugin);
		// 2.添加数据库读写插件支持
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(true);
		arp.setDialect(new OracleDialect());
		plugins.add(arp);
		// 添加表映射
		arp.addMapping(TableNames.MM_HOST, Host.HOST_ID, Host.class);
		arp.addMapping(TableNames.MM_HOST_DB, HostDB.DBID, HostDB.class);
		arp.addMapping(TableNames.MM_MAIN, HostMain.MAINID, HostMain.class);
		arp.addMapping(TableNames.MM_HOST_USER, HostUser.USERID, HostUser.class);
		arp.addMapping(TableNames.MM_MAIN_SERVICE, HostMainServ.SVID,
				HostMainServ.class);
		arp.addMapping(TableNames.MM_MAIN_TOOL, HostMainTool.TOOLID,
				HostMainTool.class);
		arp.addMapping(TableNames.MM_MID_WARE, HostMidWare.MWID,
				HostMidWare.class);
		arp.addMapping(TableNames.MM_SYS_USER, SysUser.SYS_USER_ID,
				SysUser.class);
	}

	@Override
	public void configRoute(Routes rt) {
		rt.add("/host", HostController.class, "/host");
		rt.add("/host/db", HostDBController.class, "/host");
		rt.add("/host/user", HostUserController.class, "/host");
		rt.add("/host/main", HostMainController.class, "/host");
		rt.add("/host/main/serv", HostMainServController.class, "/host");
		rt.add("/host/main/tool", HostMainToolController.class, "/host");
		rt.add("/host/midware", HostMidWareController.class, "/host");
		rt.add("/", DefaultController.class, "/");
	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 8888, "/", 5);
	}
}
