package com.eshore.mboss.controller;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.eshore.mboss.constant.HostMainConst;
import com.eshore.mboss.constant.Sequences;
import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.model.Host;
import com.eshore.mboss.model.HostMain;
import com.eshore.mboss.model.HostMidWare;
import com.eshore.mboss.utils.ExcelHandler;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Page;

/**
 * 域处理类
 * 
 * @author lindakun
 * 
 */
public class HostMainController extends AbstractBaseController {
	// 页面显示参数变量名
	private static final String ATTR_HOST_MAIN = "hostMain";
	private static final String ATTR_HOST_MAIN_LIST = "hostMains";

	@Override
	public void toQueryList() {
		// 初始化分页参数
		setPagenationParam("navTab", "0", "20", "1", "10");
		render("list_hostMain.jsp");
	}

	@Override
	public void toAdd() {
		setAttr(OPERATION, SAVE);
		setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
				HostMainConst.HOST_MAIN_LIST_TITLE, 2)));
		render("add_hostMain.jsp");
	}

	@Override
	public void toEdit() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				HostMain hostMain = HostMain.dao.findById(id);

				// 获取域所属的主机信息
				BigDecimal hostId = hostMain.getBigDecimal(HostMain.HOSTID);
				Host host = Host.dao.findById(hostId);
				setAttr("_HOSTNAME", (String) host.get(Host.HOST_NAME, ""));

				BigDecimal mwId = hostMain.getBigDecimal(HostMain.MWID);
				HostMidWare hostMidWare = HostMidWare.dao.findById(mwId);
				setAttr("_MWNAME",
						(String) hostMidWare.get(HostMidWare.NAME, ""));

				setAttr(ATTR_HOST_MAIN, hostMain);
				setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
						HostMainConst.HOST_MAIN_LIST_TITLE, 2)));
				setAttr(OPERATION, UPDATE);
				render("add_hostMain.jsp");
			} else {
				returnFail("域ID不能为空!");
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
		queryDynamicRender("list_hostMain.jsp");
	}

	public void queryListForLookup() {
		queryDynamicRender("lookup_hostMain.jsp");
	}

	public void queryDynamicRender(String view) {
		try {
			// 获取需要使用的参数
			String[] params = new String[] { getPara(KEYNAME) };

			// 生成条件语句
			String conditonSQL = SqlGenerator.generateFromMap(getParaMap(),
					params, TableNames.MM_MAIN);
			Page<HostMain> hostMains = HostMain.dao.paginate(
					getParaToInt(PAGE_NUM), getParaToInt(NUM_PER_PAGE),
					HostMainConst.QUERY_HOST_MAIN_LIST, conditonSQL);
			// 将参数设置回页面
			keepPara();
			setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
			setPagenationParam(getPara(TARGET_TYPE), hostMains.getTotalRow(),
					getPara(NUM_PER_PAGE), hostMains.getPageNumber(),
					getPara(PAGE_NUM_SHOWN));
			setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
					HostMainConst.HOST_MAIN_LIST_TITLE, 2)));
			setAttr(ATTR_HOST_MAIN_LIST, hostMains.getList());
			render(view);
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
			HostMain hostMain = getAttrsOfModel(HostMain.class);
			hostMain.set(HostMain.OPERATOR, "1234");
			if (SAVE.equalsIgnoreCase(oper)) {
				int hostDbIndex = SqlGenerator
						.generateIndex(Sequences.SQ_MM_HOSTMAIN);
				hostMain.set(HostMain.MAINID, hostDbIndex);
				hostMain.save();
			} else if (UPDATE.equalsIgnoreCase(oper)) {
				hostMain.update();
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
				HostMain.dao.deleteById(id);
				returnAndAlert();
			} else {
				returnFail("域ID不能为空!");
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
		String fileName = "/temp/hostMain_list_"
				+ Thread.currentThread().getId() + ".xls";
		// 获取需要使用的参数
		String[] params = new String[] { getPara(KEYNAME) };

		// 生成条件语句
		String conditonSQL = SqlGenerator.generateFromMap(getParaMap(), params,
				TableNames.MM_MAIN);
		List<HostMain> activeList = HostMain.dao
				.find(HostMainConst.QUERY_HOST_MAIN_LIST + conditonSQL);
		keepPara();
		setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
		boolean flag = ExcelHandler.exportExcel(getWebRoot() + fileName,
				activeList, HostMainConst.HOST_MAIN_LIST_TITLE);
		if (flag) {
			// 使用ForwardUrl参数告诉前台需要下载的文件的路径及名称
			returnJson("200", "操作成功", "", "", "", fileName, "");
		} else {
			returnFail("服务器内部错误，下载失败，请重试!");
		}
	}

	@Override
	public void toLookup() {
		super.toLookup();
	}
}
