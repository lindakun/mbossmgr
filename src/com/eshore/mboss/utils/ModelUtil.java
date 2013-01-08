package com.eshore.mboss.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableInfo;
import com.jfinal.plugin.activerecord.TableInfoMapping;

@SuppressWarnings({"rawtypes","unused"})
public class ModelUtil {

	private static final void removeOtherAttrOfModel(Model<?> model,
			String modelName, Map<String, String[]> parasMap) {
		TableInfo tableInfo = TableInfoMapping.me().getTableInfo(
				model.getClass());
		String modelNameAndDot = modelName + ".";
		for (Entry<String, String[]> e : parasMap.entrySet()) {
			String paraKey = e.getKey();
			if (paraKey.startsWith(modelNameAndDot)) {
				String paraName = paraKey.substring(modelNameAndDot.length());
				Class colType = tableInfo.getColType(paraName);
				if (colType == null) {
				}
			}
		}
	}

	private static final void getAttrsOfModel(Class<Model> modelClass,
			Map<String, String[]> parasMap) {
		TableInfo tableInfo = TableInfoMapping.me().getTableInfo(
				modelClass);
		String modelNameAndDot = StringKit.firstCharToLowerCase(modelClass.getSimpleName()) + ".";
		for (Entry<String, String[]> e : parasMap.entrySet()) {
			String paraKey = e.getKey();
			if (paraKey.startsWith(modelNameAndDot)) {
				String paraName = paraKey.substring(modelNameAndDot.length());
				Class colType = tableInfo.getColType(paraName);
				if (colType == null) {
					System.out.println("-----------"+paraName);
				}
			}
		}
	}

	public static <T> T mapToBean(Class<T> type, Map map) {
		T bean = null;
		try {
			bean = type.newInstance();

		} catch (Exception e) {
			return null;
		}
		Class c = bean.getClass();
		for (Object key : map.keySet()) {

			if (key instanceof String) {
				try {
					Field field = c.getDeclaredField((String) key);
					Object value = map.get(key);
					if (value != null) {
						field.setAccessible(true);
						field.set(bean, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		return bean;
	}

	/**
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map beanToMap(Object bean) throws IllegalArgumentException,
			IllegalAccessException {
		Class c = bean.getClass();
		Map returnMap = new HashMap();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers())
					|| Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			String propertyName = field.getName();
			field.setAccessible(true);
			Object value = field.get(bean);
			if (value != null) {
				returnMap.put(propertyName, value);
			}
		}
		return returnMap;
	}
}
