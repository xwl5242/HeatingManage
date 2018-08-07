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
	<input id="modules" type="hidden" value="user">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="logs-form" role="form" class="form-horizontal m-t">
						<!-- 表单 -->
                        <div class="row">
                            <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">操作模块</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" class="form-control" name="optKey" placeholder="请输入操作模块">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">操作类型</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <select name="optType" class="chosen-select form-control">
                                            <option value="">全部</option>
                                            <option value="0">新增</option>
                                            <option value="1">删除</option>
                                            <option value="2">修改</option>
                                            <option value="3">查询</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-tools col-sm-4 col-xs-4 col-md-4 col-lg-4">
								<div class="btn-tools col-sm-6 col-xs-6 col-md-6 col-lg-6"></div>
								<div class="btn-tools col-sm-6 col-xs-6 col-md-6 col-lg-6">
	                                <button type="button" class="btn btn-sm btn-primary search"> 搜索</button>
	                                <button type="reset" class="btn btn-sm btn-success reset"> 重置</button>
								</div>
                            </div>
                        </div>
                    </form>
                    <div class="example">
                    	<table id="log-table-list"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path%>/script/web/log/list.js"></script>
</body>
</html>