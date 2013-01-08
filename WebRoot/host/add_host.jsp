<!-- 
	添加及编辑主机界面
 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../import.jsp"%>

<div class="pageContent">
	<form method="post" class="pageForm required-validate"
		action="host/updateOrSave"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="operation" id="operation"
			value="${operation}" /> <input type="hidden" name="navId" id="navId"
			value="" /> <input type="hidden" name="host.HOSTID"
			value="${host.HOSTID}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>主机名称：</label> <input name="host.HOSTNAME" class="required"
					type="text" size="30" value="${host.HOSTNAME}" />
			</p>
			<p>
				<label>主机IP：</label> <input name="host.HOSTIP" class="required"
					type="text" size="30" value="${host.HOSTIP}" />
			</p>
			<p>
				<label>主机状态：</label> <select id="addHostSelect" name="host.STATE"
					class="required combox">
					<option value="">请选择</option>
					<option value="0">停用</option>
					<option value="1">启用</option>
					<option value="-1">已移除</option>
				</select> <input type="hidden" id="hostState" value="${host.STATE}" />
			</p>
			<p>
				<label>主机型号：</label> <input name="host.HOSTTYPE" class="required"
					type="text" size="30" value="${host.HOSTTYPE}" />
			</p>
			<p>
				<label>CPU信息：</label> <input name="host.HOSTCPU" class="required"
					type="text" size="30" value="${host.HOSTCPU}" />
			</p>
			<p>
				<label>内存信息：</label> <input name="host.HOSTMEM" class="required"
					type="text" size="30" value="${host.HOSTMEM}" />
			</p>
			<p>
				<label>操作系统：</label> <input name="host.HOSTSYSTEM" class="required"
					type="text" size="30" value="${host.HOSTSYSTEM}" />
			</p>
			<p>
				<label>JDK信息：</label> <input name="host.HOSTJDK" class="required"
					type="text" size="30" value="${host.HOSTJDK}" />
			</p>
			<p>
				<label>备注信息：</label>
				<textarea name="host.REMARK" class="textInput">${host.REMARK}</textarea>
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
		//主机类型下拉框设置
		$("#addHostSelect").val($("#hostState").val());
	});
</script>