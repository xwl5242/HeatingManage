<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <title>修改字典</title>
    <jsp:include page="../commons/head.jsp"></jsp:include>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
		<!-- 右侧 -->
        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="addform" role="form" class="form-horizontal m-t">
                    	<input id="id" name="id" type="hidden" value="${dic.id }">
                    	<input id="dcSeq" name="dcSeq" type="hidden" value="${dic.dcSeq }">
                    	<input id="_method" name="_method" type="hidden" value="put">
						<!-- 表单 -->
                        <div class="row">
                            <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">字典名称</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" value="${dic.dcName }" maxlength="16" class="form-control" id="dcName" name="dcName" placeholder="请输入字典名称">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">字典描述</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" value="${dic.dcDesc }" class="form-control" name="dcDesc" placeholder="请输入字典描述">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">字典key值</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text" value="${dic.dcK }" class="form-control" name="dcK" placeholder="请输入字典key值">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-xs-6 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">字典值</label>
                                    <div class="col-sm-7 col-xs-7 col-md-7 col-lg-7">
                                        <input type="text"  value="${dic.dcV }" class="form-control" name="dcV" placeholder="请输入字典值">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-9 col-xs-9 col-md-9 col-lg-9  btn-group"></div>
                            <div class="btn-tools col-sm-3 col-xs-3 col-md-3 col-lg-3">
                                <button type="submit" class="btn btn-sm btn-success"> 保存</button>
                                <button type="button" class="btn btn-sm btn-default layer-cancel"> 取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义js -->
<script src="<%=path%>/script/web/dic/edit.js"></script>
<script type="text/javascript">
	$(function(){
		oldDicName = '${dic.dcName}';
	});
</script>
</body>
</html>