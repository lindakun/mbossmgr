<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="import.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MBOSS运营管理平台</title>
<link href="themes/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="#"><img src="themes/default/images/login_logo.gif" />
				</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#">设为首页</a>
						</li>
						<li><a href="#">反馈</a>
						</li>
						<li><a href="doc/dwz-user-guide.chm" target="_blank">帮助</a>
						</li>
					</ul>
				</div>
				<h2 class="login_title">
					<img src="themes/default/images/login_title.png" />
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form action="login" method="post">
					<p>
						<span
							style="display:block;color:red;height:5px;padding-left:80px;">${loginErr}</span>
					</p>
					<p>
						<label>用户名：</label> <input type="text" name="USERNAME" size="20"
							class="login_input" />
					</p>
					<p>
						<label>密码：</label> <input type="password" name="PASSWORD"
							size="20" class="login_input" />
					</p>
					<p>
						<label>验证码：</label> <input class="code" type="text" size="5" /> <span><img
							src="themes/default/images/header_bg.png" alt="" width="75"
							height="24" /> </span>
					</p>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner">
				<img src="themes/default/images/login_banner.jpg" />
			</div>
			<div class="login_main">
				<ul class="helpList">
					<li><a href="#">下载驱动程序</a>
					</li>
					<li><a href="#">如何安装密钥驱动程序？</a>
					</li>
					<li><a href="#">忘记密码怎么办？</a>
					</li>
					<li><a href="#">为什么登录失败？</a>
					</li>
				</ul>
				<div class="login_inner">
					<p>这只是个简单的企业内部管理系统</p>
					<p>简单的系统有着不简单的功能。</p>
					<p>这就是MBOSS运营管理平台…</p>
				</div>
			</div>
		</div>
		<div id="login_footer">Copyright &copy; 2012 Lindakun Inc. All
			Rights Reserved.</div>
	</div>
</body>
</html>