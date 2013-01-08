<!-- 
	添加及编辑主机域界面
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>

<div class="pageContent">
	<form method="post" class="pageForm required-validate"
		action="host/main/updateOrSave"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="operation" id="operation"
			value="${operation}" /> <input type="hidden" name="navId" id="navId"
			value="" /> <input type="hidden" name="hostMain.MAINID"
			value="${hostMain.MAINID}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>${titles.HOSTID}：</label> <input type="hidden"
					name="hostMain.HOSTID" value="${hostMain.HOSTID}" /> <input
					type="text" class="required readonly" name="hostMain.HOSTNAME"
					value="${_HOSTNAME}" suggestFields="" suggestUrl=""
					readonly="readonly" lookupGroup="hostMain" /> <a class="btnLook"
					href="host/main/toLookup?obj=host" lookupGroup="hostMain">查找带回</a>
			</p>
			<p>
				<label>${titles.MWID}：</label> <input type="hidden"
					name="hostMain.MWID" value="${hostMain.MWID}" /> <input
					type="text" class="required readonly" name="hostMain.MWNAME"
					value="${_MWNAME}" suggestFields="" suggestUrl=""
					readonly="readonly" lookupGroup="hostMain" /> <a class="btnLook"
					href="host/main/toLookup?obj=hostmidware" lookupGroup="hostMain">查找带回</a>
			</p>
			<p>
				<label>${titles.NAME}：</label> <input name="hostMain.NAME"
					class="required" type="text" size="30" value="${hostMain.NAME}" />
			</p>
			<p>
				<label>${titles.PORT}：</label> <input name="hostMain.PORT"
					class="required" type="text" size="30" value="${hostMain.PORT}" />
			</p>
			<p>
				<label>${titles.PATH}：</label> <input name="hostMain.PATH"
					class="required" type="text" size="30" value="${hostMain.PATH}" />
			</p>
			<p>
				<label>${titles.CONSOLE}：</label> <input name="hostMain.CONSOLE"
					class="required" type="text" size="30" value="${hostMain.CONSOLE}" />
			</p>
			<p>
				<label>${titles.DBCONECTION}：</label> <input
					name="hostMain.DBCONECTION" class="required" type="text" size="30"
					value="${hostMain.DBCONECTION}" />
			</p>
			<p>
				<label>${titles.REMARK}：</label>
				<textarea name="hostMain.REMARK" class="textInput">${hostMain.REMARK}</textarea>
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
		//主机域类型下拉框设置
		//$("#addHostSelect").val($("#hostState").val());
	});
</script>