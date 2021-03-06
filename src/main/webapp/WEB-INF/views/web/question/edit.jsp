<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <title>新增用户</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
    <style>
    	textarea{
    		resize:none;
    	}
    	textarea.max{
    		height: 120px !important;
    	}
    	textarea.min{
    		height: 80px !important;
    	}
    	textarea.small{
    		height: 40px !important;
    	}
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="addform" role="form" class="form-horizontal m-t">
                    	<input id="id" name="id" type="hidden" value="${questionId }">
                    	<input id="creator" name="creator" type="hidden" value="${question.creator }">
                    	<input id="createTime" name="createTime" type="hidden" value="${question.createTime }">
						<!-- 表单 -->
                        <div class="row">
                        	<div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题类型</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <select onchange="selectQType(this);" id="qType" name="qType" value="${question.qType }" class="chosen-select form-control">
                                            <option value="">全部</option>
                                            <option value="0">单选</option>
                                            <option value="1">多选</option>
                                            <option value="2">填空</option>
                                            <option value="3">问答</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题标题</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <textarea class="form-control min" id="qTitle" name="qTitle" placeholder="请输入问题标题"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <div class="row" id="sel_up">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题分数</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" value="${question.qScore }" class="form-control" name="qScore" placeholder="请输入问题分数">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <input id="qSelItem" name="qSelItem" value="${question.qSelItem }" type="hidden">
                        <div id="autoAnswerMax" class="row" style="display: none;">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题答案</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <textarea class="form-control max" name="qAnswer" placeholder="请输入问题答案"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <div id="autoAnswerSmall" class="row" style="display: none;">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题答案</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <textarea class="form-control small" name="qAnswer" placeholder="请输入问题答案"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <div id="autoAwKeyword" class="row" style="display: none;">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题答案关键字</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <textarea class="form-control min" name="qAwKeyword" placeholder="请输入问题答案关键字"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
						<div class="row">
                            <div class="col-sm-9 col-xs-9 col-md-9 col-lg-9  btn-group"></div>
                            <div class="btn-tools col-sm-3 col-xs-3 col-md-3 col-lg-3">
                                <button type="submit" class="btn btn-sm btn-success"> 保存</button>
                                <button type="button" class="btn btn-sm btn-default layer-cancel"> 取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path%>/script/web/question/edit.js"></script>
<script type="text/javascript">
	oldQuestionTitle = '${question.qTitle}';
	//初始化页面元素
	$("#qType").find("option[value='${question.qType}']").attr("selected",true);
	$("#qTitle").text('${question.qTitle}');
	var qType = $("#qType").val();
	if(qType<=1){
		//选择题
		$("#autoAnswerSmall").show();
		$("#autoAnswerSmall textarea").text('${question.qAnswer}');
		//初始化选项
		var qSelItem = '${question.qSelItem}';
		var qSelItemNum = qSelItem.split(";");
		//添加选项
		var qSelItemHtml = "";
		for(var i=0;i<qSelItemNum.length;i++){
			//绘制元素标签并赋予初始值
			qSelItemHtml+=renderSelDivHtml(qSelItemNum[i].split(".")[1]);
		}
		$("#sel_up").after(qSelItemHtml);
	}else if(qType==2){
		//填空题
		$("#autoAnswerSmall").show();
		$("#autoAnswerSmall textarea").text('${question.qAnswer}');
	}else if(qType==3){
		//问答题
		$("#autoAnswerMax").show();
		$("#autoAnswerMax textarea").text('${question.qAnswer}');
		$("#autoAwKeyword").show();
		$("#autoAwKeyword textarea").text('${question.qAwKeyword}');
	}
</script>
</body>
</html>