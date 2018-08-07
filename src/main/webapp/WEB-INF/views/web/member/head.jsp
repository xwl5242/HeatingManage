<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta name="format-detection" content="telephone=no">
<title>首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="shortcut icon" href="<%=path %>/static/webFront/favicon.ico">
<link rel="stylesheet" href="<%=path %>/static/webFront/css/webbackgroud.css">
<script src="<%=path %>/static/Hplus/js/jquery.min.js"></script>
<script src="<%=path %>/script/md5.js"></script>
<script src="<%=path %>/script/sha1.js"></script>
<script src="<%=path%>/script/IdentityCodeValid.js"></script>
<script src="<%=path %>/script/web/member/common.js"></script>
<script>
var root = '<%=path%>';
window.onload=function(){
	member = '${sessionScope.member}';
	memberName = '${sessionScope.memberName}';
	//如果登录成功，登录按钮隐藏，个人信息显示
	if(member){
		$(".not_logged_in").hide();//登录按钮隐藏
		//个人信息显示
		$(".account").css("padding-top",0);
		$(".logged_in").show();
	}
	//我的答题
	$("#gotomytp").click(function(){
		if(!member){
			goingLogin();
		}else{
			window.location.href=root+"/m/mytplist";
		}
	})
}
</script>