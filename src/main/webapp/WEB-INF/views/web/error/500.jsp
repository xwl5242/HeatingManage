<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500错误</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico"> <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/font-awesome.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/animate.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/style.css" rel="stylesheet">
</head>
<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>500</h1>
        <h3 class="font-bold">服务器内部错误</h3>
        <div class="error-desc">
            服务器好像出错了...
            <br/>您可以返回主页看看
            <br/><a href="index.html" class="btn btn-primary m-t">主页</a>
        </div>
    </div>
    <!-- 全局js -->
    <script src="<%=path%>/static/Hplus/js/jquery.min.js"></script>
    <script src="<%=path%>/static/Hplus/js/bootstrap.min.js"></script>
</body>
</html>