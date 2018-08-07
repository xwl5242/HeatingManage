var bTable;
$(function () {
    bTable = $('#role-table-list').bootstrapTable({
        height: getTableHeight("roles-form"),//高度
        cache: false,//禁用 AJAX 数据缓存
        url: root + '/role/list',//获取数据的Servlet地址
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
                params:$("#roles-form").serialize(),
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
            	title: '角色名称',
                field: 'role_name',
                align: 'center'
            },
            {
            	title: '角色描述',
                field: 'role_desc',
                align: 'center'
            },
            {
                title: '操作',
                field: 'opt',
                align: 'center' ,
                formatter:function(value,row,index){
                    return '<a title="编辑" class="btn btn-xs btn-info" onclick="editRole(&quot;'+row.id+'&quot;)"> <i class="fa fa-edit"></i></a>&nbsp;' +
                        '<a title="删除" class="btn btn-xs btn-danger" onclick="removeRoles(&quot;'+row.id+'&quot;)"><i class="fa fa-trash-o"></i></a>&nbsp;' +
                        '<a title="授权权限" class="btn btn-xs btn-warning" onclick="grantRight(&quot;'+row.id+'&quot;)"><i class="fa fa-wrench"></i></a>';
                }
            }

        ]


    });

});
//添加角色
function addRole(){
	layer.open({
        area: ['900px', '400px'],
        type: 2,
        title: '新增角色',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/role/add'
    });
}
//修改角色
function editRole(id){
	layer.open({
		area: ['900px', '400px'],
        type: 2,
        title: '修改角色',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/role/edit/'+id
    });
}
//删除角色
function removeRoles(singleId){
	if(!singleId){
		var rows = layerCommon("role-table-list",true);
		if(!rows) return;
	}
	var ids = singleId?"'"+singleId+"'":getCheckValue4Ids("role-table-list");
	layer.confirm('您是否确定删除角色？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: root + '/role/delete',
            type: 'post',
            dataType: 'json',
            data:{
            	_method:'delete',
            	ids:ids
            },
            success: function (result) {
            	if(result.code){
    				layer.msg("操作成功！");
    				$('#role-table-list').bootstrapTable("refresh");
    			}else{
    				layer.msg(result.msg);
    				$('#role-table-list').bootstrapTable("refresh");
    			}
            }
        });
    });
}
function grantRight(id){
	layer.open({
		area: ['600px', '400px'],
        type: 2,
        title: '角色授权',
        btn: ['保存', '取消'],
        yes: function(index, layero){
        	var body = layer.getChildFrame('body', index);
        	var roleId = body.find('#roleId').val();
        	var rightIds = body.find('#rightIds').val();
            $.ajax({
            	url:root+"/role/grant",
            	type:"post",
            	dataType:"json",
            	data:{
            		roleId:roleId,
            		rightIds:rightIds
            	},
            	success:function(result){
            		layer.close(index);
            		layer.msg("角色授权成功！");
            		parent.location.reload();
            	}
            });
        },
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/role/grant/'+id
    });
}