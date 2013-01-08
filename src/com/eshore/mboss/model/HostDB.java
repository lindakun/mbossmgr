package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 主机数据库实例Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class HostDB extends Model<HostDB> {
	public static final String DBID = "DBID";
	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String TYPENAME = "TYPENAME";
	public static final String PORT = "PORT";
	public static final String HOSTID = "HOSTID";
	public static final String INSTANCENAME = "INSTANCENAME";
	public static final String OPERATOR = "OPERATOR";
	public static final String DBVERSION = "DBVERSION";
	public static final String REMARK = "REMARK";

	public static final HostDB dao = new HostDB();
}
