package com.eshore.mboss.controller;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.eshore.mboss.constant.HostUserConst;
import com.eshore.mboss.constant.Sequences;
import com.eshore.mboss.constant.TableNames;
import com.eshore.mboss.model.Host;
import com.eshore.mboss.model.HostUser;
import com.eshore.mboss.utils.ExcelHandler;
import com.eshore.mboss.utils.SqlGenerator;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Page;

/**
 * 用户用户
 * 
 * @author lindakun
 * 
 */
public class HostUserController extends AbstractBaseController {
	// 页面显示参数变量名
	private static final String ATTR_HOST_USER = "hostUser";
	private static final String ATTR_HOST_USER_LIST = "hostUsers";

	@Override
	public void toQueryList() {
		// 初始化分页参数
		setPagenationParam("navTab", "0", "20", "1", "10");
		render("list_hostUser.jsp");
	}

	@Override
	public void toAdd() {
		setAttr(OPERATION, SAVE);
		setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
				HostUserConst.HOST_USER_LIST_TITLE, 2)));
		render("add_hostUser.jsp");
	}

	@Override
	public void toEdit() {
		try {
			Integer id = getParaToInt(ATTR_ID);
			if (id != null) {
				HostUser hostUser = HostUser.dao.findById(id);
				// 获取用户所属的主机信息
				BigDecimal hostId = hostUser.getBigDecimal(HostUser.HOSTID);
				Host host = Host.dao.findById(hostId);
				setAttr("_HOSTNAME", (String) host.get(Host.HOST_NAME, ""));

				setAttr(ATTR_HOST_USER, hostUser);
				setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
						HostUserConst.HOST_USER_LIST_TITLE, 2)));
				setAttr(OPERATION, UPDATE);
				render("add_hostUser.jsp");
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
					params, TableNames.MM_HOST_USER);
			Page<HostUser> hostUsers = HostUser.dao.paginate(
					getParaToInt(PAGE_NUM), getParaToInt(NUM_PER_PAGE),
					HostUserConst.QUERY_HOST_USER_LIST, conditonSQL);
			// 将参数设置回页面
			keepPara();
			setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
			setPagenationParam(getPara(TARGET_TYPE), hostUsers.getTotalRow(),
					getPara(NUM_PER_PAGE), hostUsers.getPageNumber(),
					getPara(PAGE_NUM_SHOWN));
			setAttr(ATTR_TITLES, JSONObject.parse(JsonKit.toJson(
					HostUserConst.HOST_USER_LIST_TITLE, 2)));
			setAttr(ATTR_HOST_USER_LIST, hostUsers.getList());
			render("list_hostUser.jsp");
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
			HostUser hostUser = getAttrsOfModel(HostUser.class);
			hostUser.set(HostUser.OPERATOR, "1234");
			if (SAVE.equalsIgnoreCase(oper)) {
				int hostDbIndex = SqlGenerator
						.generateIndex(Sequences.SQ_MM_HOSTUSER);
				hostUser.set(HostUser.USERID, hostDbIndex);
				hostUser.save();
			} else if (UPDATE.equalsIgnoreCase(oper)) {
				hostUser.update();
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
				HostUser.dao.deleteById(id);
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
		String fileName = "/temp/hostUser_list_"
				+ Thread.currentThread().getId() + ".xls";
		// 获取需要使用的参数
		String[] params = new String[] { getPara(KEYNAME) };

		// 生成条件语句
		String conditonSQL = SqlGenerator.generateFromMap(getParaMap(), params,
				TableNames.MM_HOST_USER);
		List<HostUser> activeList = HostUser.dao
				.find(HostUserConst.QUERY_HOST_USER_LIST + conditonSQL);
		keepPara();
		setAttr(KEYWORD, getAttr(getPara(KEYNAME)));
		boolean flag = ExcelHandler.exportExcel(getWebRoot() + fileName,
				activeList, HostUserConst.HOST_USER_LIST_TITLE);
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
