<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<jsp:include page="../head.jsp"></jsp:include>
</head>
<body ontouchstart>
<div class="page">
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">${hashMap.title }</h2>
            <p class="weui-msg__desc">${hashMap.desc }</a></p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a href="<%=path %>/wx/toIndex" class="weui-btn weui-btn_primary">返回首页</a>
            </p>
        </div>
<!--         <div class="weui-msg__extra-area"> -->
<!--             <div class="weui-footer"> -->
<!--                 <p class="weui-footer__links"> -->
<!--                     <a href="javascript:void(0);" class="weui-footer__link">底部链接文本</a> -->
<!--                 </p> -->
<!--                 <p class="weui-footer__text">Copyright &copy; 2008-2016 weui.io</p> -->
<!--             </div> -->
<!--         </div> -->
    </div>
</div>
</body>
</html>