package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 主机Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class Host extends Model<Host> {
	public static final String HOST_ID = "HOSTID";
	public static final String HOST_NAME = "HOSTNAME";
	public static final String HOST_IP = "HOSTIP";
	public static final String HOST_TYPE = "HOSTTYPE";
	public static final String HOST_CPU = "HOSTCPU";
	public static final String HOST_MEM = "HOSTMEM";
	public static final String HOST_SYSTEM = "HOSTSYSTEM";
	public static final String HOST_DATABASE = "HOSTDATABASE";
	public static final String HOST_JDK = "HOSTJDK";
	public static final String HOST_APPLICATION = "HOSTAPPLICATION";
	public static final String HOST_STATE = "STATE";
	public static final String OPERATOR = "OPERATOR";
	public static final String REMARK = "REMARK";

	public static final Host dao = new Host();
}
