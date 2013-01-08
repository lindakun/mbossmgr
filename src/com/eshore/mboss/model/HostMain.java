package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 域Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class HostMain extends Model<HostMain> {
	public static final String MAINID = "MAINID";
	public static final String HOSTID = "HOSTID";
	public static final String MWID = "MWID";
	public static final String NAME = "NAME";
	public static final String PORT = "PORT";
	public static final String PATH = "PATH";
	public static final String CONSOLE = "CONSOLE";
	public static final String DBCONECTION = "DBCONECTION";
	public static final String OPERATOR = "OPERATOR";
	public static final String REMARK = "REMARK";

	public static final HostMain dao = new HostMain();
}
