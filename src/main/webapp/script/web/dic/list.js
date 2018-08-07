var bTable;
$(function () {
    bTable = $('#dic-table-list').bootstrapTable({
        height: getTableHeight("dics-form"),//高度
        cache: false,//禁用 AJAX 数据缓存
        url: root + '/dic/list',//获取数据的Servlet地址
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
                _method: "PATCH",
                params:$("#dics-form").serialize(),
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
            	title: '字典名称',
                field: 'dc_name',
                align: 'center'
            },
            {
            	title: '字典描述',
                field: 'dc_desc',
                align: 'center'
            },
            {
                title: '字典key值',
                field: 'dc_k',
                align: 'center'
            },
            {
            	title: '字典值',
                field: 'dc_v',
                align: 'center'
            },
            {
                title: '操作',
                field: 'opt',
                align: 'center' ,
                formatter:function(value,row,index){
                    return '<a title="编辑" class="btn btn-xs btn-info" onclick="editDic(&quot;'+row.id+'&quot;)"> <i class="fa fa-edit"></i></a>&nbsp;' +
                        '<a title="删除" class="btn btn-xs btn-danger" onclick="removeDics(&quot;'+row.id+'&quot;)"><i class="fa fa-trash-o"></i></a>&nbsp;';
                }
            }

        ]


    });

});
//添加用户
function addDic(){
	layer.open({
        area: ['900px', '400px'],
        type: 2,
        title: '新增字典',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/dic/add'
    });
}
//修改用户
function editDic(id){
	layer.open({
		area: ['900px', '400px'],
        type: 2,
        title: '修改字典',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/dic/edit/'+id
    });
}
//删除用户
function removeDics(singleId){
	if(!singleId){
		var rows = layerCommon("dic-table-list",true);
		if(!rows) return;
	}
	var ids = singleId?"'"+singleId+"'":getCheckValue4Ids("dic-table-list");
	layer.confirm('您是否确定删除字典？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: root + '/dic/delete',
            type: 'post',
            dataType: 'json',
            data:{
            	_method:'delete',
            	ids:ids
            },
            success: function (result) {
            	if(result.code){
    				layer.msg("操作成功！");
    				$('#dic-table-list').bootstrapTable("refresh");
    			}else{
    				layer.msg(result.msg);
    				$('#dic-table-list').bootstrapTable("refresh");
    			}
            }
        });
    });
}