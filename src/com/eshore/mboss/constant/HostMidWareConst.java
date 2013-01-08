package com.eshore.mboss.constant;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * 保存SQL和前台展示的标题内容
 * 
 * @author lindakun
 * 
 */
public class HostMidWareConst {
	/**
	 * 标题内容
	 */
	public static final TreeMap<String, String> HOST_MIDWARE_LIST_TITLE = new TreeMap<String, String>(
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
		HOST_MIDWARE_LIST_TITLE.put("MWID", "中间件ID");
		HOST_MIDWARE_LIST_TITLE.put("NAME", "中间件名称");
		HOST_MIDWARE_LIST_TITLE.put("VERSION", "版本");
		HOST_MIDWARE_LIST_TITLE.put("OPERATOR", "操作人");
		HOST_MIDWARE_LIST_TITLE.put("REMARK", "备注");
	}

	/**
	 * 主机列表SQL头信息
	 */
	public static final String QUERY_HOST_MIDWARE_LIST = "SELECT MWID, NAME, VERSION, OPERATOR, REMARK";

}
