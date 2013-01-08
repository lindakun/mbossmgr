package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 域服务Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class HostMainServ extends Model<HostMainServ> {
	public static final String SVID = "SVID";
	public static final String SVNAME = "SVNAME";
	public static final String SVPATH = "SVPATH";
	public static final String MAINID = "MAINID";
	public static final String OPERATOR = "OPERATOR";
	public static final String REMARK = "REMARK";

	public static final HostMainServ dao = new HostMainServ();
}
