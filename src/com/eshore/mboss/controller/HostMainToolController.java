package com.eshore.mboss.controller;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.eshore.mboss.constant.HostMainToolConst;
import com.eshore.mboss.constant.Sequences;
import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.model.HostMain;
import com.eshore.mboss.model.HostMainTool;
import com.eshore.mboss.utils.ExcelHandler;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Page;

/**
 * 工具
 * 
 * @author lindakun
 * 
 */
public class HostMainToolController extends AbstractBaseController {
	// 页面显示参数变量名
	private static final String ATTR_HOST_MAIN_TOOL = "hostMainTool";
	private static final String ATTR_HOST_MAIN_TOOL_LIST = "hostMainTools";

	@Override
	public void toQueryList() {
		// 初始化分页参数
		setPagenationParam("navTab", "0", "20", "1", "10");
		render("list_hostMainTool.jsp");
	}

	@Override
	public void toAdd() {
		setAttr(OPERATION, SAVE);
		setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
				HostMainToolConst.HOST_MAIN_TOOL_LIST_TITLE, 2)));
		render("add_hostMainTool.jsp");
	}

	@Override
	public void toEdit() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				HostMainTool hostMainTool = HostMainTool.dao.findById(id);
				
				// 获取服务所属域的信息
				BigDecimal mainId = hostMainTool
						.getBigDecimal(HostMainTool.MAINID);
				HostMain hostMain = HostMain.dao.findById(mainId);
				setAttr("_MAINNAME", (String) hostMain.get(HostMain.NAME, ""));
				
				setAttr(ATTR_HOST_MAIN_TOOL, hostMainTool);
				setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
						HostMainToolConst.HOST_MAIN_TOOL_LIST_TITLE, 2)));
				setAttr(OPERATION, UPDATE);
				render("add_hostMainTool.jsp");
			} else {
				returnFail("用户ID不能为空!");
			}
		} catch (NumberFormatException e) {
			returnFail("参数错误！", e);
		} catch (ActiveRecordException e1) {
			returnFail("数据库操作错误！", e1);
		} catch (Exception e2) {
			returnFail("服务器错误！", e2);
		}
	}

	@Override
	public void queryList() {
		try {
			// 获取需要使用的参数
			String[] params = new String[] { getPara(KEYNAME) };

			// 生成条件语句
			String conditonSQL = SqlGenerator.generateFromMap(getParaMap(),
					params, TableNames.MM_MAIN_TOOL);
			Page<HostMainTool> hostMainTools = HostMainTool.dao.paginate(
					getParaToInt(PAGE_NUM), getParaToInt(NUM_PER_PAGE),
					HostMainToolConst.QUERY_HOST_MAIN_TOOL_LIST, conditonSQL);
			// 将参数设置回页面
			keepPara();
			setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
			setPagenationParam(getPara(TARGET_TYPE),
					hostMainTools.getTotalRow(), getPara(NUM_PER_PAGE),
					hostMainTools.getPageNumber(), getPara(PAGE_NUM_SHOWN));
			setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
					HostMainToolConst.HOST_MAIN_TOOL_LIST_TITLE, 2)));
			setAttr(ATTR_HOST_MAIN_TOOL_LIST, hostMainTools.getList());
			render("list_hostMainTool.jsp");
		} catch (NumberFormatException e) {
			returnFail("参数错误！", e);
		} catch (ActiveRecordException e1) {
			returnFail("数据库操作错误！", e1);
		} catch (Exception e2) {
			returnFail("服务器错误！", e2);
		}
	}

	@Override
	public void updateOrSave() {
		try {
			String oper = getPara(OPERATION);
			HostMainTool hostMainTool = getAttrsOfModel(HostMainTool.class);
			hostMainTool.set(HostMainTool.OPERATOR, "1234");
			if (SAVE.equalsIgnoreCase(oper)) {
				int hostDbIndex = SqlGenerator
						.generateIndex(Sequences.SQ_MM_HOSTMAIN_TOOL);
				hostMainTool.set(HostMainTool.TOOLID, hostDbIndex);
				hostMainTool.save();
			} else if (UPDATE.equalsIgnoreCase(oper)) {
				hostMainTool.update();
			}
			returnAndRefresh();
		} catch (NumberFormatException e) {
			returnFail("参数错误！", e);
		} catch (ActiveRecordException e1) {
			returnFail("数据库操作错误！", e1);
		} catch (Exception e2) {
			returnFail("服务器错误！", e2);
		}
	}

	@Override
	public void delete() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				HostMainTool.dao.deleteById(id);
				returnAndAlert();
			} else {
				returnFail("用户ID不能为空!");
			}
		} catch (NumberFormatException e) {
			returnFail("参数错误！", e);
		} catch (ActiveRecordException e1) {
			returnFail("数据库操作错误！", e1);
		} catch (Exception e2) {
			returnFail("服务器错误！", e2);
		}
	}

	@Override
	public void exportData() {
		String fileName = "/temp/hostMainTool_list_"
				+ Thread.currentThread().getId() + ".xls";
		// 获取需要使用的参数
		String[] params = new String[] { getPara(KEYNAME) };

		// 生成条件语句
		String conditonSQL = SqlGenerator.generateFromMap(getParaMap(), params,
				TableNames.MM_MAIN_TOOL);
		List<HostMainTool> activeList = HostMainTool.dao
				.find(HostMainToolConst.QUERY_HOST_MAIN_TOOL_LIST + conditonSQL);
		keepPara();
		setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
		boolean flag = ExcelHandler.exportExcel(getWebRoot() + fileName,
				activeList, HostMainToolConst.HOST_MAIN_TOOL_LIST_TITLE);
		if (flag) {
			// 使用ForwardUrl参数告诉前台需要下载的文件的路径及名称
			returnJson("200", "操作成功", "", "", "", fileName, "");
		} else {
			returnFail("服务器内部错误，下载失败，请重试!");
		}
	}

}
