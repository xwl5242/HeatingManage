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
    <title>预览</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
    <style>
    	table{
    		border: solid 1px;
    	}
    	table tr{
    		border: solid 1px;line-height:30px;text-align:center;
    	}
    	table tr td{
    		border: solid 1px;
    	}
    </style>
</head>
<body class="gray-bg top-navigation">

    <div id="wrapper">
        <div id="page-wrapper" class="gray-bg">
        	<!-- 页面顶部的菜单 -->
            <div id="topmenu" class="row border-bottom white-bg">
                <nav class="navbar navbar-static-top" role="navigation">
                    <div class="navbar-header">
                        <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
                            <i class="fa fa-reorder"></i>
                        </button>
                        <a href="#" class="navbar-brand">在线考试</a>
                    </div>
                    <div class="navbar-collapse collapse" id="navbar">
                        <ul class="nav navbar-nav">
                            <li class="active">
                                <a aria-expanded="false" role="button" href="index.html"> 返回首页</a>
                            </li>
                            <li class="dropdown">
                            	<!-- 个人中心 -->
                                <a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"> 个人中心 <span class="caret"></span></a>
                                <ul role="menu" class="dropdown-menu">
                                    <li><a href="">我的信息</a>
                                    </li>
                                    <li><a href="">我的试卷</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <ul class="nav navbar-top-links navbar-right">
                            <li>
                                <a href="">
                                    <i class="fa fa-sign-out"></i> 退出
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
            <!-- 页面主题内容 -->
            <div class="wrapper wrapper-content">
                <div class="container">
					<div class="row">
			            <div class="col-lg-12">
			                <div class="ibox">
			                    <div class="ibox-content">
			                        <div class="pull-right">
<!-- 			                            <button class="btn btn-white btn-xs" type="button">700BIKE</button> -->
<!-- 			                            <button class="btn btn-white btn-xs" type="button">BeginOne</button> -->
<!-- 			                            <button class="btn btn-white btn-xs" type="button">乐视超级自行车</button> -->
			                        </div>
			                        <div class="text-center article-title">
			                            <h1 id="tpName"></h1>
			                        </div>
			                        <div id="remark">
			                        </div>
			                       	<br>
			                       	<div class="col-sm-12 col-xs-12 col-md-12 col-lg-12" style="padding-left: 0px;">
			                       		<div class="col-sm-4 col-xs-4 col-md-4 col-lg-4" style="padding-left: 0px;">
			                       			<h4>姓名：<span style="text-decoration: underline;">预览人员</span></h4>
			                       		</div>
			                       		<div class="col-sm-4 col-xs-4 col-md-4 col-lg-4" style="padding-left: 0px;">
			                       			<h4>电话：<span style="text-decoration: underline;">15252525252</span></h4>
			                       		</div>
			                       		<div class="col-sm-4 col-xs-4 col-md-4 col-lg-4" style="padding-left: 0px;">
			                       			<h4>身份证号：<span style="text-decoration: underline;">130417199009090909</span></h4>
			                       		</div>
			                       	</div>
			                       	<br>
			                       	<table id="topTables" style="border: solid 1px;">
			                       	</table>
			                        <hr>
			
			                        <div class="row">
                            			<div id="qcontent" class="col-lg-12 social-feed-box" ></div>
			                        </div>
			                    </div>
			                </div>
			            </div>
			        </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function () {
        	var type="${type}";
        	var tp,single,mutil,fillblank,answer;
        	if(type==0){
        		$("#topmenu").hide();
        		tp = opener.qtMap.tp;
        		$("#tpName").text(tp.tpName);
        		$("#remark").html(getRemarkDiv(tp.tpRemark));
        		var qTypeNum = Object.keys(opener.qtMap).length;
        		$("#topTables").html(getTopTable(qTypeNum));
        		single = opener.qtMap.qType0;
        		mutil = opener.qtMap.qType1;
        		fillblank = opener.qtMap.qType2;
        		answer = opener.qtMap.qType3;
        		var html = renderDiv(fillblank,0)+renderDiv(single,1)+renderDiv(mutil,2)+renderDiv(answer,3);
        		$("#qcontent").append(html);
        	}
        });
        //绘制题div内容
        function renderDiv(q,n){
        	var html = '<h4>'+getNum(n)+'、'+getQType(q[0].q_type)+'</h4><div class="row">';
        	if(q.length){
	        	for(var i=0;i<q.length;i++){
	        		html+='<div class="col-sm-12 col-xs-12 col-md-12 col-lg-12" style="margin-bottom:5px;">'+
	        	    '<a href="#">'+(i+1)+'.'+q[i].q_title+' ('+q[i].q_score+'分)</a>'+
	        	    '</div>';
	        	    if(q[i].q_type<=1){
		        	    html+=renderSelItem(q[i].q_sel_item);
	        	    }
	        	}
	        	return html+'</div>';
        	}
        }
        //选择题选项绘制
        function renderSelItem(sItem){
        	var html = '<div class="col-sm-12 col-xs-12 col-md-12 col-lg-12" style="margin-bottom:20px;">';
        	var sItems = sItem.split(";");
        	for(var i=0;i<sItems.length;i++){
        		var div="";
        		if(sItems[i].length<=8){
	        		var n = Math.floor(12/sItems.length);
        			div = '<div class="col-sm-'+n+' col-xs-'+n+' col-md-'+n+' col-lg-'+n+'">';
        		}else{
        			div = '<div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">';
        		}
        		html+=div+sItems[i]+'</div>';
        	}
        	return html+"</div>";
        }
        //根据类型获取汉字题类型
        function getQType(qType){
        	var r = "";
        	switch(qType){
	        	case "0":r="单选题（每小题1分，共10分。在备选答案中，选择一个正确答案并将答案题号填入题后的括号内）：";break;
	        	case "1":r="多选题（每小题2分，共10分。在备选答案中选择2个或2个以上正确答案，并将正确的答案题号填入题后括号内，选错或未选不得分）：";break;
	        	case "2":r="填空题：（将答案填入题内空白处，每空1分，共计20分 ）";break;
	        	case "3":r="问答题：（每小题10分，共40分）";break;
        	}
        	return r;
        }
        //备注信息
        function getRemarkDiv(remark){
        	var html = "备注：<br />";
        	var remarks = remark.split(";");
        	for(var i=0;i<remarks.length;i++){
        		html+="<p>"+remarks[i]+"</p>";
        	}
        	return html;
        }
        //试卷头表格
        function getTopTable(n){
        	var cw = ($(".ibox-content").width()-30)/n;
        	var html = "";
        	var top = "",bodys="";
        	for(var i=0;i<n-1;i++){
        		var tm = getNum(i);
        		top+='<td style="width:'+cw+'px;">'+tm+'</td>';
        		bodys+='<td style="width:'+cw+'px;"></td>';
        	}
        	html+='<tr>'+
	   		'	<td style="width:'+cw+'px;">题号</td>'+top+
	   		'<td style="width:'+cw+'px;">总分</td>'+
	   		'</tr>'+
	   		'<tr>'+
	   		'	<td style="width:'+cw+'px;">总分</td>'+bodys+
	   		'<td style="width:'+cw+'px;"></td>'+
	   		'</tr>'+
	   		'<tr>'+
	   		'	<td style="width:'+cw+'px;">得分</td>'+bodys+
	   		'<td style="width:'+cw+'px;"></td>'+
	   		'</tr>'+
	   		'<tr>'+
	   		'	<td style="width:'+cw+'px;">评卷人</td>'+bodys+
	   		'<td style="width:'+cw+'px;"></td>'+
	   		'</tr>';
        	return html;
        }
        function getNum(i){
        	var tm = "";
        	switch(i){
	    		case 0:tm="一";break;
	    		case 1:tm="二";break;
	    		case 2:tm="三";break;
	    		case 3:tm="四";break;
	    		case 4:tm="五";break;
			}
        	return tm;
        }
    </script>
</body>
</html>