<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>

<head>
    <title>派题</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
        	<input id="tpId" name="tpId" type="hidden" value="${tpId }">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                      <div class="panel panel-default">
		                  <div class="panel-heading">单选题</div>
		                  <p></p>
			              <div class="row">
				              <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8  btn-group">
					              <button type="button" class="btn btn-sm btn-info" onclick="addQuestion('0')"><i class="fa fa-plus"></i>添加</button>&nbsp;&nbsp;
<!-- 					              <button type="button" class="btn btn-sm btn-danger" onclick="removeQues('0')"><i class="fa fa-trash-o"></i>删除</button> -->
				              </div>
			              </div>
			              <div class="example">
			                  <table id="singleSel-table-list">
			                      <thead>
                                    <tr>
                                        <th data-field="id" data-visible="false">ID</th>
                                        <th data-field="sn" data-formatter="snFormat">序号</th>
                                        <th data-field="title">题目名称</th>
                                        <th data-field="score">题目分数</th>
                                        <th data-field="selItem">题目选项</th>
                                        <th data-field="answer">题目答案</th>
                                        <th data-field="opt">操作</th>
                                    </tr>
                                  </thead>
			                  </table>
			              </div>
                      </div>
                      <div class="panel panel-default">
		                  <div class="panel-heading">多选题</div>
		                  <p></p>
			              <div class="row">
				              <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8  btn-group">
					              <button type="button" class="btn btn-sm btn-info" onclick="addQuestion('1')"><i class="fa fa-plus"></i>添加</button>&nbsp;&nbsp;
<!-- 					              <button type="button" class="btn btn-sm btn-danger" onclick="removeQues('1')"><i class="fa fa-trash-o"></i>删除</button> -->
				              </div>
			              </div>
			              <div class="example">
			                  <table id="mutilSel-table-list">
			                  	 <thead>
                                    <tr>
                                        <th data-field="id" data-visible="false">ID</th>
                                        <th data-field="sn" data-formatter="snFormat">序号</th>
                                        <th data-field="title">题目名称</th>
                                        <th data-field="score">题目分数</th>
                                        <th data-field="selItem">题目选项</th>
                                        <th data-field="answer">题目答案</th>
                                        <th data-field="opt">操作</th>
                                    </tr>
                                  </thead>
			                  </table>
			              </div>
                      </div>
                      <div class="panel panel-default">
		                  <div class="panel-heading">填空题</div>
		                  <p></p>
			              <div class="row">
				              <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8  btn-group">
					              <button type="button" class="btn btn-sm btn-info" onclick="addQuestion('2')"><i class="fa fa-plus"></i>添加</button>&nbsp;&nbsp;
<!-- 					              <button type="button" class="btn btn-sm btn-danger" onclick="removeQues('2')"><i class="fa fa-trash-o"></i>删除</button> -->
				              </div>
			              </div>
			              <div class="example">
			                  <table id="fillblank-table-list">
			                  	 <thead>
                                    <tr>
                                        <th data-field="id" data-visible="false">ID</th>
                                        <th data-field="sn" data-formatter="snFormat">序号</th>
                                        <th data-field="title">题目名称</th>
                                        <th data-field="score">题目分数</th>
                                        <th data-field="answer">题目答案</th>
                                        <th data-field="opt">操作</th>
                                    </tr>
                                  </thead>
			                  </table>
			              </div>
                      </div>
                      <div class="panel panel-default">
		                  <div class="panel-heading">问答题</div>
		                  <p></p>
			              <div class="row">
				              <div class="col-sm-8 col-xs-8 col-md-8 col-lg-8  btn-group">
					              <button type="button" class="btn btn-sm btn-info" onclick="addQuestion('3')"><i class="fa fa-plus"></i>添加</button>&nbsp;&nbsp;
<!-- 					              <button type="button" class="btn btn-sm btn-danger" onclick="removeQues('3')"><i class="fa fa-trash-o"></i>删除</button> -->
				              </div>
			              </div>
			              <div class="example">
			                  <table id="answer-table-list">
			                  	 <thead>
                                    <tr>
                                        <th data-field="id" data-visible="false">ID</th>
                                        <th data-field="sn" data-formatter="snFormat">序号</th>
                                        <th data-field="title">题目名称</th>
                                        <th data-field="score">题目分数</th>
                                        <th data-field="answer">题目答案</th>
                                        <th data-field="awkeyword">题目答案关键字</th>
                                        <th data-field="opt">操作</th>
                                    </tr>
                                  </thead>
			                  </table>
			              </div>
                      </div>
                      <div class="row">
                          <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4  btn-group"></div>
                          <div class="btn-tools col-sm-4 col-xs-4 col-md-4 col-lg-4">
<!--                               <button type="button" onclick="preView()" class="btn btn-sm btn-primary"> 预览</button> -->
                              <input type="button" onclick="saveTQ()" class="btn btn-sm btn-success" value="保存"> 
                              <input type="button" onclick="saveTQAndClose()" class="btn btn-sm btn-warning" value="保存并关闭"> 
                              <input type="button" onclick="closeTQ()" class="btn btn-sm btn-default layer-cancel" value="关闭"> 
                          </div>
                          <div class="col-sm-4 col-xs-4 col-md-4 col-lg-4  btn-group"></div>
                      </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path %>/script/web/commons/myTab.js"></script>
<script src="<%=path%>/script/web/testpaper/grant.js"></script>
<script>
	window.onload=function(){
		$(".ibox-content").height('100%');
	}
	var tpqLists = '${tpqList}';
	var snFormat = function(value,row,index){
        return index+1;
    }
</script>
</body>
</html>