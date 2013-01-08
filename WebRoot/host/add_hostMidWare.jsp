<!-- 
	添加及编辑中间件界面
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>

<div class="pageContent">
	<form method="post" class="pageForm required-validate"
		action="host/midware/updateOrSave"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="operation" id="operation"
			value="${operation}" /> <input type="hidden" name="navId" id="navId"
			value="" /> <input type="hidden" name="hostMidWare.MWID"
			value="${hostMidWare.MWID}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>${titles.NAME}：</label> <input name="hostMidWare.NAME"
					class="required" type="text" size="30" value="${hostMidWare.NAME}" />
			</p>
			<p>
				<label>${titles.VERSION}：</label> <input name="hostMidWare.VERSION"
					class="required" type="text" size="30" value="${hostMidWare.VERSION}" />
			</p>
			<p>
				<label>${titles.REMARK}：</label>
				<textarea name="hostMidWare.REMARK" class="textInput">${hostMidWare.REMARK}</textarea>
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
		//中间件类型下拉框设置
		//$("#addHostSelect").val($("#hostState").val());
	});
</script>