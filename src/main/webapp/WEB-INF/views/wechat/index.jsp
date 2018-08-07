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
        <div class="person_top">
            <div class="person_top_header" id="header_container">
<%--                 <img class="header_thumb" src="${ sessionScope.wxMember.mWxHeadimg}" alt=""> --%>
            </div>
            <div class="person_top_info">
                <a href="javascript:;" class="opt_icon js-edit-info"><i class="bsui_m_icon-edit"></i></a>
                <h6 class="info_title" id="user-name">${sessionScope.wxMember.mName }</h6>
                <div class="info_phone" id="user-phone">${sessionScope.wxMember.mPhone }</div>
                <div class="info_desc">
                    <span id="user-cid">${sessionScope.wxMember.mCId }</span>
                    <font id="user-gg" color="#d9d9d9">&nbsp;|&nbsp;</font>
                    <span id="user-address">${sessionScope.wxMember.mAddress }</span>
                </div>
            </div>
        </div>

        <div class="weui-cells person-opt_list">

            <a class="weui-cell weui-cell_access" href="<%=path %>/wx/myTpList">
                <div class="weui-cell__hd"><i class="bsui_m_icon-home"></i></div>
                <div class="weui-cell__bd">
                    <p>我的答题</p>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
            <a class="weui-cell weui-cell_access" href="<%=path %>/wx/tpIndex/0">
                <div class="weui-cell__hd"><i class="bsui_m_icon-edit"></i></div>
                <div class="weui-cell__bd">
                    <p>开始答题</p>
                </div>
                <div class="weui-cell__ft"></div>
            </a>
        </div>

        <div id="loadingToast" style="display:none;">
            <div class="weui-mask_transparent"></div>
            <div class="weui-toast">
                <i class="weui-loading weui-icon_toast"></i>
                <p class="weui-toast__content">数据加载中...</p>
            </div>
        </div>
    </div>
</body>
<script>
$(function(){
	//右上角编辑图标的事件
	$("i[class='bsui_m_icon-edit']").click(function(){
		window.location.href=root+"/wx/person";
	});
});
</script>
</html>