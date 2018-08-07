<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>在线答题系统</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="shortcut icon" href="<%=path %>/static/Hplus/favicon.ico"><!-- 网站标签 -->
<link href="<%=path %>/static/Hplus/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/animate.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/plugins/jsTree/style.min.css" rel="stylesheet">
<link href="<%=path %>/static/Hplus/css/style.css" rel="stylesheet">

<!-- 全局js -->
<script src="<%=path %>/static/Hplus/js/jquery.min.js"></script>
<script src="<%=path %>/static/Hplus/js/bootstrap.min.js"></script>
<script src="<%=path %>/static/Hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="<%=path %>/static/Hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="<%=path %>/static/Hplus/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="<%=path %>/static/Hplus/js/hplus.js"></script>
<script src="<%=path %>/static/Hplus/js/contabs.js"></script>

<!-- 第三方插件 -->
<script src="<%=path %>/static/Hplus/js/plugins/pace/pace.min.js"></script>

<!--下拉框chosen-->
<script src="<%=path %>/static/Hplus/js/plugins/chosen/chosen.jquery.js"></script>

<!-- Bootstrap table -->
<script src="<%=path %>/static/Hplus/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="<%=path %>/static/Hplus/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<!-- jQuery Validation 表单校验-->
<script src="<%=path%>/static/Hplus/js/plugins/validate/jquery.validate.min.js"></script>
<script src="<%=path%>/static/Hplus/js/plugins/validate/messages_zh.min.js"></script>
<script src="<%=path%>/script/web/commons/common4Validate.js"></script>

<!-- jsTree plugin javascript -->
<script src="<%=path%>/static/Hplus/js/plugins/jsTree/jstree.min.js"></script>
<script>var root = '<%=path%>';</script>
<script src="<%=path%>/script/web/commons/commonJs.js"></script>
<script src="<%=path%>/script/web/commons/jsTreeCommon.js"></script>