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
	<!-- 自定义样式覆盖（去除）radio选中样式 -->
	<style>
		:focus{
			outline:none;
		}
		.g_survey .survey_form_checkbox + label:hover, .page_stat_recycle .survey_form_checkbox + label:hover, .g_survey .survey_form_checkbox + label:active, .page_stat_recycle .survey_form_checkbox + label:active {
		    background-color: rgba(255, 255, 255, 0.95);
		}
		.g_survey .survey_form_checkbox + label .survey_form_ui, .page_stat_recycle .survey_form_checkbox + label .survey_form_ui {
		    border: 1px solid #fff;
		    width: 20px;
		    height: 20px;
		}
	</style>
</head>
<body class="g_wrapper page_survey g_survey">
	<jsp:include page="header.jsp"></jsp:include>
	<c:set var="webroot" value="${pageContext.request.contextPath}" />
	<!-- 问卷主体 -->
	<div id="container" class="g_container">
		<div class="survey_background_wrap container-background"></div>
		<div class="g_content">
			<div class="survey_wrap no_title_index" style="background-color: rgba(255, 255, 255, 0.95);">
				<div class="survey_main">
					<h1 class="survey_title" style="display: block;">
						<div class="inner">
							<div class="title_content">
								<p>${tpqMap.tp.tpTitle}</p>
							</div>
						</div>
					</h1>
					<div class="survey_content">
						<div class="survey_prefix" style="display: block;">
							<div class="inner">
								<h2 class="prefix_content">
									<!-- 备注 -->
									<p style="margin-bottom: 10px;"><strong>备注:</strong></p>
									<p><img alt="" src="<%=path %>/static/webFront/img/line.PNG"></p>
									<c:set value="${fn:split(tpqMap.tp.tpRemark,';')}" var="tpRemarks"></c:set>
									<c:forEach items="${tpRemarks }" var="tpremark">
										<p><span style="color: #FF0000;">*</span>${tpremark}</p>
									</c:forEach>
									<p><img alt="" src="<%=path %>/static/webFront/img/line.PNG"></p>
								</h2>
							</div>
						</div>
						<div class="survey_container">
							<input type="hidden" id="tpId" value="${tpqMap.tp.id }">
				        	<c:set value="${tpqMap.tpqList}" var="tpqList"></c:set>
				        	<c:forEach items="${tpqList }" var="tpq" varStatus="tpqStatus">
								<div class="survey_page" id="page${tpqStatus.index+1 }">
									<div class="question question_radio required" 
										id="question_${tpq.q_id }" data-type="radio" 
										data-id="${tpq.q_id }" role="group" data-role="radiogroup" 
										aria-required="true" style="display: block;"> 
										<div class="inner"> 
											<div class="title" id="questionTitle_${tpq.q_id }"> 
												<div role="presetation"> 
													<span class="title_index"> 
														<span class="question_index" data-for="${tpq.q_id }">${tpqStatus.index+1}</span>. 
													</span> 
													<h2 class="title_text"><strong>${tpqStatus.index+1}.${tpq.q_title }</strong></h2>  
													<span class="required" title="必答题" aria-label="必答题" style="display: none;">*</span>  
												</div> 
												<span class="tips" role="alert"></span> 
											</div> 
											<div class="description"> 
												<div class="description_text"></div> 
											</div> 
											<c:set value="${tpq.q_answer eq tpq.my_answer}" var="isTrue"></c:set>  
											<div class="inputs">   
												<c:set value="${fn:split(tpq.q_sel_item,';')}" var="selItems"></c:set>
			                					<c:forEach items="${selItems }" var="selItem" varStatus="selItemStatus">
													<c:set value="${fn:substring(selItem,0,1)}" var="Aoption"></c:set>
													<c:choose>
								                		<c:when test="${isTrue }">
								                			<c:choose>
								                				<c:when test="${(65+selItemStatus.index) eq tpq.q_answer}">
								                					<c:set value="outline: 1px solid #45e86c;margin-top: 5px;" var="outlines"></c:set>
								                					<c:set value="background-image: url('${webroot }/static/webFront/img/green.png');" var="imgs"></c:set>
								                				</c:when>
								                				<c:otherwise><c:set value="" var="outlines"></c:set><c:set value="" var="imgs"></c:set></c:otherwise>
								                			</c:choose>
								                		</c:when>
								                		<c:otherwise>
								                			<c:choose>
								                				<c:when test="${(65+selItemStatus.index) eq tpq.q_answer}">
								                					<c:set value="outline: 1px solid #45e86c;margin-top: 5px;" var="outlines"></c:set>
								                					<c:set value="background-image: url('${webroot }/static/webFront/img/green.png');" var="imgs"></c:set>
								                				</c:when>
								                				<c:when test="${(65+selItemStatus.index) eq tpq.my_answer}">
								                					<c:set value="outline: 1px solid #ed6661;margin-top: 5px;" var="outlines"></c:set>
								                					<c:set value="background-image: url('${webroot }/static/webFront/img/error.png');" var="imgs"></c:set>
								                				</c:when>
								                				<c:otherwise><c:set value="" var="outlines"></c:set><c:set value="" var="imgs"></c:set></c:otherwise>
								                			</c:choose>
								                		</c:otherwise>
								                	</c:choose>
													<div class="option_item" style="width: 100%;${outlines}" role="radio"> 
														<input class="survey_form_checkbox" type="radio" disabled="disabled" name="answer_${tpq.q_id }" 
														data-oid="${Aoption}" data-exclusive="0" id="option_${tpq.q_id }_${Aoption}"> 
														<label for="option_${tpq.q_id }_${Aoption}"> 
															<i style="${imgs}" class="survey_form_ui"></i> 
															<div class="option_text"><p>${selItem }</p></div> 
														</label> 
													</div>  
												</c:forEach> 
											</div>
										</div> 
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- 页面翻页按钮 -->
				<div class="survey_control">
					<div class="inner">
						<a href="<%=path %>/m/mytplist" title="返回" class="survey_btn survey_prevpage">返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>