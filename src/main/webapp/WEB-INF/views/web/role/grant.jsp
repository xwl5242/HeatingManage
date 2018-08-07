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
<body class="gray-bg" >
<div class="wrapper wrapper-content animated fadeInRight">
<input id="modules" type="hidden" value="right">
    <div class="row">
    
    	<div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
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
                <div class="ibox-content">
                	<div class="row">
                        <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
							 	<input type="text" class="form-control" id="org-search-val" placeholder="请输入权限名称">
						</div>
						<div class="col-sm-2 col-xs-2 col-md-2 col-lg-2">
                             	<button type="button" class="btn btn-sm btn-primary otree-search"> 搜索</button>
                        </div>
                    </div>
                    <br>
                    <div id="org-tree" class="otree-multiple"></div>
                    <input id="roleId" type="hidden" value="${roleId }">
                    <input id="rightIds" type="hidden" >
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script type="text/javascript">
$(function(){
	$(".otree-multiple").orgTree({core:{multiple:true},plugins:['checkbox']});
	console.log($("#org-tree").jstree());
	setTimeout("initTree()",1000);
	//tree选中事件
	$("#org-tree").on('select_node.jstree',function(e,data){
		var ids = $("#org-tree").jstree().get_checked();
		$("#rightIds").val(ids.join());
	})
	//tree取消选中事件
	$("#org-tree").on('deselect_node.jstree',function(e,data){
		var ids = $("#org-tree").jstree().get_checked();
		$("#rightIds").val(ids.join());
	})
});
function initTree(){
	if(!$("#org-tree").jstree()._cnt){
		setTimeout("initTree()",1000);
	}else{
		$(".ibox-content").height($(".otree-multiple").height()+100);
		var rows = JSON.parse('${rightList}'),ids="";
		for(var i=0;i<rows.length;i++){
			$("#org-tree").jstree().check_node(rows[i].right_id);
			ids+=","+rows[i].right_id;
		}
		$("#rightIds").val(ids);
	}
}
</script>
</body>
</html>