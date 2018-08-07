var bTable;
$(function () {
    bTable = $('#user-table-list').bootstrapTable({
        height: getTableHeight("users-form"),//高度
        cache: false,//禁用 AJAX 数据缓存
        url: root + '/user/list',//获取数据的Servlet地址
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
                params:$("#users-form").serialize(),
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
            	title: '登录名称',
                field: 'user_code',
                align: 'center'
            },
            {
            	title: '登录密码',
                field: 'password',
                align: 'center'
            },
            {
                title: '用户名称',
                field: 'user_name',
                align: 'center'
            },
            {
            	title: '用户昵称',
                field: 'nick_name',
                align: 'center'
            },
            {
            	title: '用户状态',
                field: 'use_status',
                align: 'center',
                formatter:function(value,row,index){
                	return value==0?"禁用":"启用";
                }
            },
            {
            	title: '用户电话',
                field: 'phone',
                align: 'center'
            },
            {
            	title: '用户邮箱',
                field: 'mail',
                align: 'center'
            },
            {
                title: '操作',
                field: 'opt',
                align: 'center' ,
                formatter:function(value,row,index){
                    return '<a title="编辑" class="btn btn-xs btn-info" onclick="editUser(&quot;'+row.id+'&quot;)"> <i class="fa fa-edit"></i></a>&nbsp;' +
                        '<a title="删除" class="btn btn-xs btn-danger" onclick="removeUsers(&quot;'+row.id+'&quot;)"><i class="fa fa-trash-o"></i></a>&nbsp;' +
                        '<a title="授权权限" class="btn btn-xs btn-warning" onclick="grantRole(&quot;'+row.id+'&quot;)"><i class="fa fa-wrench"></i></a>';
                }
            }

        ]


    });

});
//添加用户
function addUser(){
	layer.open({
        area: ['900px', '400px'],
        type: 2,
        title: '新增用户',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/user/add'
    });
}
//修改用户
function editUser(id){
	layer.open({
		area: ['900px', '400px'],
        type: 2,
        title: '修改用户',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/user/edit/'+id
    });
}
function grantRole(id){
	layer.open({
		area: ['900px', '600px'],
        type: 2,
        title: '用户授权',
        btn: ['保存', '取消'],
        yes: function(index, layero){
        	var body = layer.getChildFrame('body', index);
        	var roleIds = body.find('#roleIds').val();
        	var userId = body.find('#userId').val();
            $.ajax({
            	url:root+"/user/grant",
            	type:"post",
            	dataType:"json",
            	data:{
            		userId:userId,
            		roleIds:roleIds
            	},
            	success:function(result){
            		hasRoleArray = [];
            		layer.close(index);
            		layer.msg("用户授权成功！");
    				$('#user-table-list').bootstrapTable("refresh");
            	}
            });
        },
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/user/grant/'+id
    });
}
//启用或禁用用户
function userAbled(status){
	var rows = layerCommon("user-table-list",true);
	if(!rows) return;
	var ids = getCheckValue4Ids("user-table-list");
	$.ajax({
		url:root+"/user/useStatus",
		type:'post',
		dataType:'json',
		data:{
			_method : "put",
			status:status,
			ids:ids
		},
		success:function(result){
			if(result.code){
				layer.msg("操作成功！");
				$('#user-table-list').bootstrapTable("refresh");
			}else{
				layer.msg(result.msg);
				$('#user-table-list').bootstrapTable("refresh");
			}
		}
	});
}
//重置密码
function resetPwd(){
	var rows = layerCommon("user-table-list",true);
	if(!rows) return;
	var ids = getCheckValue4Ids("user-table-list");
	$.ajax({
		url:root+"/user/resetPwd",
		type:'post',
		dataType:'json',
		data:{
			_method : "put",
			ids:ids
		},
		success:function(result){
			if(result.code){
				layer.msg("操作成功！");
				$('#user-table-list').bootstrapTable("refresh");
			}else{
				layer.msg(result.msg);
				$('#user-table-list').bootstrapTable("refresh");
			}
		}
	});
}
//删除用户
function removeUsers(id){
	if(!id){
		var rows = layerCommon("user-table-list",true);
		if(!rows) return;
	}
	var ids = id?"'"+id+"'":getCheckValue4Ids("user-table-list");
	layer.confirm('您是否确定删除用户？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: root + '/user/delete',
            type: 'post',
            dataType: 'json',
            data:{
            	_method:'delete',
            	ids:ids
            },
            success: function (result) {
            	if(result.code){
    				layer.msg("操作成功！");
    				$('#user-table-list').bootstrapTable("refresh");
    			}else{
    				layer.msg(result.msg);
    				$('#user-table-list').bootstrapTable("refresh");
    			}
            }
        });
    });
}