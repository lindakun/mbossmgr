package com.eshore.mboss.controller;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.eshore.mboss.constant.HostDBConst;
import com.eshore.mboss.constant.Sequences;
import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.model.Host;
import com.eshore.mboss.model.HostDB;
import com.eshore.mboss.utils.ExcelHandler;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Page;

/**
 * 数据库信息处理类
 * 
 * @author lindakun
 * 
 */
public class HostDBController extends AbstractBaseController {
	// 页面显示参数变量名
	private static final String ATTR_HOSTDB = "hostDB";
	private static final String ATTR_HOSTDB_LIST = "hostDBs";

	@Override
	public void toQueryList() {
		// 初始化分页参数
		setPagenationDefaultParamNavTab();
		render("list_hostDB.jsp");
	}

	@Override
	public void toAdd() {
		setAttr(OPERATION, SAVE);
		setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
				HostDBConst.HOSTDB_LIST_TITLE, 2)));
		render("add_hostDB.jsp");
	}

	@Override
	public void toEdit() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				HostDB hostDB = HostDB.dao.findById(id);

				BigDecimal hostId = hostDB.getBigDecimal(HostDB.HOSTID);
				Host host = Host.dao.findById(hostId);
				setAttr("_HOSTNAME", (String) host.get(Host.HOST_NAME, ""));

				setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
						HostDBConst.HOSTDB_LIST_TITLE, 2)));
				setAttr(ATTR_HOSTDB, hostDB);
				setAttr(OPERATION, UPDATE);
				render("add_hostDB.jsp");
			} else {
				returnFail("数据库ID不能为空!");
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
		queryDynamicRender("list_hostDB.jsp");
	}

	public void queryListForLookup() {
		queryDynamicRender("lookup_hostDB.jsp");
	}

	private void queryDynamicRender(String view) {
		try {
			// 获取需要使用的参数
			String[] params = new String[] { getPara(KEYNAME) };

			// 生成条件语句
			String conditonSQL = SqlGenerator.generateFromMap(getParaMap(),
					params, TableNames.MM_HOST_DB);
			Page<HostDB> hostDBs = HostDB.dao.paginate(getParaToInt(PAGE_NUM),
					getParaToInt(NUM_PER_PAGE), HostDBConst.QUERY_HOSTDB_LIST,
					conditonSQL);
			// 将参数设置回页面
			keepPara();
			setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
			setPagenationParam(getPara(TARGET_TYPE), hostDBs.getTotalRow(),
					getPara(NUM_PER_PAGE), hostDBs.getPageNumber(),
					getPara(PAGE_NUM_SHOWN));
			setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
					HostDBConst.HOSTDB_LIST_TITLE, 2)));
			setAttr(ATTR_HOSTDB_LIST, hostDBs.getList());
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
			HostDB hostDB = getAttrsOfModel(HostDB.class);
			hostDB.set(HostDB.OPERATOR, "1234");
			if (SAVE.equalsIgnoreCase(oper)) {
				int hostDbIndex = SqlGenerator
						.generateIndex(Sequences.SQ_MM_HOSTDB);
				hostDB.set(HostDB.DBID, hostDbIndex);
				hostDB.save();
			} else if (UPDATE.equalsIgnoreCase(oper)) {
				hostDB.update();
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
				HostDB.dao.deleteById(id);
				returnAndAlert();
			} else {
				returnFail("数据库ID不能为空!");
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
		String fileName = "/temp/hostDB_list_" + Thread.currentThread().getId()
				+ ".xls";
		// 获取需要使用的参数
		String[] params = new String[] { getPara(KEYNAME) };

		// 生成条件语句
		String conditonSQL = SqlGenerator.generateFromMap(getParaMap(), params,
				TableNames.MM_HOST_DB);
		List<HostDB> activeList = HostDB.dao.find(HostDBConst.QUERY_HOSTDB_LIST
				+ conditonSQL);
		keepPara();
		setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
		boolean flag = ExcelHandler.exportExcel(getWebRoot() + fileName,
				activeList, HostDBConst.HOSTDB_LIST_TITLE);
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
