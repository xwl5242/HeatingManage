<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>

<head>
    <title>用户授权</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
<input id="modules" type="hidden" value="role">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                        <div class="panel panel-primary">
                            <div class="panel-heading">角色列表</div>
                            <input id="userId" value="${userId }" type="hidden" >
                            <input id="roleIds" type="hidden" >
                            <div id="roleList" class="panel-body">
                               
                            </div>
                        </div>
                    <div class="row">
                        <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8  btn-group">
                            <button type="button" class="btn btn-sm btn-info" onclick="selectRole()"><i class="fa fa-plus"></i>添加</button>&nbsp;&nbsp;
                            <button type="button" class="btn btn-sm btn-danger" onclick="removeRole()"><i class="fa fa-trash-o"></i>删除</button>
                        </div>
                    </div>
                    <div class="example">
                    	<table id="urole-table-list"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path%>/script/web/user/grant.js"></script>
<script type="text/javascript">
	$(function(){
		hasRoleArray = [];
		var roles="",ids="";
		var rows = JSON.parse('${roleList}');
		for(i=0;i<rows.length;i++){
			roles+="<button type='button'id='"+rows[i].id+"' class='btn btn-w-m btn-primary'>"+rows[i].role_name+"</button>&nbsp;";
			ids += rows[i].id+",";
			hasRoleArray.push(rows[i].id);
		}
		$("#roleList").html(roles);
		$('#roleIds').val(ids.substring(0,ids.length-1));
	});
</script>
</body>
</html>