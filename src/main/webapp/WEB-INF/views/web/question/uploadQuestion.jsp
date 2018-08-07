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
    <script src="<%=path%>/static/Hplus/js/jquery.form.js"></script>
</head>
<body class="gray-bg">
<div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                	<div class="row">
						<form method="POST"  enctype="multipart/form-data" id="form1">   
						    <div id="file-pretty">
						        <div class="form-group">
						            <label class="font-noraml">文件选择</label>
						            <input type="file" id="upfile" name="upfile" class="form-control">
						        </div>
						    </div>
						</form>
					</div>
					<div class="row">
						<div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
						<div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
							<button type="button" id="downbtn" name="downbtn" class="btn btn-sm btn-primary"> 下载模板</button>
	                    	<button type="button" id="uploadbtn" name="uploadbtn" class="btn btn-sm btn-success"> 导入题目</button>
						</div>
	                    <div class="col-sm-3 col-xs-3 col-md-3 col-lg-3"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script src="<%=path %>/static/Hplus/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
<script type="text/javascript">
/**
 * ajax 方式上传文件操作
 */
$(document).ready(function(){  
	//设置页面的高度
	$(".ibox-content").height($(".ibox-content").height()+40);
	//设置上传文件筐样式
	$('#file-pretty input[type="file"]').prettyFile();
	//导入excel按钮，点击事件
    $('#uploadbtn').click(function(){  
    	var loadIndex = layer.load();
        if(checkData()){  
            $('#form1').ajaxSubmit({    
            	type:'POST',
                url:'uploadQuestion',  
                dataType: 'json',  
                success: function(result){
                	layer.close(loadIndex);
                	layer.msg(result.msg);
                	if(result.code){
	                    $("#upfile").val("");  
	                    parent.$('#question-table-list').bootstrapTable("refresh");
	            		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	            		parent.layer.close(index); //再执行关闭  
                	}
                },  
                error: function(result){
                	layer.msg(result.msg);  
                } 
            });   
        }  
    });  
	$("#downbtn").click(function(){
		window.location.href=root+"/question/downloadTemplete";
	});
}); 
 
/**
 * JS校验form表单信息  
 */
function checkData(){  
   var fileDir = $("#upfile").val();  
   var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
   if("" == fileDir){  
       layer.msg("选择需要导入的Excel文件！");  
       return false;  
   }  
   if(".xls" != suffix && ".xlsx" != suffix ){  
	   layer.msg("选择Excel格式的文件导入！");  
       return false;  
   }  
   return true;  
}  
</script>
</body>
</html>