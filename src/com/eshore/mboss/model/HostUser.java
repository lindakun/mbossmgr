package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 主机用户Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class HostUser extends Model<HostUser> {
	public static final String USERID = "USERID";
	public static final String USERNAME = "USERNAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String LOGINTYPE = "LOGINTYPE";
	public static final String DEFAULTPATH = "DEFAULTPATH";
	public static final String PORT = "PORT";
	public static final String HOSTID = "HOSTID";
	public static final String OPERATOR = "OPERATOR";
	public static final String REMARK = "REMARK";

	public static final HostUser dao = new HostUser();
}
