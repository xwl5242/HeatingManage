<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<html lang="en">
<head>
	<title>注册</title>
	<jsp:include page="head.jsp"></jsp:include>
	<!-- 注册页面css -->
	<link href="<%=path %>/static/webFront/css/my_register.css" rel="stylesheet">
</head>
<body>
	<!-- 頁面上部菜單 -->
	<div class="top"></div>
	<div class="main-wraper" style="margin: 0px 0px 0px 0px;">
		<div class="main">
			<div class="form">
				<div>
					<div class="welcome">
						欢迎注册
					</div>
					<div class="header">
						每一天，望您有个好心情。
					</div>
				</div>
				<form autocomplete="off">
					<div class="input-area">
						<div class="input-flags">
							<div class="input-ok" style="display: none;">
							</div>
						</div>
						<label class="input-placeholder" id="nickname-tips" for="mName" >昵称</label>
						<div class="input-outer">
							<input autocomplete="off" type="text" id="mName" name="mName" class="nickname" maxlength="24">
						</div>
						<div class="error-tips-wrap slideup">
							<div class="error-tips" title="昵称必须是以字母开头，字母、数字和下划线的组合" style="display: none;">
							</div>
						</div>
					</div>
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
						<div class="error-tips-wrap slideup">
							<div class="error-tips" style="display: none;">
							</div>
						</div>
					</div>
					<div class="input-area">
						<div class="input-flags">
							<div class="input-ok" style="display: none;">
							</div>
						</div>
						<label class="input-placeholder" id="nickname-tips" for="mCId" >身份证</label>
						<div class="input-outer">
							<input autocomplete="off" onchange="hasMember(this,'该身份证号已被注册,请登录!')" type="text" id="mCId" name="mCId" class="nickname" maxlength="18">
						</div>
						<div class="error-tips-wrap slideup">
							<div class="error-tips" title="身份证号码不正确" style="display: none;">
							</div>
						</div>
					</div>
					<div class="input-area">
						<div class="input-flags">
							<div class="input-ok" style="display: none;">
							</div>
						</div>
						<label class="input-placeholder" id="nickname-tips" for="mPhone" >手机</label>
						<div class="input-outer">
							<input autocomplete="off" onchange="hasMember(this,'该手机号已被注册,请登录!')" type="text" id="mPhone" name="mPhone" class="nickname" maxlength="12">
						</div>
						<div class="error-tips-wrap slideup">
							<div class="error-tips" title="手机号码不正确" style="display: none;">
							</div>
						</div>
					</div>
					<div class="input-area">
						<div class="input-flags">
							<div class="input-ok" style="display: none;">
							</div>
						</div>
						<label class="input-placeholder" id="nickname-tips" for="mAddress" >地址</label>
						<div class="input-outer">
							<input autocomplete="off" type="text" id="mAddress" name="mAddress" class="nickname" maxlength="24">
						</div>
						<div class="error-tips-wrap slideup">
							<div class="error-tips" title="" style="display: none;">
							</div>
						</div>
					</div>
					<div>
						<input id="goRegister" type="button" onclick="webRegister()" class="submit" value="立即注册">
						<div class="error-tips-wrap slideup">
							<div class="error-tips" style="display: none;">
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="succ" style="display: none;">
				<div>
					<div class="succ-logo">
					</div>
					<div class="reg-succ">
						注册成功
					</div>
					<div>
						<div class="download-qq-outer">
							<div>
								<img src="<%=path %>/static/webFront/img/ssdjzQr.jpg">
							</div>
							<div class="qq">
								扫描关注公众号，享受掌上答题
							</div>
						</div>
						<button class="login" onclick="javascript:window.location.href = '<%=path %>/m/login'">立即登录</button>
					</div>
				</div>
			</div>
			<div class="error" style="display: none;">
				<div class="error-logo">
				</div>
				<div class="error-msg">
					很抱歉，服务器繁忙，请稍后重试。
				</div>
				<div class="error-link">
					<a onclick="gotoRegister()">返回</a>|<a>意见反馈</a>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=path %>/script/web/member/register.js"></script>
</body>
</html>