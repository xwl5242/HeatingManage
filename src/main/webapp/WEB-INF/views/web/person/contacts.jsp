<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>联系人</title>
	<jsp:include page="../commons/head.jsp"></jsp:include>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-6">
                <div class="ibox-content">
                    <div class="col-sm-8">
                        <h3><strong>开发团队</strong></h3>
                        <p><i class="fa fa-map-marker"></i> 北京·朝阳</p>
                        信息中心
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox-content">
                    <div class="col-sm-8">
                        <h3><strong>公司介绍</strong></h3>
                        <p><i class="fa fa-map-marker"></i> 北京·朝阳</p>
                        圣商
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>