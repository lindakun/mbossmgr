package com.eshore.mboss.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.eshore.mboss.constant.HostConst;
import com.eshore.mboss.constant.Sequences;
import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.model.Host;
import com.eshore.mboss.utils.ExcelHandler;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Page;

/**
 * 主机处理类
 * 
 * @author lindakun
 * 
 */
public class HostController extends AbstractBaseController {
	// 页面显示参数变量名
	private static final String ATTR_HOST = "host";
	private static final String ATTR_HOST_LIST = "hosts";

	@Override
	public void toQueryList() {
		// 初始化分页参数
		setPagenationParam("navTab", "0", "20", "1", "10");
		render("list_host.jsp");
	}

	@Override
	public void toAdd() {
		setAttr(OPERATION, SAVE);
		render("add_host.jsp");
	}

	@Override
	public void toEdit() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				Host host = Host.dao.findById(id);
				setAttr(ATTR_HOST, host);
				setAttr(OPERATION, UPDATE);
				render("add_host.jsp");
			} else {
				returnFail("主机ID不能为空!");
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
		queryDynamicRender("list_host.jsp");
	}

	public void queryListForLookup() {
		queryDynamicRender("lookup_host.jsp");
	}

	public void queryDynamicRender(String view) {
		try {
			// 获取需要使用的参数
			String[] params = new String[] { getPara(KEYNAME) };

			// 生成条件语句
			String conditonSQL = SqlGenerator.generateFromMap(getParaMap(),
					params, TableNames.MM_HOST);
			Page<Host> hosts = Host.dao.paginate(getParaToInt(PAGE_NUM),
					getParaToInt(NUM_PER_PAGE), HostConst.QUERY_HOST_LIST,
					conditonSQL);

			// setParamBackToPage(getParaMap());
			keepPara();
			setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
			setPagenationParam(getPara(TARGET_TYPE), hosts.getTotalRow(),
					getPara(NUM_PER_PAGE), hosts.getPageNumber(),
					getPara(PAGE_NUM_SHOWN));
			setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
					HostConst.HOST_LIST_TITLE, 2)));
			setAttr(ATTR_HOST_LIST, hosts.getList());
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
			Host host = getModel(Host.class);
			host.set(Host.OPERATOR, "1234");
			if (SAVE.equalsIgnoreCase(oper)) {
				int hostIndex = SqlGenerator
						.generateIndex(Sequences.SQ_MM_HOST);
				host.set(Host.HOST_ID, hostIndex);
				host.save();
			} else if (UPDATE.equalsIgnoreCase(oper)) {
				host.update();
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
				Host.dao.deleteById(id);
				returnAndAlert();
			} else {
				returnFail("主机ID不能为空!");
			}
		} catch (NumberFormatException e) {
			returnFail("参数错误！", e);
		} catch (ActiveRecordException e1) {
			returnFail("数据库操作错误！", e1);
		} catch (Exception e2) {
			returnFail("服务器错误！", e2);
		}
	}

	public void exportData() {
		String fileName = "/temp/host_list_" + Thread.currentThread().getId()
				+ ".xls";
		// 获取需要使用的参数
		String[] params = new String[] { getPara(KEYNAME) };

		// 生成条件语句
		String conditonSQL = SqlGenerator.generateFromMap(getParaMap(), params,
				TableNames.MM_HOST);
		List<Host> activeList = Host.dao.find(HostConst.QUERY_HOST_LIST
				+ conditonSQL);
		// setParamBackToPage(getParaMap());
		keepPara();
		setAttr(KEYWORD, getAttr(getPara(KEYNAME)));

		boolean flag = ExcelHandler.exportExcel(getWebRoot() + fileName,
				activeList, HostConst.HOST_LIST_TITLE);
		if (flag) {
			// 使用ForwardUrl参数告诉前台需要下载的文件的路径及名称
			returnJson("200", "操作成功", "", "", "", fileName, "");
		} else {
			returnFail("服务器内部错误，下载失败，请重试!");
		}
	}

	/**
	 * 导入主机列表
	 */
	public void importData() {
		// List<Host> hostList = ExcelHandler.importExcel(getWebRoot()
		// + "/temp/active_list.xls");
		// for (Active active : activeList) {
		// active.save();
		// }
	}
}
