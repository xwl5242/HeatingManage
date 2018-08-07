<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <link href="<%=path %>/static/Hplus/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path %>/static/Hplus/css/font-awesome.min.css" rel="stylesheet">
	<link href="<%=path %>/static/Hplus/css/animate.css" rel="stylesheet">
	<link href="<%=path %>/static/Hplus/css/style.css" rel="stylesheet">
	<style>
		img{
			width: 100%;
		}
	</style>
</head>

<body class="gray-bg">
    <div class="row  border-bottom white-bg dashboard-header" style="height: 100%;padding: 20px 20px 20px 20px;">
            <div class="ibox float-e-margins" style="height: 100%">
                <div class="ibox-content" style="height: 100%">
                    <div class="carousel slide" id="carousel2" style="height: 100%">
                        <ol class="carousel-indicators">
                            <li data-slide-to="0" data-target="#carousel2" class="active"></li>
                            <li data-slide-to="1" data-target="#carousel2"></li>
                            <li data-slide-to="2" data-target="#carousel2" class=""></li>
                        </ol>
                        <div class="carousel-inner" style="height: 100%">
                            <div class="item active" style="height: 100%">
                                <img alt="image" class="img-responsive" src="<%=path %>/static/Hplus/img/p_big1.jpg">
                                <div class="carousel-caption">
                                    <p></p>
                                </div>
                            </div>
                            <div class="item " style="height: 100%">
                                <img alt="image" class="img-responsive" src="<%=path %>/static/Hplus/img/p_big3.jpg">
                                <div class="carousel-caption">
                                    <p></p>
                                </div>
                            </div>
                            <div class="item" style="height: 100%">
                                <img alt="image" class="img-responsive" src="<%=path %>/static/Hplus/img/p_big2.jpg">
                                <div class="carousel-caption">
                                    <p></p>
                                </div>
                            </div>
                        </div>
                        <a data-slide="prev" href="welcome#carousel2" class="left carousel-control">
                            <span class="icon-prev"></span>
                        </a>
                        <a data-slide="next" href="welcome#carousel2" class="right carousel-control">
                            <span class="icon-next"></span>
                        </a>
                    </div>
                </div>
            </div>
    </div>
<script src="<%=path %>/static/Hplus/js/jquery.min.js"></script>
<script src="<%=path %>/static/Hplus/js/bootstrap.min.js"></script>
</body>
</html>