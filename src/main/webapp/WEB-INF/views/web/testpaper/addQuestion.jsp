<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>

<head>
    <title>用户管理</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<input id="modules" type="hidden" value="question">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="addQ-form" role="form" class="form-horizontal m-t">
                    	<input id="hasIds" name="hasIds" value="${hasIds }" type="hidden">
						<!-- 表单 -->
                        <div class="row">
                            <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题标题</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" class="form-control" name="qTitle" placeholder="请输入问题标题">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">问题类型</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <select id="qType" name="qType" class="chosen-select form-control">
                                            <option value="">全部</option>
                                            <option value="0">单选</option>
                                            <option value="1">多选</option>
                                            <option value="2">填空</option>
                                            <option value="3">问答</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4">
                                <div class="btn-tools col-sm-6 col-xs-6 col-md-6 col-lg-6">
	                                <button type="button" class="btn btn-sm btn-primary search"> 搜索</button>
	                            </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8  btn-group">
                                <button type="button" class="btn btn-sm btn-info" onclick="addQuestion()"><i class="fa fa-plus"></i>添加</button>
                            </div>
                        </div>
                    </form>
                    <div class="example">
                    	<table id="addQ-table-list"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path%>/script/web/testpaper/addQuestion.js"></script>
<script type="text/javascript">
$(function(){
	//给题目类型赋值
	$("#qType").find("option[value='${type}']").attr("selected",true);
	//赋值后，根据类型获取该类型题目列表，提供选择
	var bTable = $('#addQ-table-list').bootstrapTable({
	    height: getTableHeight("addQ-form"),//高度
	    cache: false,//禁用 AJAX 数据缓存
	    url: root + '/question/list',//获取数据的Servlet地址
	    method: 'post',//使用POST请求到服务器获取数据
	    contentType: "application/x-www-form-urlencoded",
	    dataType: "json",
	    idField: "ID",
	    pagination: true,//是否分页
	    pageSize: 10,//每页显示的记录数
	    pageNumber: 1,//当前第几页
	    pageList: [10, 30, 50],//记录数可选列表
	    search: false,  //是否启用查询
	    striped: true,//表格显示条纹
	    sidePagination: "server", //服务端请求
	    queryParamsType: "undefined",
	    queryParams: function queryParams(params) {
	        var param = {
	            _method: "POST",
	            params:$("#addQ-form").serialize(),
	            pageNumber: params.pageNumber,
	            pageSize: params.pageSize
	        };
	        return param;
	    }, formatLoadingMessage: function () {
	        return "请稍等，正在加载中...";
	    },
	    responseHandler: function (res) {
	        return {
	            "rows": res.rows,
	            "total": res.total
	        };
	    },
	    columns: [
	        {
	            field: 'id', title: 'ID', visible: false
	        },
	        {
	            title: '序号',
	            field: 'sn',
	            align: 'center',
	            formatter:function(value,row,index){
	                return index+1;
	            }
	        },
	        {
	        	checkbox: true
	        },
	        {
	        	title: '问题标题',
	            field: 'q_title',
	            align: 'center'
	        },
	        {
	        	title: '问题类型',
	            field: 'q_type',
	            align: 'center',
	            formatter:function(value,row,index){
	            	var result = "";
	            	switch(value){
	                	case "0":result="单选";break;
	                	case "1":result="多选";break;
	                	case "2":result="填空";break;
	                	case "3":result="问答";break;
	            	}
	            	return result;
	            }
	        },
	        {
	            title: '问题分数',
	            field: 'q_score',
	            align: 'center'
	        },
	        {
	        	title: '问题答案',
	            field: 'q_answer',
	            align: 'center'
	        },
	        {
	        	title: '问题选项',
	            field: 'q_sel_item',
	            align: 'center',
	            formatter:function(value,row,index){
	            	return (row.q_type<=1)?value:"该问题类型不是选择题";
	            }
	        },
	        {
	        	title: '答案关键字',
	            field: 'q_aw_keyword',
	            align: 'center',
	            formatter:function(value,row,index){
	            	return (row.q_type==3)?value:"该问题类型不是问答题";
	            }
	        }
	    ]
	});
});
</script>
</body>
</html>