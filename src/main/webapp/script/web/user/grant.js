var bTable;
$(function () {
    bTable = $('#urole-table-list').bootstrapTable({
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
            }

        ]


    });

});
//添加角色
function selectRole(){
	var roles = $("#roleList").html(),ids = $('#roleIds').val();
	var rows = $('#urole-table-list').bootstrapTable('getSelections');
	if(rows){
		var hasdh = false;
		for(i=0;i<rows.length;i++){
			if($.inArray(rows[i].id, hasRoleArray)==-1||hasRoleArray.length==0){
				roles+="<button type='button'id='"+rows[i].id+"' class='btn btn-w-m btn-primary'>"+rows[i].role_name+"</button>&nbsp;";
				ids += ","+rows[i].id;
				hasRoleArray.push(rows[i].id);
			}
		}
		$("#roleList").html(roles);
		$('#roleIds').val(ids);
	}
}
function removeRole(){
	var ids = "";
	var rows = $('#urole-table-list').bootstrapTable('getSelections');
	if(rows){
		for(i=0;i<rows.length;i++){
			$("#"+rows[i].id).remove();
			hasRoleArray = removeWithoutCopy(hasRoleArray,rows[i].id);
		}
		var btns = $("#roleList > button");
		for(j=0;j<btns.length;j++){
			ids += $(btns[j]).attr("id")+",";
		}
		$('#roleIds').val(ids.substring(0,ids.length-1));
	}
}

function removeWithoutCopy(arr, item) {  
    for(var i=arr.length-1;i>=0;i--)  
      {  
       if(arr[i]==item)  
         {  
           arr.splice(i,1);  
          }  
       }  
    return arr;  
} 