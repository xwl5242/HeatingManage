<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>

<head>
    <title>权限管理</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
    <style>
    .jstree-open > .jstree-anchor > .fa-folder:before {
        content: "\f07c";
    }

    .jstree-default .jstree-icon.none {
        width: 0;
    }
</style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
<input id="modules" type="hidden" value="right">
    <div class="row">
    
    	<div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>组织机构树</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div id="orgTreeH" class="ibox-content">
                	<div class="row">
                        <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
							 	<input type="text" class="form-control" id="org-search-val" placeholder="请输入权限名称">
						</div>
						<div class="col-sm-2 col-xs-2 col-md-2 col-lg-2">
                             	<button type="button" class="btn btn-sm btn-primary otree-search"> 搜索</button>
                        </div>
                    </div>
                    <br>
                    <div id="org-tree" class="otree"></div>
                </div>
            </div>
        </div>
		<!-- 右侧 -->
        <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8">
            <div class="ibox float-e-margins">
                <div id="tables" class="ibox-content">
                    <div class="example">
                    	<input id="otreeSelectedId" type="hidden" value="0">
                    	<table id="right-table-list"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path%>/script/web/right/list.js"></script>
</body>
</html>