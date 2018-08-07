<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body ontouchstart>
	<div class="container person" id="container">
        <div class="weui-cells__title">完善个人信息</div>
		<form id="updateM">
        <div class="weui-cells">
            <a class="weui-cell weui-cell_access" href="javascript:;">
                <div class="weui-cell__hd"><label class="weui-label">头像</label></div>
                <div class="weui-cell__bd">
                    <div class="person_info">
                        <div class="person_opt">
<%--                             <img class="header_btn" src="${sessionScope.wxMember.mWxHeadimg }"> --%>
                            <img class="header_btn" src="<%=path %>/static/Hplus/img/profile_small.jpg">
                        </div>
                    </div>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
            <input type="hidden" id="id" name="id" value="${sessionScope.wxMember.id }">
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input js-my-name" name="mName" value="${sessionScope.wxMember.mName }" type="text" placeholder="必填">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">手机</label></div>
                <input type="hidden" name="mPhone" value="${sessionScope.wxMember.mPhone }">
                <div class="weui-cell__bd js-my-phone" style="color:#999;">${sessionScope.wxMember.mPhone }</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">身份证</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input js-my-cid" name="mCId" onchange="validCid(this);" value="${sessionScope.wxMember.mCId }" type="text" placeholder="必填">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">所在城市</label></div>
                <div class="weui-cell__bd">
                    <input class="weui-input js-my-address" name="mAddress" value="${sessionScope.wxMember.mAddress }" type="text" pattern="text" placeholder="必填">
                </div>
            </div>
        </div>

        <div class="spacing">
            <a href="javascript:;" onclick="updateMember()" class="weui-btn weui-btn_primary js-btn-save">保存</a>
        </div>
        </form>
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
    </div>
</body>
<script>
function updateMember(){
	//表单验证不通过
	if(!formValidations()) return;
	//通过执行注册
	$.ajax({
    	url:root+"/wx/updateMember",
    	type:"post",
    	dataType:"json",
    	data:$('#updateM').serialize(),
    	success:function(result){
    		if(result.code){
    			window.location.href=root+"/wx/msg/wx_update/success";
    		}else{
    			window.location.href=root+"/wx/msg/wx_update/fail";
    		}
    	}
    });
}
</script>
</html>