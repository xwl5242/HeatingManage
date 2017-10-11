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
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="<%=path%>/static/Hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/animate.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/style.css?v=4.1.0" rel="stylesheet">
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>
<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name"></h1>
            </div>
            <h3>欢迎使用**系统</h3>
            <form class="m-t" role="form" action="<%=path %>/loginIn">
                <div class="form-group">
                    <input type="email" name="userName" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="密码" required="">
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
<!--                 <p class="text-muted text-center">  -->
<!--                 	<a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a> -->
<!--                 </p> -->
            </form>
        </div>
    </div>
    <!-- 全局js -->
    <script src="<%=path%>/static/Hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="<%=path%>/static/Hplus/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="<%=path%>/script/login.js"></script>
</body>
</html>
