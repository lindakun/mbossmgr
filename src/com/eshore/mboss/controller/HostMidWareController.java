package com.eshore.mboss.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.eshore.mboss.constant.HostMidWareConst;
import com.eshore.mboss.constant.Sequences;
import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.model.HostMidWare;
import com.eshore.mboss.utils.ExcelHandler;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Page;

/**
 * 中间件处理类
 * 
 * @author lindakun
 * 
 */
public class HostMidWareController extends AbstractBaseController {
	// 页面显示参数变量名
	private static final String ATTR_HOST_MIDWARE = "hostMidWare";
	private static final String ATTR_HOST_MIDWARE_LIST = "hostMidWares";

	@Override
	public void toQueryList() {
		// 初始化分页参数
		setPagenationParam("navTab", "0", "20", "1", "10");
		render("list_hostMidWare.jsp");
	}

	@Override
	public void toAdd() {
		setAttr(OPERATION, SAVE);
		setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
				HostMidWareConst.HOST_MIDWARE_LIST_TITLE, 2)));
		render("add_hostMidWare.jsp");
	}

	@Override
	public void toEdit() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				HostMidWare hostMidWare = HostMidWare.dao.findById(id);
				setAttr(ATTR_HOST_MIDWARE, hostMidWare);
				setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
						HostMidWareConst.HOST_MIDWARE_LIST_TITLE, 2)));
				setAttr(OPERATION, UPDATE);
				render("add_hostMidWare.jsp");
			} else {
				returnFail("中间件ID不能为空!");
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
		queryDynamicRender("list_hostMidWare.jsp");
	}

	public void queryListForLookup() {
		queryDynamicRender("lookup_hostMidWare.jsp");
	}

	public void queryDynamicRender(String view) {
		try {
			// 获取需要使用的参数
			String[] params = new String[] { getPara(KEYNAME) };

			// 生成条件语句
			String conditonSQL = SqlGenerator.generateFromMap(getParaMap(),
					params, TableNames.MM_MID_WARE);
			Page<HostMidWare> hostMidWares = HostMidWare.dao.paginate(
					getParaToInt(PAGE_NUM), getParaToInt(NUM_PER_PAGE),
					HostMidWareConst.QUERY_HOST_MIDWARE_LIST, conditonSQL);
			// 将参数设置回页面
			keepPara();
			setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
			setPagenationParam(getPara(TARGET_TYPE),
					hostMidWares.getTotalRow(), getPara(NUM_PER_PAGE),
					hostMidWares.getPageNumber(), getPara(PAGE_NUM_SHOWN));
			setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
					HostMidWareConst.HOST_MIDWARE_LIST_TITLE, 2)));
			setAttr(ATTR_HOST_MIDWARE_LIST, hostMidWares.getList());
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
			HostMidWare hostMidWare = getModel(HostMidWare.class);
			hostMidWare.set(HostMidWare.OPERATOR, "1234");
			if (SAVE.equalsIgnoreCase(oper)) {
				int hostDbIndex = SqlGenerator
						.generateIndex(Sequences.SQ_MM_HOST_MIDWARE);
				hostMidWare.set(HostMidWare.MWID, hostDbIndex);
				hostMidWare.save();
			} else if (UPDATE.equalsIgnoreCase(oper)) {
				hostMidWare.update();
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
				HostMidWare.dao.deleteById(id);
				returnAndAlert();
			} else {
				returnFail("中间件ID不能为空!");
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
		String fileName = "/temp/hostMidWare_list_"
				+ Thread.currentThread().getId() + ".xls";
		// 获取需要使用的参数
		String[] params = new String[] { getPara(KEYNAME) };

		// 生成条件语句
		String conditonSQL = SqlGenerator.generateFromMap(getParaMap(), params,
				TableNames.MM_MID_WARE);
		List<HostMidWare> activeList = HostMidWare.dao
				.find(HostMidWareConst.QUERY_HOST_MIDWARE_LIST + conditonSQL);
		keepPara();
		setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
		boolean flag = ExcelHandler.exportExcel(getWebRoot() + fileName,
				activeList, HostMidWareConst.HOST_MIDWARE_LIST_TITLE);
		if (flag) {
			// 使用ForwardUrl参数告诉前台需要下载的文件的路径及名称
			returnJson("200", "操作成功", "", "", "", fileName, "");
		} else {
			returnFail("服务器内部错误，下载失败，请重试!");
		}
	}

}
