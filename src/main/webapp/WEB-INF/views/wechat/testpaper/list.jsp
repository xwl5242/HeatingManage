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
	<link rel="stylesheet" href="<%=path %>/static/weui/css/tpIndex.css">
</head>
<body ontouchstart>
	<div class="weui-panel weui-panel_access">
        <div class="weui-panel__hd">我的答题记录</div>
        <div class="weui-panel__bd">
        	<c:forEach items="${tpqList}" var="tpq" varStatus="tpqStatus"> 
 			<c:set var="tpTime" value="${fn:split(tpq.exam_time,'.')[0]}"></c:set>
	            <div class="weui-media-box weui-media-box_text" id="tp${tpqStatus}" onclick="showTpDetai('${tpTime}');">
	                <h4 class="weui-media-box__title">${tpq.tp_name}</h4>
	                <p class="weui-media-box__desc">分数:<font style="color: green;font-size: 16px;">${tpq.score}</font>,${tpTime}</p>
	            </div>
	        </c:forEach>
        </div>
    </div>
</body>
<script>
	function showTpDetai(date){
		window.location.href = root+"/wx/queryTp/"+date;
	}
</script>
</html>