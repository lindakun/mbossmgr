<!-- 
	数据库列表界面
 -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="../import.jsp"%>

<!-- 分页控件Form -->
<form id="pagerForm" method="post" action="host/db/queryList">
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
					<td><select id="hostDBSelect" class="combox" name="keyName">
							<option value="DBID">数据库ID</option>
							<option value="USERNAME">用户名</option>
							<option value="TYPENAME">类型</option>
							<option value="HOSTID">主机ID</option>
					</select>：<input type="text" id="keyWord" name="${keyName}"
						value="${keyWord}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="hostDBBtn">查询</button>
							</div>
						</div></li>
					<li><a class="button" href="demo_page6.html" target="dialog"
						mask="true" title="查询框" style="display:none;"><span>高级查询</span>
					</a></li>
				</ul>
			</div>
		</div>
	</div>
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="host/db/toAdd" target="dialog"
				maxable=false mask=false resizable=false drawable=true
				title="[添加主机数据库]" width="800" height="480" rel="dlg_hostDB"><span>添加</span> </a></li>
			<li><a class="delete" href="host/db/delete?id={hostDB_id}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a></li>
			<li><a class="edit" href="host/db/toEdit?id={hostDB_id}"
				target="dialog" maxable=false mask=false resizable=false
				drawable=true title="[编辑主机数据库]" width="800" height="480" rel="dlg_hostDB"><span>修改</span>
			</a></li>
			<li class="line">line</li>
			<li><a class="icon" href="host/db/exportData" target="dwzExport"
				targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span> </a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="60">${titles.DBID}</th>
				<th width="100">${titles.USERNAME}</th>
				<th width="80">${titles.PASSWORD}</th>
				<th width="60">${titles.TYPENAME}</th>
				<th width="60">${titles.PORT}</th>
				<th width="60">${titles.HOSTID}</th>
				<th width="80">${titles.INSTANCENAME}</th>
				<th width="80">${titles.DBVERSION}</th>
				<th width="60">${titles.OPERATOR}</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hostDBs}" varStatus="i" var="hostDB">
				<tr target="hostDB_id" rel="${hostDB.DBID}">
					<td>${hostDB.DBID}</td>
					<td>${hostDB.USERNAME}</td>
					<td>${hostDB.PASSWORD}</td>
					<td>${hostDB.TYPENAME}</td>
					<td>${hostDB.PORT}</td>
					<td>${hostDB.HOSTID}</td>
					<td>${hostDB.INSTANCENAME}</td>
					<td>${hostDB.DBVERSION}</td>
					<td>${hostDB.OPERATOR}</td>
					<td><a href="host/db/delete?id=${hostDB.DBID}"
						target="ajaxTodo" title="确定要删除吗?" class="btnDel">删除</a>
						<a href="host/db/toEdit?id=${hostDB.DBID}"
						target="dialog" maxable=false mask=false resizable=false
						drawable=true title="[编辑主机数据库]" width="800" height="480"
						class="btnEdit" rel="dlg_hostDB">修改</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="hostDBpageSelect" class="combox"
				name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
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
		$("#hostDBpageSelect").val(fromCurNav("#numPerPage").val());
		//提交表单
		$("#hostDBBtn").click(function() {
			navTabPageBreak();
		});
		//判断条件查询关键字是否为空，不为空则刷新后保留
		var defKeyName = fromCurNav("#keyWord").attr("name");
		if($.string.isBlank(defKeyName)){
			fromCurNav("#keyWord").attr("name",$("#hostDBSelect").val());
		}else{
			$("#hostDBSelect").val(defKeyName);
		}
		//添加条件查询选择框事件
		$("#hostDBSelect").change(
				function() {
					fromCurNav("#keyWord").attr("name",
							$(this).children('option:selected').val());
				});
	});
</script>