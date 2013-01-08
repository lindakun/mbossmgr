<!-- 
	添加及编辑数据库界面
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>

<div class="pageContent">
	<form method="post" class="pageForm required-validate"
		action="host/db/updateOrSave"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="operation" id="operation"
			value="${operation}" /> <input type="hidden" name="navId" id="navId"
			value="" /> <input type="hidden" name="hostDB.DBID"
			value="${hostDB.DBID}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>${titles.USERNAME}：</label> <input name="hostDB.USERNAME"
					class="required" type="text" size="30" value="${hostDB.USERNAME}" />
			</p>
			<p>
				<label>${titles.PASSWORD}：</label> <input name="hostDB.PASSWORD"
					class="required" type="text" size="30" value="${hostDB.PASSWORD}" />
			</p>
			<p>
				<label>${titles.TYPENAME}：</label> <input name="hostDB.TYPENAME"
					class="required" type="text" size="30" value="${hostDB.TYPENAME}" />
			</p>
			<p>
				<label>${titles.PORT}：</label> <input name="hostDB.PORT"
					class="required" type="text" size="30" value="${hostDB.PORT}" />
			</p>
			<p>
				<label>${titles.HOSTID}：</label>
				<input type="hidden" name="hostDB.HOSTID" value="${hostDB.HOSTID}" />
				<input type="text" class="required readonly" name="hostDB.HOSTNAME"
					value="${_HOSTNAME}" suggestFields="" suggestUrl=""
					readonly="readonly" lookupGroup="hostDB" /> <a class="btnLook"
					href="host/db/toLookup?obj=host" lookupGroup="hostDB">查找带回</a>
			</p>
			<p>
				<label>${titles.INSTANCENAME}：</label> <input
					name="hostDB.INSTANCENAME" class="required" type="text" size="30"
					value="${hostDB.INSTANCENAME}" />
			</p>
			<p>
				<label>${titles.DBVERSION}：</label> <input name="hostDB.DBVERSION"
					class="required" type="text" size="30" value="${hostDB.DBVERSION}" />
			</p>
			<p>
				<label>${titles.REMARK}：</label>
				<textarea name="hostDB.REMARK" class="textInput">${hostDB.REMARK}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		//数据库类型下拉框设置
		//$("#addHostSelect").val($("#hostState").val());
	});
</script>