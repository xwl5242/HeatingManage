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
		<form id="registerForm">
		<!-- 姓名 -->
		<div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="mName" type="text" placeholder="请输入中文姓名"/>
            </div>
        </div>
        <!-- 手机 -->
        <div class="weui-cell">
            <div class="weui-cell__hd">
                <label class="weui-label">手机</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="mPhone" onchange="validPhone(this);" type="tel" placeholder="请输入手机号"/>
            </div>
<!--             <div class="weui-cell__ft"> -->
<!--                 <button class="weui-vcode-btn" style="width:115px;">获取验证码</button> -->
<!--             </div> -->
        </div>
        <!-- 身份证 -->
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">身份证</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="mCId" onchange="validCid(this);" type="number" pattern="[0-9]*" placeholder="请输入身份证号"/>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">密码</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" name="mPwd" type="password" placeholder="请输入密码"/>
            </div>
        </div>
        <!-- 所在城市 -->
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">所在城市</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" name="mAddress" type="text" placeholder="请输入所在城市"/>
            </div>
        </div>
        <!-- 验证码 -->
<!--         <div class="weui-cell"> -->
<!--             <div class="weui-cell__hd"><label class="weui-label">验证码</label></div> -->
<!--             <div class="weui-cell__bd"> -->
<!--                 <input class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入验证码"/> -->
<!--             </div> -->
<!--         </div> -->
		</form>
    </div>
    <div class="weui-cells__tips"></div>
    <div class="weui-btn-area">
        <a id="registerBtn" class="weui-btn weui-btn_primary">注册</a>
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
<!-- 	                <a id="successBtn" href="" class="weui-dialog__btn weui-dialog__btn_primary">确认</a> -->
	            </div>
	        </div>
	    </div>
    </div>
</body>
<script src="<%=path%>/script/wechat/register.js"></script>
</html>