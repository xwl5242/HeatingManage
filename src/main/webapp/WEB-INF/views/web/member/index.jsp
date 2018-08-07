<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath().toString();
%>
<html lang="zh-cn">
<head>
	<jsp:include page="head.jsp"></jsp:include>
	<title>首页</title>
	<!-- 页面整体的css -->
	<link rel="stylesheet" href="<%=path %>/static/webFront/css/my_index.css">
	<!-- 考试结果显示层的css -->
	<link rel="stylesheet" href="<%=path %>/static/webFront/css/my_exam_msg.css">
	<!-- 提示用户登录后再操作的弹出层的css -->
	<link rel="stylesheet" href="<%=path %>/static/webFront/css/my_layer.css">
</head>
<body class="g_wrapper page_survey g_survey">
	<jsp:include page="header.jsp"></jsp:include>
	<!-- main -->
	<div id="container" class="g_container">
		<div class="survey_background_wrap container-background"></div>
		<!-- 试卷整体开始 -->
		<div class="g_content">
			<div class="survey_wrap no_title_index" style="background-color: rgba(255, 255, 255, 0.95);">
				<div class="survey_main">
					<input type="hidden" id="totalPage" value="${fn:length(tpqMap.tpqList)}"><!-- 试卷总页数 -->
					<!-- 试卷标题开始 -->
					<h1 class="survey_title" style="display: block;">
						<div class="inner">
							<div class="title_content">
								<p>${tpqMap.tp.tpTitle}</p>
							</div>
						</div>
					</h1>
					<!-- 试卷标题结束 -->
					<!-- 试卷主体开始 -->
					<div class="survey_content">
						<!-- 试卷备注开始 -->
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
						<!-- 试卷备注结束 -->
						<!-- 页面题目开始 -->
						<div class="survey_container">
							<input type="hidden" id="tpId" value="${tpqMap.tp.id }">
				        	<c:set value="${tpqMap.tpqList}" var="tpqList"></c:set>
				        	<c:forEach items="${tpqList }" var="tpq" varStatus="tpqStatus">
				        		<c:choose>
				        			<c:when test="${tpqStatus.index eq 0}">
				        				<c:set value="" var="pageDisplay"></c:set>
				        			</c:when>
				        			<c:otherwise>
				        				<c:set value="display:none;" var="pageDisplay"></c:set>
				        			</c:otherwise>
				        		</c:choose>
				        		<div class="survey_page" id="page${tpqStatus.index+1 }" style="${pageDisplay}">
									<div class="question question_radio required" 
										id="question_${tpq.id }" data-type="radio" 
										data-id="${tpq.id }" role="group" data-role="radiogroup" 
										aria-required="true" style="display: block;"> 
										<div class="inner"> 
											<div class="title" id="questionTitle_${tpq.id }"> 
												<div role="presetation"> 
													<span class="title_index"> 
														<span class="question_index" data-for="${tpq.id }">${tpqStatus.index+1}</span>. 
													</span> 
													<h2 class="title_text"><strong>${tpqStatus.index+1}.${tpq.q_title }</strong></h2>  
													<span class="required" title="必答题" aria-label="必答题" style="display: none;">*</span>  
												</div> 
												<span class="tips" role="alert"></span> 
											</div> 
											<input type="hidden" id="trueAnswer${tpq.id}" value="${tpq.q_Answer}">
											<div class="description"> 
												<div class="description_text"></div> 
											</div>   
											<div class="inputs">   
												<c:set value="${fn:split(tpq.q_sel_item,';')}" var="selItems"></c:set>
			                					<c:forEach items="${selItems }" var="selItem" varStatus="selItemStatus">
													<c:set value="${fn:substring(selItem,0,1)}" var="Aoption"></c:set>
													<div class="option_item" style="width: 100%;" role="radio" tabindex="-1" aria-checked="false"> 
														<input class="survey_form_checkbox" type="radio" name="answer_${tpq.id }" 
														data-oid="${Aoption}" data-exclusive="0" id="option_${tpq.id }_${Aoption}"> 
														<label for="option_${tpq.id }_${Aoption}"> 
															<i class="survey_form_ui"></i> 
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
						<!-- 页面题目结束 -->
					</div>
					<!-- 试卷主体结束 -->
				</div>
				<!-- 考试结果提示语开始 -->
				<div class="succ" style="display: none;height: 100%">
					<div id="examMsg">
						<div class="succ-logo"></div>
						<div class="reg-succ">
							注册成功
						</div>
						<div>
							<div class="download-qq-outer" data-bind="visible: isLianghao()">
								<div class="qq"></div>
							</div>
							<input type="button" class="login" onclick="javascript:window.location.href = '<%=path %>/m'" value="返回首页">
							<input type="button" class="login" onclick="javascript:window.location.href = '<%=path %>/m/mytplist'" value="我的答题">
						</div>
					</div>
				</div>
				<!-- 考试结果提示语结束 -->
				<!-- 页面翻页按钮开始 -->
				<div class="survey_control">
					<div class="inner">
						<a href="javascript:;" title="上一页" class="survey_btn survey_prevpage" style="display: none;">上一页</a>
						<a href="javascript:;" title="提交" class="survey_btn survey_submit" style="display: none;">提交</a> 
						<a href="javascript:;" title="下一页" class="survey_btn survey_nextpage"style="display: inline-block;">下一页</a>
					</div>
				</div>
				<!-- 页面翻页按钮结束 -->
			</div>
		</div>
		<!-- 试卷整体结束 -->
	</div>
	<!-- 提示用户登录后再操作的弹出层开始 -->
	<div id="light" class="white_content">
		<div class="going-login-loading">
			<img src="<%=path %>/static/webFront/img/loading-0.gif"> 正在跳转到登录页面，请稍等...
		</div>
	</div>
	<div id="fade" class="black_overlay"></div>
	<!-- 提示用户登录后再操作的弹出层结束 -->
</body>
<script src="<%=path %>/script/web/member/index.js"></script>
</html>