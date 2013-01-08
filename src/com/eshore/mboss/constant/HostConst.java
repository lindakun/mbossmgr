package com.eshore.mboss.constant;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * 保存SQL和前台展示的标题内容
 * 
 * @author lindakun
 * 
 */
public class HostConst {
	/**
	 * 标题内容
	 */
	public static final TreeMap<String, String> HOST_LIST_TITLE = new TreeMap<String, String>(
			new Comparator<String>() {
				/*
				 * int compare(Object o1, Object o2) 返回一个基本类型的整型， 返回负数表示：o1
				 * 小于o2， 返回0 表示：o1和o2相等， 返回正数表示：o1大于o2。
				 */
				public int compare(String o1, String o2) {
					// 指定排序器按照降序排列
					// 不排序，按照进来的顺序
					return 1;
				}
			});
	static {
		HOST_LIST_TITLE.put("HOSTID", "主机ID");
		HOST_LIST_TITLE.put("HOSTNAME", "主机名称");
		HOST_LIST_TITLE.put("HOSTIP", "主机IP");
		HOST_LIST_TITLE.put("HOSTTYPE", "主机型号");
		HOST_LIST_TITLE.put("HOSTCPU", "CPU信息");
		HOST_LIST_TITLE.put("HOSTMEM", "内存信息");
		HOST_LIST_TITLE.put("HOSTSYSTEM", "操作系统");
		HOST_LIST_TITLE.put("HOSTJDK", "JDK信息");
		HOST_LIST_TITLE.put("STATE", "状态");
		HOST_LIST_TITLE.put("OPERATOR", "操作人");
		HOST_LIST_TITLE.put("REMARK", "备注");
	}

	/**
	 * 主机列表SQL头信息
	 */
	public static final String QUERY_HOST_LIST = "SELECT HOSTID, HOSTNAME, HOSTIP, HOSTTYPE,"
			+ " HOSTCPU, HOSTMEM, HOSTSYSTEM, HOSTJDK, STATE, OPERATOR, REMARK";

}
