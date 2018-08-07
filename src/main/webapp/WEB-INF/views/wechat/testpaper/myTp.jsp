<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<!-- 正确答案个数 -->
	<input id="successAnswerNum" type="hidden">
	<!-- Swiper 容器-->
    <div class="swiper-container">
        <div class="swiper-wrapper" id="questionList">
        	<c:set value="${tpqMap.tpqList}" var="tpqList"></c:set>
        	<c:forEach items="${tpqList }" var="tpq" varStatus="tpqStatus">
        		<div class="swiper-slide">
		            <div>
		            	<div class="weui-article" style="padding-top:30px;">
			                <h3 style="font-size: 16px;"><span>${tpqStatus.index+1}</span>.${tpq.q_title}</h3>
						</div>
		                <div class="weui-cells weui-cells_checkbox my-weui-cells">
		                	<c:set value="${fn:split(tpq.q_sel_item,';')}" var="selItems"></c:set>
		                	<c:set value="${tpq.q_answer eq tpq.my_answer}" var="isTrue"></c:set>
		                	<!-- 回答正确： -->
		                	<c:forEach items="${selItems }" var="selItem" varStatus="selItemStatus">
					            <label class="weui-cell weui-check__label" for="${tpq.q_id }-${selItemStatus.index}">
					                <div class="weui-cell__hd">
					                    <input type="radio" class="weui-check" name="singleName-${tpq.q_id }" id="${tpq.q_id }-${selItemStatus.index}"/>
					                	<c:choose>
					                		<c:when test="${isTrue }">
					                			<c:choose>
					                				<c:when test="${(65+selItemStatus.index) eq tpq.q_answer}"><i class='weui-icon-success-no-circle'></i></c:when>
					                				<c:otherwise><i class='weui-icon-circle'></i></c:otherwise>
					                			</c:choose>
					                		</c:when>
					                		<c:otherwise>
					                			<c:choose>
					                				<c:when test="${(65+selItemStatus.index) eq tpq.q_answer}"><i class='weui-icon-success-no-circle'></i></c:when>
					                				<c:when test="${(65+selItemStatus.index) eq tpq.my_answer}"><i id="faili" class='weui-icon-cancel'></i></c:when>
					                				<c:otherwise><i class='weui-icon-circle'></i></c:otherwise>
					                			</c:choose>
					                		</c:otherwise>
					                	</c:choose>
					                </div>
					                <div class="weui-cell__bd">
					                    <p id="ap${tpq.q_id }-${selItemStatus.index}">${selItem}</p>
					                </div>
					            </label>
				            </c:forEach>
				        </div>
			        </div>
				</div>
        	</c:forEach>
        </div>
    </div>
    <!-- 页面下部的操作按钮和题目信息 -->
    <div class="my-footer">
    	<div class="weui-cell">
<!-- 		    <div class="weui-flex__item"> -->
<!-- 		    <div class="placeholder"> -->
<!-- 		        <i class="bsui_m_icon-edit"></i> -->
<!-- 		        <font style="margin-left:5px;font-size:10px;">交卷</font> -->
<!-- 			</div></div> -->
		    <div class="weui-flex__item"><div class="placeholder">
		        <i class="weui-icon-success" style="font-size:18px;"></i>
		        <font id="successNum" style="margin-left:5px;font-size:10px;">0</font>
			</div></div>
		    <div class="weui-flex__item"><div class="placeholder">
		        <i class="weui-icon-cancel" style="font-size:18px;"></i>
		        <font id="failNum" style="margin-left:5px;font-size:10px;">0</font>
			</div></div>
		    <div class="weui-flex__item"><div class="placeholder">
		        <i class="weui-icon-info-circle" style="font-size:18px;"></i>
		        <font style="margin-left:5px;font-size:10px;"><span id="pageNo">1</span> / <span id="pageSize"></span></font>
			</div></div>
		</div>
    </div>
    <script src="<%=path %>/script/wechat/testpaper/myTp.js"></script>
</body>
</html>