var bTable;
$(function () {
    bTable = $('#right-table-list').bootstrapTable({
        height: getTableHeight(""),//高度
        cache: false,//禁用 AJAX 数据缓存
        url: root + '/right/list',//获取数据的Servlet地址
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
                params:$("#otreeSelectedId").val(),
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
            	title: '权限名称',
                field: 'right_name',
                align: 'center'
            },
            {
            	title: '权限url',
                field: 'right_url',
                align: 'center'
            },
            {
            	title: '权限描述',
                field: 'right_desc',
                align: 'center'
            },
            {
                title: '操作',
                field: 'opt',
                align: 'center' ,
                formatter:function(value,row,index){
                	return '<a title="添加" class="btn btn-xs btn-info" onclick="addRight(&quot;'+row.id+'&quot;)"> <i class="fa fa-plus"></i></a>&nbsp;' +
                	    '<a title="编辑" class="btn btn-xs btn-info" onclick="editRight(&quot;'+row.id+'&quot;)"> <i class="fa fa-edit"></i></a>&nbsp;' +
                        '<a title="删除" class="btn btn-xs btn-danger" onclick="removeRights(&quot;'+row.id+'&quot;)"><i class="fa fa-trash-o"></i></a>&nbsp;';
                }
            }

        ]
    });
    //设置机构树的高度
    $("#orgTreeH").height(getTableHeight("")-50);
    $(".otree").on("changed.jstree", function (e, data) {  
    	var sId = !data.node?$("#otreeSelectedId").val():data.node.id;
    	$("#otreeSelectedId").val(sId);
    	$('#right-table-list').bootstrapTable("refresh");
    });  

});
//添加权限
function addRight(id){
	layer.open({
        area: ['800px', '400px'],
        type: 2,
        title: '新增权限',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/right/add/'+id
    });
}
//修改权限
function editRight(id){
	layer.open({
		area: ['800px', '400px'],
        type: 2,
        title: '修改权限',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/right/edit/'+id
    });
}
//删除权限
function removeRights(singleId){
	layer.confirm('您是否确定删除权限？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.ajax({
            url: root + '/right/delete',
            type: 'post',
            dataType: 'json',
            data:{
            	_method:'delete',
            	ids:singleId
            },
            success: function (result) {
            	if(result.code){
    				layer.msg("操作成功！");
    				parent.location.reload(); 
    			}else{
    				layer.msg(result.msg);
    				$(".otree").jstree(true).refresh();
    				$('#right-table-list').bootstrapTable("refresh");
    			}
            }
        });
    });
}