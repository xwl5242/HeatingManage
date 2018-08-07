<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="<%=path %>/static/Hplus/favicon.ico"> 
    <link href="<%=path%>/static/Hplus/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/font-awesome.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/animate.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/style.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/login.css" rel="stylesheet">
    <script>
    	var root = '<%=path%>';
    	if(window.top !== window.self){ window.top.location = window.location;}
    </script>
</head>
<body class="signin">
	<div class="loginback-back"></div>
    <div class="signinpanel">
        <div class="row">
        	<div class="col-sm-3"></div>
            <div class="col-sm-6">
                <form class="m-t" id="backgroundform" onsubmit="false">
                	<div style="text-align: center;margin-top: 0px;margin-bottom: 20px;font-size: 24px;">资格考试后台管理系统</div>
	                <div class="form-group">
	                    <input name="userCode" class="form-control uname" placeholder="用户名" required="">
	                </div>
	                <div class="form-group">
	                    <input type="password" name="password" class="form-control pword m-b" placeholder="密码" required="">
	                </div>
	                <button type="submit" id="login" class="btn btn-primary block full-width m-b">登 录</button>
	            </form>
            </div>
            <div class="col-sm-3"></div>
        </div>
    </div>
    <!-- 全局js -->
    <script src="<%=path%>/static/Hplus/js/jquery.min.js"></script>
    <script src="<%=path%>/static/Hplus/js/bootstrap.min.js"></script>
    <!-- jQuery Validation 表单校验-->
    <script src="<%=path%>/static/Hplus/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="<%=path%>/static/Hplus/js/plugins/validate/messages_zh.min.js"></script>
    <script src="<%=path%>/script/web/commons/common4Validate.js"></script>
    <script src="<%=path%>/script/web/login.js"></script>
</body>
</html>
