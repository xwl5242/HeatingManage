<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>400 页面</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="<%=path%>/static/Hplus/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/font-awesome.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/animate.css" rel="stylesheet">
    <link href="<%=path%>/static/Hplus/css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>400</h1>
        <h3 class="font-bold">由于语法格式有误，服务器无法理解此请求！</h3>
    </div>
    <!-- 全局js -->
    <script src="<%=path%>/static/Hplus/js/jquery.min.js"></script>
    <script src="<%=path%>/static/Hplus/js/bootstrap.min.js"></script>
</body>
</html>
