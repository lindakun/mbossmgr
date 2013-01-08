<!-- 
	主机域列表界面
 -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="../import.jsp"%>

<!-- 分页控件Form -->
<form id="pagerForm" method="post" action="host/main/queryList">
	<!-- 当前页 -->
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<!-- 每页显示条数 -->
	<input type="hidden" id="numPerPage" name="numPerPage"
		value="${numPerPage}" />
	<!-- 总条数 -->
	<input type="hidden" id="totalCount" name="totalCount"
		value="${totalCount}" />
	<!-- 排序字段 -->
	<input type="hidden" name="orderField" value="${orderField}" />
	<!-- 升序降序 -->
	<input type="hidden" name="orderDirection" value="asc" />
	<!-- 窗口类型 -->
	<input type="hidden" name="targetType" value="${targetType}" />

	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><select id="hostMainSelect" class="combox" name="keyName">
							<option value="MAINID" selected="selected">域ID</option>
							<option value="HOSTID">主机ID</option>
							<option value="MWID">中间件ID</option>
							<option value="PATH">路径</option>
							<option value="PORT">端口</option>
					</select>：<input type="text" id="keyWord" name="${keyName}"
						value="${keyWord}" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="submitBtn">查询</button>
							</div>
						</div>
					</li>
					<li><a class="button" href="demo_page6.html" target="dialog"
						mask="true" title="查询框" style="display:none;"><span>高级查询</span>
					</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="host/main/toAdd" target="dialog"
				maxable=false mask=false resizable=false drawable=true
				title="[添加主机域]" width="800" height="480" rel="dlg_hostMain"><span>添加</span> </a>
			</li>
			<li><a class="delete" href="host/main/delete?id={hostMain_id}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a>
			</li>
			<li><a class="edit" href="host/main/toEdit?id={hostMain_id}"
				target="dialog" maxable=false mask=false resizable=false
				drawable=true title="[编辑主机域]" width="800" height="480" rel="dlg_hostMain"><span>修改</span>
			</a>
			</li>
			<li class="line">line</li>
			<li><a class="icon" href="host/main/exportData"
				target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span>
			</a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="60">${titles.MAINID}</th>
				<th width="60">${titles.HOSTID}</th>
				<th width="60">${titles.MWID}</th>
				<th width="60">${titles.NAME}</th>
				<th width="60">${titles.PORT}</th>
				<th width="60">${titles.PATH}</th>
				<th width="60">${titles.CONSOLE}</th>
				<th width="60">${titles.DBCONECTION}</th>
				<th width="60">${titles.OPERATOR}</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hostMains}" varStatus="i" var="hostMain">
				<tr target="hostMain_id" rel="${hostMain.MAINID}">
					<td>${hostMain.MAINID}</td>
					<td>${hostMain.HOSTID}</td>
					<td>${hostMain.MWID}</td>
					<td>${hostMain.NAME}</td>
					<td>${hostMain.PORT}</td>
					<td>${hostMain.PATH}</td>
					<td>${hostMain.CONSOLE}</td>
					<td>${hostMain.DBCONECTION}</td>
					<td>${hostMain.OPERATOR}</td>
					<td><a href="host/main/delete?id=${hostMain.MAINID}"
						target="ajaxTodo" title="确定要删除吗?" class="btnDel">删除</a>
						<a href="host/main/toEdit?id=${hostMain.MAINID}"
						target="dialog" maxable=false mask=false resizable=false
						drawable=true title="[编辑主机域]" width="800" height="480"
						class="btnEdit" rel="dlg_hostMain">修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="hostMainPageSelect" class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="2">2</option>
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共${totalCount}条</span>
		</div>
		<div class="pagination" targetType="${targetType}"
			totalCount="${totalCount}" numPerPage="${numPerPage}"
			pageNumShown="${pageNumShown}" currentPage="${pageNum}"></div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		//初始化每页显示页数选择框
		$("#hostMainPageSelect").val(fromCurNav("#numPerPage").val());
		//提交表单
		fromCurNav("#submitBtn").click(function() {
			navTabPageBreak();
		});
		//判断条件查询关键字是否为空，不为空则刷新后保留
		var defKeyName = fromCurNav("#keyWord").attr("name");
		if ($.string.isBlank(defKeyName)) {
			fromCurNav("#keyWord").attr("name", $("#hostMainSelect").val());
		} else {
			$("#hostMainSelect").val(defKeyName);
		}
		//添加条件查询选择框事件
		$("#hostMainSelect").change(
				function() {
					fromCurNav("#keyWord").attr("name",
							$(this).children('option:selected').val());
				});
	});
</script>