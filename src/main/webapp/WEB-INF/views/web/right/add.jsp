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
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="addform" right="form" class="form-horizontal m-t">
                    <input id="pid" name="pid" type="hidden" value="${pid }" >
						<!-- 表单 -->
                        <div class="row">
                        	<div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
                            <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">权限名称</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" maxlength="8" class="form-control" id="rightName" name="rightName" placeholder="请输入权限名称">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
                        </div>
                        <div class="row">
                        	<div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
                            <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">权限描述</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" maxlength="20" class="form-control" name="rightDesc" placeholder="请输入权限描述">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
                        </div>
                        <div class="row">
                        	<div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
                            <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">权限url</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" maxlength="32" class="form-control" name="rightUrl" placeholder="请输入权限描述">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
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
<script src="<%=path%>/script/web/right/add.js"></script>
</body>
</html>