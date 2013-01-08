<!-- 
	主机列表界面
 -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="../import.jsp"%>

<!-- 分页控件Form -->
<form id="pagerForm" method="post" action="host/queryList">
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
					<td><select id="hostSelect" class="combox" name="keyName">
							<option value="HOSTID" selected="selected">主机ID</option>
							<option value="HOSTNAME">主机名称</option>
							<option value="HOSTIP">主机IP</option>
							<option value="HOSTTYPE">主机型号</option>
							<option value="STATE">主机状态</option>
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
			<li><a class="add" href="host/toAdd" target="dialog"
				maxable=false mask=false resizable=false drawable=true
				title="[添加主机]" width="800" height="480"><span>添加</span> </a>
			</li>
			<li><a class="delete" href="host/delete?id={host_id}"
				target="ajaxTodo" title="确定要删除吗?"><span>删除</span> </a>
			</li>
			<li><a class="edit" href="host/toEdit?id={host_id}"
				target="dialog" maxable=false mask=false resizable=false
				drawable=true title="[编辑主机]" width="800" height="480"><span>修改</span>
			</a>
			</li>
			<li class="line">line</li>
			<li><a class="icon" href="host/exportData" target="dwzExport"
				targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span> </a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="40">${titles.HOSTID}</th>
				<th width="100">${titles.HOSTNAME}</th>
				<th width="120">${titles.HOSTIP}</th>
				<th width="60">${titles.HOSTTYPE}</th>
				<th width="60">${titles.HOSTCPU}</th>
				<th width="60">${titles.HOSTMEM}</th>
				<th width="60">${titles.HOSTSYSTEM}</th>
				<th width="60">${titles.HOSTJDK}</th>
				<th width="40">${titles.STATE}</th>
				<th width="60">${titles.OPERATOR}</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hosts}" varStatus="i" var="host">
				<tr target="host_id" rel="${host.HOSTID}">
					<td>${host.HOSTID}</td>
					<td>${host.HOSTNAME}</td>
					<td>${host.HOSTIP}</td>
					<td>${host.HOSTTYPE}</td>
					<td>${host.HOSTCPU}</td>
					<td>${host.HOSTMEM}</td>
					<td>${host.HOSTSYSTEM}</td>
					<td>${host.HOSTJDK}</td>
					<td><c:choose>
							<c:when test="${host.STATE == -1}">已移除</c:when>
							<c:when test="${host.STATE == 0}">停用</c:when>
							<c:when test="${host.STATE == 1}">启用</c:when>
							<c:otherwise>未知状态${host.STATE}</c:otherwise>
						</c:choose></td>
					<td>${host.OPERATOR}</td>
					<td><a href="host/delete?id=${host.HOSTID}" target="ajaxTodo"
						title="确定要删除吗?" class="btnDel">删除</a> <a
						href="host/toEdit?id=${host.HOSTID}" target="dialog" maxable=false
						mask=false resizable=false drawable=true title="[编辑主机]"
						width="800" height="480" class="btnEdit">修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="hostPageSelect" class="combox"
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
		fromCurNav("#hostPageSelect").val(fromCurNav("#numPerPage").val());
		//fromCurNav("#pageSelect", navTab.getCurrentPanel()).val(fromCurNav("#numPerPage").val());
		//提交表单
		fromCurNav("#submitBtn").click(function() {
			navTabPageBreak();
		});
		//判断条件查询关键字是否为空，不为空则刷新后保留
		var defKeyName = fromCurNav("#keyWord").attr("name");
		if ($.string.isBlank(defKeyName)) {
			fromCurNav("#keyWord")
					.attr("name", fromCurNav("#hostSelect").val());
		} else {
			fromCurNav("#hostSelect").val(defKeyName);
		}
		//添加条件查询选择框事件
		fromCurNav("#hostSelect").change(
				function() {
					fromCurNav("#keyWord").attr("name",
							$(this).children('option:selected').val());
				});
	});
</script>