<!-- 
	添加及编辑域工具界面
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>

<div class="pageContent">
	<form method="post" class="pageForm required-validate"
		action="host/main/tool/updateOrSave"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="operation" id="operation"
			value="${operation}" /> <input type="hidden" name="navId" id="navId"
			value="" /> <input type="hidden" name="hostMainTool.TOOLID"
			value="${hostMainTool.TOOLID}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>${titles.MAINID}：</label> <input type="hidden"
					name="hostMainTool.MAINID" value="${hostMainTool.MAINID}" /> <input
					type="text" class="required readonly" name="hostMainTool.NAME"
					value="${_MAINNAME}" suggestFields="" suggestUrl=""
					readonly="readonly" lookupGroup="hostMainTool" /> <a
					class="btnLook" href="host/main/serv/toLookup?obj=hostmain"
					lookupGroup="hostMainTool">查找带回</a>
			</p>
			<p>
				<label>${titles.TOOLNAME}：</label> <input name="hostMainTool.TOOLNAME"
					class="required" type="text" size="30"
					value="${hostMainTool.TOOLNAME}" />
			</p>
			<p>
				<label>${titles.VERSION}：</label> <input name="hostMainTool.VERSION"
					class="required" type="text" size="30"
					value="${hostMainTool.VERSION}" />
			</p>
			<p>
				<label>${titles.PATH}：</label> <input name="hostMainTool.PATH"
					class="required" type="text" size="30"
					value="${hostMainTool.PATH}" />
			</p>			
			<p>
				<label>${titles.REMARK}：</label>
				<textarea name="hostMainTool.REMARK" class="textInput">${hostMainTool.REMARK}</textarea>
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
		//域工具类型下拉框设置
		//$("#addHostSelect").val($("#hostState").val());
	});
</script>