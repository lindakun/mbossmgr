<!-- 
	查找中间件
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>
<form id="pagerForm" method="post" action="host/midware/queryListForLookup"
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
					<td><select id="hostMidwareSelect" class="combox"
						name="keyName">
							<option value="MWID" selected="selected">中间件ID</option>
							<option value="NAME">中间件名称</option>
							<option value="VERSION">版本</option>
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
				<th width="60">${titles.MWID}</th>
				<th width="120">${titles.NAME}</th>
				<th width="120">${titles.VERSION}</th>
				<th width="60">${titles.OPERATOR}</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${hostMidWares}" varStatus="i" var="hostMidware">
				<tr target="hostMidware_id" rel="${hostMidware.MWID}">
					<td>${hostMidware.MWID}</td>
					<td>${hostMidware.NAME}</td>
					<td>${hostMidware.VERSION}</td>
					<td>${hostMidware.OPERATOR}</td>
					<td><a class="btnSelect"
						href="javascript:$.bringBack({MWID:'${hostMidware.MWID}', MWNAME:'${hostMidware.NAME}', orgNum:'1001'})"
						title="查找带回">选择</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="lookuphostMidWarePageSelect"
				class="combox" name="numPerPage"
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
		fromCurDlg("#lookuphostMidWarePageSelect").val(
				fromCurDlg("#numPerPage").val());
		//判断条件查询关键字是否为空，不为空则刷新后保留
		var defKeyName = fromCurDlg("#keyWord").attr("name");
		if ($.string.isBlank(defKeyName)) {
			fromCurDlg("#keyWord").attr("name",
					fromCurDlg("#lookuphostMideWareSelect").val());
		} else {
			fromCurDlg("#lookuphostMideWareSelect").val(defKeyName);
		}
		//添加条件查询选择框事件
		fromCurDlg("#lookuphostMideWareSelect").change(
				function() {
					fromCurDlg("#keyWord").attr("name",
							$(this).children('option:selected').val());
				});
	});
</script>