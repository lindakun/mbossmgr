<!-- 
	添加及编辑域服务界面
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>

<div class="pageContent">
	<form method="post" class="pageForm required-validate"
		action="host/main/serv/updateOrSave"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="operation" id="operation"
			value="${operation}" /> <input type="hidden" name="navId" id="navId"
			value="" /> <input type="hidden" name="hostMainServ.SVID"
			value="${hostMainServ.SVID}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>${titles.SVNAME}：</label> <input name="hostMainServ.SVNAME"
					class="required" type="text" size="30"
					value="${hostMainServ.SVNAME}" />
			</p>
			<p>
				<label>${titles.SVPATH}：</label> <input name="hostMainServ.SVPATH"
					class="required" type="text" size="30"
					value="${hostMainServ.SVPATH}" />
			</p>
			<p>
				<label>${titles.MAINID}：</label> <input type="hidden"
					name="hostMainServ.MAINID" value="${hostMainServ.MAINID}" /> <input
					type="text" class="required readonly" name="hostMainServ.NAME"
					value="${_MAINNAME}" suggestFields="" suggestUrl=""
					readonly="readonly" lookupGroup="hostMainServ" /> <a
					class="btnLook" href="host/main/serv/toLookup?obj=hostmain"
					lookupGroup="hostMainServ">查找带回</a>
			</p>
			<p>
				<label>${titles.REMARK}：</label>
				<textarea name="hostMainServ.REMARK" class="textInput">${hostMainServ.REMARK}</textarea>
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
		//域服务类型下拉框设置
		//$("#addHostSelect").val($("#hostState").val());
	});
</script>