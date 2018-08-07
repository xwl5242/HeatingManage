<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!-- 页面header开始 -->
<div id="header" class="g_header" style="display:block;">
	<c:set var="webroot" value="${pageContext.request.contextPath}" />
    <div class="wrapper">
        <div class="nav">
            <ul>
            	<li><a style="color:#333;" href="<%=path%>/m" class="require_login">首页</a></li>
                <li><a style="color:#333;cursor: pointer;" id="gotomytp" class="require_login">我的答题 </a></li>
                <li><a style="color:#333;cursor: pointer;">帮助中心</a></li>
            </ul>
        </div>
        <div class="account" style="padding-top:10px;">
            <div id="login_checked" class="logged_in">
            	<c:set value="${sessionScope.member.mWxHeadimg}" var="wxHeadimg"></c:set>
            	<c:choose>
            		<c:when test="${empty wxHeadimg or 'null'==wxHeadimg}">
            			<c:set value="${webroot }/static/Hplus/img/profile_small.jpg" var="wxHeadimg"></c:set>
            		</c:when>
            		<c:otherwise>
            			<c:set value="${sessionScope.member.mWxHeadimg}" var="wxHeadimg"></c:set>
            		</c:otherwise>
            	</c:choose>
            	<img class="avatar" src="${wxHeadimg}">
                <span class="name">${sessionScope.memberName }</span>
                <span class="splitor">|</span>
                <a href="<%=path %>/m/loginOut" id="logout" class="logout">退出</a>
            </div>
            <div id="login_checking" class="not_logged_in" style="display: block;">
                <a id="web-login" href="<%=path %>/m/login" target="_top" class="btn btn_blue btn_small">登录</a>
            </div>
        </div>
    </div>
</div>
<!-- 页面header结束 -->