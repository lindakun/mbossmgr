package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 系统用户Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class SysUser extends Model<SysUser> {
	public static final String SYS_USER_ID = "ID";
	public static final String SYS_USER_NAME = "USERNAME";
	public static final String SYS_USER_PW = "PASSWORD";
	public static final String SYS_USER_DEPARTMAENT = "DEPARTMAENT";
	public static final String SYS_USER_EMAIL = "EMAIL";
	public static final String REMARK = "REMARK";

	public static final SysUser dao = new SysUser();
}
