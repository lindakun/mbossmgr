package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 中间件Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class HostMidWare extends Model<HostMidWare> {
	public static final String MWID = "MWID";
	public static final String NAME = "NAME";
	public static final String VERSION = "VERSION";
	public static final String OPERATOR = "OPERATOR";
	public static final String REMARK = "REMARK";

	public static final HostMidWare dao = new HostMidWare();
}
