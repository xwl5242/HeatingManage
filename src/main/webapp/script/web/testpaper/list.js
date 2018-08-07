var bTable;
$(function () {
    bTable = $('#testpaper-table-list').bootstrapTable({
        height: getTableHeight("testpaper-form"),//高度
        cache: false,//禁用 AJAX 数据缓存
        url: root + '/testpaper/list',//获取数据的Servlet地址
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
                params:$("#testpaper-form").serialize(),
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
            	title: '试卷名称',
                field: 'tp_name',
                align: 'center'
            },
            {
            	title: '试卷标题',
                field: 'tp_title',
                align: 'center'
            },
            {
            	title: '试卷备注',
                field: 'tp_remark',
                align: 'center'
            },
            {
            	title: '是否发布',
                field: 'is_publish',
                align: 'center',
                formatter:function(value,row,index){
                	var result = "";
                	switch(value){
	                	case "0":result="否";break;
	                	case "1":result="是";break;
                	}
                	return result;
                }
            }
        ]
    });
});
//添加试卷
function addTestPaper(){
	layer.open({
        area: ['800px', '750px'],
        type: 2,
        title: '新增试卷',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/testpaper/add'
    });
}
//修改试卷
function editTestPaper(){
	var rows = layerCommon("testpaper-table-list",false);
	if(!rows) return;
	layer.open({
		area: ['800px', '750px'],
        type: 2,
        title: '修改试卷',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/testpaper/edit/'+rows[0].id
    });
}
//删除试卷
function removeTestPaper(){
	var rows = layerCommon("testpaper-table-list",false);
	if(!rows) return;
	layer.confirm('您是否确定删除试卷？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: root + '/testpaper/delete/'+rows[0].id,
            type: 'post',
            dataType: 'json',
            data:{
            	_method:'delete'
            },
            success: function (result) {
            	if(result.code){
    				layer.msg("操作成功！");
    				$('#testpaper-table-list').bootstrapTable("refresh");
    			}else{
    				layer.msg(result.msg);
    				$('#testpaper-table-list').bootstrapTable("refresh");
    			}
            }
        });
    });
}
//发布或取消发布试卷
function publishTestPaper(status){
	var rows = layerCommon("testpaper-table-list",false);
	if(!rows) return;
	if(rows[0].is_publish==1&&status==1){
		layer.msg("该试卷已经发布，请重新选择！");
		return;
	}else if(status==1){
		var datas = $('#testpaper-table-list').bootstrapTable('getData');
		for(var i=0;i<datas.length;i++){
			if(datas[i].is_publish==1){
				layer.msg("已经存在'已发布'状态的试卷了，请谨慎操作！");
				return;
			}
		}
	}
	if(rows[0].is_publish==0&&status==0){
		layer.msg("该试卷已经取消发布，请重新选择！");
		return;
	}
	$.ajax({
        url: root + '/testpaper/publish/'+rows[0].id+'/'+status,
        type: 'post',
        dataType: 'json',
        data:{
        	_method:'put'
        },
        success: function (result) {
        	if(result.code){
				layer.msg("操作成功！");
				$('#testpaper-table-list').bootstrapTable("refresh");
			}else{
				layer.msg(result.msg);
				$('#testpaper-table-list').bootstrapTable("refresh");
			}
        }
    });
}
//给试卷派题
function grantTestPaper(){
	var rows = layerCommon("testpaper-table-list",false);
	if(!rows) return;
	//新建tab页，打开派题页面
	createNewMenuCommon("派题页面","testpaper/grant/"+rows[0].id,99,true);
}