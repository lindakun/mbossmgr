package com.eshore.mboss.controller;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.eshore.mboss.constant.HostMainServConst;
import com.eshore.mboss.constant.Sequences;
import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.model.HostMain;
import com.eshore.mboss.model.HostMainServ;
import com.eshore.mboss.utils.ExcelHandler;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Page;

/**
 * 域服务
 * 
 * @author lindakun
 * 
 */
public class HostMainServController extends AbstractBaseController {
	// 页面显示参数变量名
	private static final String ATTR_HOST_MAIN_SERV = "hostMainServ";
	private static final String ATTR_HOST_MAIN_SERV_LIST = "hostMainServs";

	@Override
	public void toQueryList() {
		// 初始化分页参数
		setPagenationParam("navTab", "0", "20", "1", "10");
		render("list_hostMainServ.jsp");
	}

	@Override
	public void toAdd() {
		setAttr(OPERATION, SAVE);
		setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
				HostMainServConst.HOST_MAIN_SERV_LIST_TITLE, 2)));
		render("add_hostMainServ.jsp");
	}

	@Override
	public void toEdit() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				HostMainServ hostMainServ = HostMainServ.dao.findById(id);

				// 获取服务所属域的信息
				BigDecimal mainId = hostMainServ
						.getBigDecimal(HostMainServ.MAINID);
				HostMain hostMain = HostMain.dao.findById(mainId);
				setAttr("_MAINNAME", (String) hostMain.get(HostMain.NAME, ""));

				setAttr(ATTR_HOST_MAIN_SERV, hostMainServ);
				setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
						HostMainServConst.HOST_MAIN_SERV_LIST_TITLE, 2)));
				setAttr(OPERATION, UPDATE);
				render("add_hostMainServ.jsp");
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
					params, TableNames.MM_MAIN_SERVICE);
			Page<HostMainServ> hostMainServs = HostMainServ.dao.paginate(
					getParaToInt(PAGE_NUM), getParaToInt(NUM_PER_PAGE),
					HostMainServConst.QUERY_HOST_MAIN_SERV_LIST, conditonSQL);
			// 将参数设置回页面
			keepPara();
			setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
			setPagenationParam(getPara(TARGET_TYPE),
					hostMainServs.getTotalRow(), getPara(NUM_PER_PAGE),
					hostMainServs.getPageNumber(), getPara(PAGE_NUM_SHOWN));
			setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
					HostMainServConst.HOST_MAIN_SERV_LIST_TITLE, 2)));
			setAttr(ATTR_HOST_MAIN_SERV_LIST, hostMainServs.getList());
			render("list_hostMainServ.jsp");
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
			HostMainServ hostMainServ = getAttrsOfModel(HostMainServ.class);
			hostMainServ.set(HostMainServ.OPERATOR, "1234");
			if (SAVE.equalsIgnoreCase(oper)) {
				int hostDbIndex = SqlGenerator
						.generateIndex(Sequences.SQ_MM_HOSTMAIN_SERV);
				hostMainServ.set(HostMainServ.SVID, hostDbIndex);
				hostMainServ.save();
			} else if (UPDATE.equalsIgnoreCase(oper)) {
				hostMainServ.update();
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
				HostMainServ.dao.deleteById(id);
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
		String fileName = "/temp/hostMainServ_list_"
				+ Thread.currentThread().getId() + ".xls";
		// 获取需要使用的参数
		String[] params = new String[] { getPara(KEYNAME) };

		// 生成条件语句
		String conditonSQL = SqlGenerator.generateFromMap(getParaMap(), params,
				TableNames.MM_MAIN_SERVICE);
		List<HostMainServ> activeList = HostMainServ.dao
				.find(HostMainServConst.QUERY_HOST_MAIN_SERV_LIST + conditonSQL);
		keepPara();
		setAttr(KEYWORD, getAttr(getPara(KEYNAME)));

		boolean flag = ExcelHandler.exportExcel(getWebRoot() + fileName,
				activeList, HostMainServConst.HOST_MAIN_SERV_LIST_TITLE);
		if (flag) {
			// 使用ForwardUrl参数告诉前台需要下载的文件的路径及名称
			returnJson("200", "操作成功", "", "", "", fileName, "");
		} else {
			returnFail("服务器内部错误，下载失败，请重试!");
		}
	}

}
