package com.eshore.mboss.controller;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.eshore.mboss.utils.TypeConvertor;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableInfo;
import com.jfinal.plugin.activerecord.TableInfoMapping;

public abstract class AbstractBaseController extends Controller {
	Logger logger = LogManager
			.getLogger(AbstractBaseController.class.getName());

	/******************** 字典常量名 ************************/
	/**
	 * 口类型窗
	 */
	protected static final String TARGET_TYPE = "targetType";
	/**
	 * 分页总数
	 */
	protected static final String TOTAL_COUNT = "totalCount";
	/**
	 * 每页条数
	 */
	protected static final String NUM_PER_PAGE = "numPerPage";
	/**
	 * 当前页号
	 */
	protected static final String PAGE_NUM = "pageNum";
	/**
	 * 预显示页号
	 */
	protected static final String PAGE_NUM_SHOWN = "pageNumShown";

	/**
	 * 增删改查操作
	 */
	protected static final String OPERATION = "operation";

	/**
	 * 需要刷新的Nav
	 */
	protected static final String NAV_ID = "navId";

	/**
	 * 
	 */
	protected static final String KEYNAME = "keyName";
	/**
	 * 
	 */
	protected static final String KEYWORD = "keyWord";

	/**
	 * 
	 */
	protected static final String ATTR_ID = "id";

	/**
	 * 数据查询列表 标题
	 */
	protected static final String ATTR_TITLES = "titles";
	/******************** 字典常量名 结束 ************************/

	/******************** 字典常量值 ************************/
	/**
	 * 
	 */
	protected static final String SAVE = "SAVE";
	/**
	 * 
	 */
	protected static final String UPDATE = "UPDATE";

	/******************** 字典常量值 结束 ************************/

	/**
	 * 显示查询列表界面
	 */
	abstract public void toQueryList();

	/**
	 * 显示添加界面
	 */
	abstract public void toAdd();

	/**
	 * 显示编辑界面
	 */
	abstract public void toEdit();

	/**
	 * 查询列表
	 */
	abstract public void queryList();

	/**
	 * 更新或者添加
	 */
	abstract public void updateOrSave();

	/**
	 * 删除
	 */
	abstract public void delete();

	/**
	 * 导出列表
	 */
	abstract public void exportData();

	/**
	 * 返回失败提示JSON
	 * 
	 * @param msg
	 *            异常信息
	 */
	protected void returnFail(String msg) {
		logger.error(msg);
		returnJson("300", msg, "", "", "", "", "");
	}

	/**
	 * 返回失败提示JSON并提示异常信息
	 * 
	 * @param msg
	 *            异常信息
	 * @param e
	 */
	protected void returnFail(String msg, Exception e) {
		logger.error(msg, e);
		returnJson("300", msg + " 异常信息：" + e.toString(), "", "", "", "", "");
	}

	/**
	 * 会话超时提示JSON
	 */
	protected void returnTimeout() {
		// logger.error("会话超时");
		returnJson("301", "", "", "", "", "", "");
	}

	/**
	 * 返回成功提示JSON
	 */
	protected void returnAndAlert() {
		returnJson("200", "操作成功", "", "", "", "", "");
	}

	/**
	 * 返回成功提示并关闭窗口或者Tab
	 */
	protected void returnAndClose() {
		returnJson("200", "操作成功", "", "", "closeCurrent", "", "");
	}

	/**
	 * 返回成功提示并关闭窗口和刷新指定Nav
	 */
	protected void returnAndRefresh() {
		String navId = getPara(NAV_ID) == null ? "" : getPara(NAV_ID);
		returnJson("200", "操作成功", navId, "", "closeCurrent", "", "");
	}

	/**
	 * 返回提示JSON
	 * 
	 * @param statusCode
	 *            状态码
	 * @param message
	 *            要提示的内容
	 * @param navTabId
	 *            窗口ID
	 * @param rel
	 * @param callbackType
	 *            回调函数
	 * @param forwardUrl
	 *            跳转 callbackType="forward"时需要forwardUrl值
	 * @param confirmMsg
	 */
	protected void returnJson(String statusCode, String message,
			String navTabId, String rel, String callbackType,
			String forwardUrl, String confirmMsg) {
		// 是否成功 200成功,300失败,301超时
		setAttr("statusCode", statusCode);
		// 提示信息内容
		setAttr("message", message);
		// 标签页ID
		setAttr("navTabId", navTabId);
		setAttr("rel", rel);
		// 回调函数 closeCurrent即关闭当前窗口或者Tab
		setAttr("callbackType", callbackType);
		// 当只有callbackType="forward"时需要forwardUrl值
		setAttr("forwardUrl", forwardUrl);
		setAttr("confirmMsg", confirmMsg);
		renderJson();
	}

	/**
	 * 设置分页参数
	 * 
	 * @param targetType
	 *            窗口类型
	 * @param totalCount
	 *            总条数
	 * @param numPerPage
	 *            每页显示条数
	 * @param pageNum
	 *            当前页数
	 * @param pageNumShown
	 *            预显示页数
	 */
	protected void setPagenationParam(Object targetType, Object totalCount,
			Object numPerPage, Object pageNum, Object pageNumShown) {
		setAttr(TARGET_TYPE, targetType);
		setAttr(TOTAL_COUNT, totalCount);
		setAttr(NUM_PER_PAGE, numPerPage);
		setAttr(PAGE_NUM, pageNum);
		setAttr(PAGE_NUM_SHOWN, pageNumShown);
	}

	/**
	 * 设置默认的分页参数 navTab
	 */
	protected void setPagenationDefaultParamNavTab() {
		setPagenationParam("navTab", "0", "20", "1", "10");
	}

	/**
	 * 设置默认的分页参数 navTab
	 */
	protected void setPagenationDefaultParamDialog() {
		setPagenationParam("dialog", "0", "20", "1", "10");
	}

	/**
	 * 获取WebRoot的根目录
	 * 
	 * @return WebRoot根目录
	 */
	protected String getWebRoot() {
		return getRequest().getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 将参数设置回页面
	 * 
	 * @param requestParams
	 */
	protected void setParamBackToPage(Map<String, String[]> requestParams) {
		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			setAttr(entry.getKey(), entry.getValue()[0]);
		}
	}

	/**
	 * 查找列表
	 */
	public void toLookup() {
		String objString = getPara("obj").toUpperCase();
		setPagenationDefaultParamDialog();
		if (objString.equalsIgnoreCase("HOST")) {
			render("lookup_host.jsp");
		} else if (objString.equalsIgnoreCase("HOSTMIDWARE")) {
			render("lookup_hostMidWare.jsp");
		}else if (objString.equalsIgnoreCase("HOSTMAIN")) {
			render("lookup_hostMain.jsp");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T getAttrsOfModel(Class<? extends Model> modelClass) {
		Model<?> model = null;
		String modelNameAndDot = StringKit.firstCharToLowerCase(modelClass
				.getSimpleName()) + ".";
		Map<String, String[]> parasMap = getRequest().getParameterMap();
		TableInfo tableInfo = TableInfoMapping.me().getTableInfo(modelClass);
		try {
			model = modelClass.newInstance();
			Field[] fields = modelClass.getFields();
			for (int i = 0; i < fields.length; i++) {
				try {
					String paraName = fields[i].getName();
					if (!tableInfo.hasColumnLabel(paraName)) {
						continue;
					}
					String[] paraValue = parasMap.get(modelNameAndDot
							+ paraName);
					Class colType = tableInfo.getColType(paraName);
					Object value = paraValue[0] != null ? TypeConvertor
							.convert(colType, paraValue[0]) : null;
					// Object value = paraValue[0] != null ?
					// TypeConverter.convert(colType, paraValue[0]) : null;
					model.set(paraName, value);
				} catch (Exception e) {
					logger.warn("生成Model出错", e);
				}
			}
		} catch (Exception e) {
			logger.error("获取页面参数转换为Model时出错", e);
			throw new RuntimeException();
		}
		return (T) model;
	}
}
