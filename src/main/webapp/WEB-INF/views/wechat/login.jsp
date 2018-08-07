<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<jsp:include page="head.jsp"></jsp:include>
	<script src="<%=path%>/script/IdentityCodeValid.js"></script>
</head>
<body ontouchstart>
	<div class="weui-cells weui-cells_form">
		<form id="loginForm">
		<!-- 登录名称 -->
		<div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">账号</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="mName" type="text" placeholder="请输入身份证号/手机号"/>
            </div>
        </div>
        <!-- 密码 -->
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">密码</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="mPwd" type="password" placeholder="请输入密码"/>
            </div>
        </div>
		</form>
    </div>
    <div class="weui-cells__tips"></div>
    <div class="weui-btn-area">
        <a id="loginBtn" class="weui-btn weui-btn_primary">登录</a>
    </div>
    
    <div class="weui-footer weui-footer_fixed-bottom">
        <p class="weui-footer__links">
            <a href="<%=path %>/wx/register" class="weui-footer__link">还没有账号？去注册</a>
        </p>
<!--         <p class="weui-footer__text">Copyright &copy; 2008-2016 weui.io</p> -->
    </div>
    
    <div id="dialogs">
	    <div class="js_dialog" id="requiredMsg" style="display: none;">
	        <div class="weui-mask"></div>
	        <div class="weui-dialog weui-skin_android">
	            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
	            <div class="weui-dialog__bd" id="realMsg">
	            </div>
	            <div class="weui-dialog__ft">
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
	            </div>
	        </div>
	    </div>
    </div>
</body>
<script src="<%=path%>/script/wechat/login.js"></script>
</html>