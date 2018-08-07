var bTable;
$(function () {
    bTable = $('#question-table-list').bootstrapTable({
        height: getTableHeight("question-form"),//高度
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
                params:$("#question-form").serialize(),
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
            },
            {
                title: '操作',
                field: 'opt',
                align: 'center' ,
                formatter:function(value,row,index){
                    return '<a title="编辑" class="btn btn-xs btn-info" onclick="editQuestion(&quot;'+row.id+'&quot;)"> <i class="fa fa-edit"></i></a>&nbsp;' +
                        '<a title="删除" class="btn btn-xs btn-danger" onclick="removeQuestion(&quot;'+row.id+'&quot;)"><i class="fa fa-trash-o"></i></a>';
                }
            }

        ]


    });

});
//导入问题
function importExcel(){
	layer.open({
        area: ['400px', '400px'],
        type: 2,
        title: '新增问题',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/question/uploadQuestion'
    });
}
//添加问题
function addQuestion(){
	layer.open({
        area: ['800px', '750px'],
        type: 2,
        title: '新增问题',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/question/add'
    });
}
//修改问题
function editQuestion(id){
	layer.open({
		area: ['800px', '750px'],
        type: 2,
        title: '修改问题',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/question/edit/'+id
    });
}
//删除问题
function removeQuestion(singleId){
	if(!singleId){
		var rows = layerCommon("question-table-list",true);
		if(!rows) return;
	}
	var ids = singleId?"'"+singleId+"'":getCheckValue4Ids("question-table-list");
	layer.confirm('您是否确定删除问题？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: root + '/question/delete',
            type: 'post',
            dataType: 'json',
            data:{
            	_method:'delete',
            	ids:ids
            },
            success: function (result) {
            	if(result.code){
    				layer.msg("操作成功！");
    				$('#question-table-list').bootstrapTable("refresh");
    			}else{
    				layer.msg(result.msg);
    				$('#question-table-list').bootstrapTable("refresh");
    			}
            }
        });
    });
}