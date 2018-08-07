<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>

<head>
    <title>用户管理</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<input id="modules" type="hidden" value="question">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="question-form" role="form" class="form-horizontal m-t">
						<!-- 表单 -->
                        <div class="row">
                            <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题标题</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" class="form-control" name="qTitle" placeholder="请输入问题标题">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题类型</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <select name="qType" class="chosen-select form-control">
                                            <option value="">全部</option>
                                            <option value="0">单选</option>
                                            <option value="1">多选</option>
                                            <option value="2">填空</option>
                                            <option value="3">问答</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8  btn-group">
                                <button type="button" class="btn btn-sm btn-info" onclick="addQuestion()"><i class="fa fa-plus"></i>添加</button>
                                <button type="button" class="btn btn-sm btn-danger" onclick="removeQuestion()"><i class="fa fa-trash-o"></i>删除</button>
                                <button type="button" class="btn btn-sm btn-success" onclick="importExcel()"><i class="fa fa-edit"></i>导入</button>
                            </div>
                            <div class="btn-tools col-sm-4 col-xs-4 col-md-4 col-lg-4">
								<div class="btn-tools col-sm-6 col-xs-6 col-md-6 col-lg-6"></div>
								<div class="btn-tools col-sm-6 col-xs-6 col-md-6 col-lg-6">
	                                <button type="button" class="btn btn-sm btn-primary search"> 搜索</button>
	                                <button type="reset" class="btn btn-sm btn-success reset"> 重置</button>
	                                <button type="button" class="btn btn-sm btn-primary export"> 导出Excel</button>
								</div>
                            </div>
                        </div>
                    </form>
                    <div class="example">
                    	<table id="question-table-list"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path%>/script/web/question/list.js"></script>
</body>
</html>