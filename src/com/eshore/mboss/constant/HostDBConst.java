package com.eshore.mboss.constant;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * 保存SQL和前台展示的标题内容
 * 
 * @author lindakun
 * 
 */
public class HostDBConst {
	/**
	 * 标题内容
	 */
	public static final TreeMap<String, String> HOSTDB_LIST_TITLE = new TreeMap<String, String>(
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
		HOSTDB_LIST_TITLE.put("DBID", "数据库ID");
		HOSTDB_LIST_TITLE.put("USERNAME", "用户名");
		HOSTDB_LIST_TITLE.put("PASSWORD", "密码");
		HOSTDB_LIST_TITLE.put("TYPENAME", "类型");
		HOSTDB_LIST_TITLE.put("PORT", "端口");
		HOSTDB_LIST_TITLE.put("HOSTID", "所属主机");
		HOSTDB_LIST_TITLE.put("INSTANCENAME", "数据库名称");
		HOSTDB_LIST_TITLE.put("OPERATOR", "操作人");
		HOSTDB_LIST_TITLE.put("DBVERSION", "版本信息");
		HOSTDB_LIST_TITLE.put("REMARK", "备注信息");
	}

	/**
	 * 主机列表SQL头信息
	 */
	public static final String QUERY_HOSTDB_LIST = "SELECT DBID, USERNAME, PASSWORD, TYPENAME,"
			+ " PORT, HOSTID, INSTANCENAME, OPERATOR, DBVERSION, REMARK";

}
