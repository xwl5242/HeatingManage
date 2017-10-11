<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>能源管控中心</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="shortcut icon" href="favicon.ico">
<link href="<%=path %>/static/Hplus/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/animate.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/style.css?v=4.1.0" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/mystyle.css" rel="stylesheet">
<!-- 全局js -->
<script src="<%=path %>/static/Hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=path %>/static/Hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="<%=path %>/static/Hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="<%=path %>/static/Hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="<%=path %>/static/Hplus/js/plugins/layer/layer.min.js"></script>
<!-- 自定义js -->
<script src="<%=path %>/static/Hplus/js/hplus.js?v=4.1.0"></script>
<script src="<%=path %>/static/Hplus/js/contabs.js"></script>
<!-- 第三方插件 -->
<script src="<%=path %>/static/Hplus/js/plugins/pace/pace.min.js"></script>
<!--下拉框chosen-->
<script src="<%=path %>/static/Hplus/js/plugins/chosen/chosen.jquery.js"></script>