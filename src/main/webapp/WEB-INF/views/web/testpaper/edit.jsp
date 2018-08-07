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
                    	<input id="id" name="id" type="hidden" value="${tp.id }">
                    	<input id="creator" name="creator" type="hidden" value="${tp.creator }">
                    	<input id="createTime" name="createTime" type="hidden" value="${tp.createTime }">
						<!-- 表单 -->
                        <div class="row" id="sel_up">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">试卷名称</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" class="form-control" value="${tp.tpName }" name="tpName" placeholder="请输入试卷名称">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">试卷标题</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <textarea class="form-control max" id="tpTitle" name="tpTitle" placeholder="请输入试卷标题"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <div class="row" id="sel_up">
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">试卷备注</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" value="${tp.tpRemark }" class="form-control" name="tpRemark" placeholder="请输入试卷备注">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                        </div>
                        <div class="row">
                        	<div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">发布状态</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <select id="isPublish" name="isPublish" value="${tp.isPublish }" class="chosen-select form-control">
                                            <option value="">全部</option>
                                            <option value="0">不发布</option>
                                            <option value="1">发布</option>
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
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">是否刷新题目</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <select name="isRefreshQuestion" class="chosen-select form-control">
                                            <option value=""></option>
                                            <option value="0">否</option>
                                            <option value="1">是</option>
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
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">是否随机出题</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <select id="tpIsrandom" name="tpIsrandom" value="${tp.tpIsrandom }" class="chosen-select form-control">
                                            <option value=""></option>
                                            <option value="0">否</option>
                                            <option value="1">是</option>
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
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">随机出题个数</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" class="form-control" readonly="readonly" name="tpRandomNumber" value="${tp.tpRandomNumber }" placeholder="请输入出题个数">
                                        <input type="hidden" id="maxValue" value="${quesCount }">
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
<script src="<%=path%>/script/web/testpaper/edit.js"></script>
<script type="text/javascript">
var oldTpName = '${tp.tpName}';
$("#isPublish").find("option[value='${tp.isPublish}']").attr("selected",true);
$("#tpIsrandom").find("option[value='${tp.tpIsrandom}']").attr("selected",true);
$("#tpTitle").text('${tp.tpTitle}');
</script>
</body>
</html>