<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<html lang="en">
<head>
	<title>登录</title>
	<jsp:include page="head.jsp"></jsp:include>
	<!-- 登录页面css -->
	<link href="<%=path %>/static/webFront/css/my_register.css" rel="stylesheet">
</head>
<body>
	<!-- 頁面上部菜單 -->
	<div class="top"></div>
	<div class="main-wraper" style="margin: 0px 0px 0px 0px;">
		<div class="main">
			<!-- 表单 -->
			<div class="form">
				<div>
					<div class="welcome">
						欢迎
					</div>
					<div class="header">
						每一天，望您有个好心情。
					</div>
				</div>
				<form autocomplete="off" id="webform">
					<!-- 用户名 -->
					<div class="input-area">
						<div class="input-flags">
							<div class="input-ok" style="display: none;">
							</div>
						</div>
						<label class="input-placeholder" id="nickname-tips" for="mName" >身份证号码/手机号码</label>
						<div class="input-outer">
							<input autocomplete="off" type="text" id="mName" name="mName" class="nickname" maxlength="24">
						</div>
						<div class="error-tips-wrap slideup">
							<div class="error-tips" title="昵称必须是以字母开头，字母、数字和下划线的组合" style="display: none;">
							</div>
						</div>
					</div>
					<!-- 密码 -->
					<div class="input-area">
						<label class="input-placeholder" id="password-tips" for="mPwd">密码</label>
						<div class="input-flags">
							<div class="input-ok" style="display: none;">
							</div>
						</div>
						<div class="input-outer">
							<div class="password-raw" style="display: none;">
							</div>
							<input autocomplete="off" type="password" id="mPwd" name="mPwd" class="password" maxlength="16">
						</div>
						<div id="pwdInfo" class="password-tips-group slideup">
							<div class="password-tips ok">
								不能包括空格
							</div>
							<div class="password-tips">
								长度为8-16个字符
							</div>
							<div class="password-tips">
								必须包含字母、数字、符号中至少2种
							</div>
						</div>
						<div id="pwdError" class="error-tips-wrap slideup">
							<div class="error-tips" style="display: none;">
							</div>
						</div>
					</div>
					<!-- 按钮 -->
					<div>
						<input type="button" id="login" class="submit" value="立即登录">
						<div class="error-tips-wrap slideup">
							<div class="error-tips" style="display: none;">
							</div>
						</div>
					</div>
					<!-- 底部导航 -->
					<div style="display: block;">
						<a href="<%=path %>/m/register" target="_blank">注册新帐号</a>   
						<span>|</span> 
						<a id="feedback_qlogin" target="_blank">意见反馈</a>  
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<%=path %>/script/web/member/login.js"></script>
</body>
</html>