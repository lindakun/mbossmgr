<!-- 
	添加及编辑主机用户界面
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>

<div class="pageContent">
	<form method="post" class="pageForm required-validate"
		action="host/user/updateOrSave"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="operation" id="operation"
			value="${operation}" /> <input type="hidden" name="navId" id="navId"
			value="" /> <input type="hidden" name="hostUser.USERID"
			value="${hostUser.USERID}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>${titles.USERNAME}：</label> <input name="hostUser.USERNAME"
					class="required" type="text" size="30" value="${hostUser.USERNAME}" />
			</p>
			<p>
				<label>${titles.PASSWORD}：</label> <input name="hostUser.PASSWORD"
					class="required" type="text" size="30" value="${hostUser.PASSWORD}" />
			</p>
			<p>
				<label>${titles.LOGINTYPE}：</label> <input name="hostUser.LOGINTYPE"
					class="required" type="text" size="30"
					value="${hostUser.LOGINTYPE}" />
			</p>
			<p>
				<label>${titles.PORT}：</label> <input name="hostUser.PORT"
					class="required" type="text" size="30" value="${hostUser.PORT}" />
			</p>
			<p>
				<label>${titles.HOSTID}：</label> <input type="hidden"
					name="hostUser.HOSTID" value="${hostUser.HOSTID}" /> <input
					type="text" class="required readonly" name="hostUser.HOSTNAME"
					value="${_HOSTNAME}" suggestFields="" suggestUrl=""
					readonly="readonly" lookupGroup="hostUser" /> <a class="btnLook"
					href="host/user/toLookup?obj=host" lookupGroup="hostUser">查找带回</a>
			</p>
			<p>
				<label>${titles.DEFAULTPATH}：</label> <input
					name="hostUser.DEFAULTPATH" class="required" type="text" size="30"
					value="${hostUser.DEFAULTPATH}" />
			</p>
			<p>
				<label>${titles.REMARK}：</label>
				<textarea name="hostUser.REMARK" class="textInput">${hostUser.REMARK}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		//主机用户类型下拉框设置
		//$("#addHostSelect").val($("#hostState").val());
	});
</script>