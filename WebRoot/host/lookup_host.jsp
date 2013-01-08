<!-- 
	查找主机
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>
<form id="pagerForm" method="post" action="host/queryListForLookup"
	onsubmit="return dwzSearch(this, 'dialog');">
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
					<td><select id="lookuphostSelect" class="combox" name="keyName">
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
								<button type="submit">查询</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
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
					<td><a class="btnSelect"
						href="javascript:$.bringBack({HOSTID:'${host.HOSTID}', HOSTNAME:'${host.HOSTNAME}', orgNum:'1001'})"
						title="查找带回">选择</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="lookuphostPageSelect" class="combox"
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
		fromCurDlg("#lookuphostPageSelect").val(fromCurDlg("#numPerPage").val());
		//判断条件查询关键字是否为空，不为空则刷新后保留
		var defKeyName = fromCurDlg("#keyWord").attr("name");
		if ($.string.isBlank(defKeyName)) {
			fromCurDlg("#keyWord")
					.attr("name", fromCurDlg("#lookuphostSelect").val());
		} else {
			fromCurDlg("#lookuphostSelect").val(defKeyName);
		}
		//添加条件查询选择框事件
		fromCurDlg("#lookuphostSelect").change(
				function() {
					fromCurDlg("#keyWord").attr("name",
							$(this).children('option:selected').val());
				});
	});
</script>