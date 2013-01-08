package com.eshore.mboss.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 根据页面参数生成SQL语句
 * 
 * @author lindakun
 * 
 */
public class SqlGenerator {

	/**
	 * 组装SQL
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String generateFromModel(Model model, String tableName,
			Object[] paramsValus) {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM " + tableName);
		boolean FIRST_TIME = true;
		int i = 0;
		Set<Entry<String, Object>> set = (Set<Entry<String, Object>>) model
				.getAttrsEntrySet();
		for (Map.Entry<String, Object> entry : set) {
			if (entry.getValue() != null
					&& StringKit.notBlank(entry.getValue().toString())) {
				if (FIRST_TIME) {
					sql.append(" WHERE ");
					FIRST_TIME = false;
				} else {
					sql.append(" AND ");
				}
				sql.append(entry.getKey() + "=? ");
				paramsValus[i] = entry.getValue();
				i++;
			}
		}
		return sql.toString();
	}

	/**
	 * 根据页面传过来的MAP生成SQL语句
	 * 
	 * @param map
	 *            所有页面参数
	 * @param requiredParams
	 *            需要加入条件的参数
	 */
	public static String generateFromMap(Map<String, String[]> map,
			String[] requiredParams, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM " + tableName);
		boolean FIRST_TIME = true;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			for (String paramName : requiredParams) {
				if (entry.getKey().equalsIgnoreCase(paramName)) {
					// 空值的Param不处理
					if (entry.getValue() == null
							|| entry.getValue().length == 0
							|| StringKit.isBlank(entry.getValue()[0])) {
						continue;
					}
					// 第一次添加WHERE关键字
					if (FIRST_TIME) {
						sql.append(" WHERE ");
						FIRST_TIME = false;
					} else {
						sql.append(" AND ");
					}
					sql.append(generateConditionSQL(entry.getKey(),
							entry.getValue()));
				}
			}
		}
		return sql.toString();
	}

	/**
	 * 获取连接符号 =或者like < >
	 * 
	 * @param paramName
	 */
	private static String generateConditionSQL(String key, String[] value) {
		if (key.toUpperCase().endsWith("ID")
				|| key.toUpperCase().equalsIgnoreCase("TYPE")
				|| key.toUpperCase().equalsIgnoreCase("STATE")) {
			return key + "='" + value[0] + "'";
		} else if (key.toUpperCase().equalsIgnoreCase("BEGINDATE")) {
			return key + ">'" + value[0] + "'";
		} else if (key.toUpperCase().equalsIgnoreCase("ENDDATE")) {
			return key + "<'" + value[0] + "'";
		} else {
			return key + " like '%" + value[0] + "%'";
		}
	}

	/**
	 * 获取序列
	 * 
	 * @param seqName
	 * @param tableName
	 * @return
	 */
	public static int generateIndex(String seqName) {
		String sql = "SELECT " + seqName + ".nextval FROM dual";
		return Db.queryBigDecimal(sql).intValue();
	}
}
