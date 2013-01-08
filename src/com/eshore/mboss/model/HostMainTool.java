package com.eshore.mboss.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 域工具Model
 * 
 * @author lindakun
 * 
 */
@SuppressWarnings("serial")
public class HostMainTool extends Model<HostMainTool> {
	public static final String TOOLID = "TOOLID";
	public static final String MAINID = "MAINID";
	public static final String TOOLNAME = "TOOLNAME";
	public static final String VERSION = "VERSION";
	public static final String PATH = "PATH";
	public static final String OPERATOR = "OPERATOR";
	public static final String REMARK = "REMARK";

	public static final HostMainTool dao = new HostMainTool();
}
