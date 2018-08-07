<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath().toString();
%>
<html lang="zh-cn">
<head>
	<jsp:include page="head.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=path %>/static/webFront/css/my_index.css">
	<link rel="stylesheet" href="<%=path %>/static/webFront/css/mytplist.css">
	<link rel="stylesheet" href="<%=path %>/static/webFront/css/my_exam_msg.css">
</head>
<body class="g_wrapper page_survey g_survey">
	<jsp:include page="header.jsp"></jsp:include>
	<!-- 问卷主体 -->
	<div id="container" class="g_container">
		<div class="survey_background_wrap container-background"></div>
		<div class="g_content">
			<div id="list_view_container" style="background-color: rgba(255, 255, 255, 0.95);" class="list_view_container table_type">
				<c:choose>
					<c:when test="${fn:length(tpqList)==0}">
						<div class="succ" style="height: 100%">
							<div id="examMsg" style="left:25%">
								<div class="alert-logo"></div>
								<div class="reg-succ" style="padding-top:30px;">
									您还没有参与过答题，快去答题吧！
								</div>
								<div>
									<input type="button" class="login" style="margin: 25px;"
									onclick="javascript:window.location.href = '<%=path %>/m'" value="前去答题">
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div id="survey_list" class="survey_list">
							<c:forEach items="${tpqList}" var="tpq" varStatus="tpqStatus"> 
			 				<c:set var="tpTime" value="${fn:split(tpq.exam_time,'.')[0]}"></c:set>
								<span id="${tpq.id }" class="survey_item"> 
									<div class="survey_item_inner" onclick="javascript:window.location.href = '<%=path %>/m/queryTp/${tpTime}'"> 
										<div class="status_col_title">${tpq.tp_name}</div> 
										<div class="status_col_score">分数:<font style="color: green;font-size: 16px;">${tpq.score }</font></div> 
										<div class="status_col_ct">${tpTime}</div> 
									</div> 
								</span> 
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>