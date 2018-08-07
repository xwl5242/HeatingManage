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
    <title>个人资料</title>
	<jsp:include page="../commons/head.jsp"></jsp:include>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInRight">
        	<div class="col-sm-4"></div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>个人资料</h5>
                    </div>
                    <div>
                        <div class="ibox-content no-padding border-left-right">
                            <img alt="image" class="img-responsive" src="<%=path %>/static/Hplus/img/profile_big.jpg">
                        </div>
                        <div class="ibox-content profile-content">
                            <h4><strong>圣商集团</strong></h4>
                            <p><i class="fa fa-map-marker"></i> 北京市朝阳区来广营诚盈大厦11楼</p>
                            <div class="row m-t-lg">
                                <div class="col-sm-4">
                                    <span class="bar">姓名</span>
                                    <h5><strong>${user.userName }</strong></h5>
                                </div>
                                <div class="col-sm-4">
                                    <span class="line">电话</span>
                                    <h5><strong>${user.phone }</strong></h5>
                                </div>
                                <div class="col-sm-4">
                                    <span class="bar">邮箱</span>
                                    <h5><strong>${user.mail }</strong></h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4"></div>
        </div>
    </div>
</body>

</html>
