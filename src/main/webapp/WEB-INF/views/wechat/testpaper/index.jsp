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
	<!-- 正确答案个数 -->
	<input id="successAnswerNum" type="hidden">
	<!-- Swiper 容器-->
    <div class="swiper-container">
        <div class="swiper-wrapper" id="questionList">
        	<input type="hidden" id="tpId" value="${tpqMap.tp.id }">
        	<c:set value="${tpqMap.tpqList}" var="tpqList"></c:set>
        	<c:forEach items="${tpqList }" var="tpq" varStatus="tpqStatus">
        		<div class="swiper-slide">
		            <div>
		            	<div class="weui-article" style="padding-top:30px;">
			                <h3 style="font-size: 16px;"><span>${tpqStatus.index+1}</span>.${tpq.q_title}</h3>
						</div>
		                <div class="weui-cells weui-cells_checkbox my-weui-cells">
		                	<c:set value="${fn:split(tpq.q_sel_item,';')}" var="selItems"></c:set>
		                	<c:forEach items="${selItems }" var="selItem" varStatus="selItemStatus">
				            <label class="weui-cell weui-check__label" for="${tpq.id }-${selItemStatus.index}-${tpqStatus.index+1}">
				                <div class="weui-cell__hd">
				                    <input type="radio" onchange="checkChange(this)" class="weui-check" 
				                    name="singleName-${tpq.id }" id="${tpq.id }-${selItemStatus.index}-${tpqStatus.index+1}"/>
				                    <i class="weui-icon-checked"></i>
				                </div>
				                <div class="weui-cell__bd">
				                    <p id="ap${tpq.id }-${selItemStatus.index}-${tpqStatus.index+1}">${selItem}</p>
				                </div>
				            </label>
				            </c:forEach>
				        </div>
				        <input type="hidden" id="answer${tpq.id}" value="${tpq.q_answer}">
			        </div>
				</div>
        	</c:forEach>
        </div>
    </div>
    <!-- 页面下部的操作按钮和题目信息 -->
    <div class="my-footer">
    	<div class="weui-cell">
		    <div class="weui-flex__item">
		    <div class="placeholder">
		        <i class="bsui_m_icon-edit"></i>
		        <font style="margin-left:5px;font-size:10px;">交卷</font>
			</div></div>
		    <div class="weui-flex__item"><div class="placeholder">
		        <i class="weui-icon-success" style="font-size:18px;"></i>
		        <font style="margin-left:5px;font-size:10px;">0</font>
			</div></div>
		    <div class="weui-flex__item"><div class="placeholder">
		        <i class="weui-icon-cancel" style="font-size:18px;"></i>
		        <font style="margin-left:5px;font-size:10px;">0</font>
			</div></div>
		    <div class="weui-flex__item"><div class="placeholder">
		        <i class="weui-icon-info-circle" style="font-size:18px;"></i>
		        <font style="margin-left:5px;font-size:10px;"><span id="pageNo">1</span> / <span id="pageSize"></span></font>
			</div></div>
		</div>
    </div>
    <!-- "交卷"按钮触摸阴影 -->
    <div id="myShade" class="my-shade"></div>
    <!-- 交卷提示 -->
    <div id="dialogs">
	    <div class="js_dialog" id="submitMsg" style="display: none;">
	        <div class="weui-mask"></div>
	        <div class="weui-dialog weui-skin_android">
	            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
	            <div class="weui-dialog__bd">
	                                        确认交卷？提交后将不再允许修改！
	            </div>
	            <div class="weui-dialog__ft">
	                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
	                <a id="successBtn" class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
	            </div>
	        </div>
	    </div>
    </div>
    <script src="<%=path %>/script/wechat/testpaper/index.js"></script>
</body>
</html>