var bTable;
$(function () {
    bTable = $('#log-table-list').bootstrapTable({
        height: getTableHeight("logs-form"),//高度
        cache: false,//禁用 AJAX 数据缓存
        url: root + '/log/list',//获取数据的Servlet地址
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
                params:$("#logs-form").serialize(),
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
            	title: '操作名称',
                field: 'opt_name',
                align: 'center'
            },
            {
            	title: '操作模块',
                field: 'opt_key',
                align: 'center'
            },
            {
                title: '操作类型',
                field: 'opt_type',
                align: 'center',
                formatter:function(value,row,index){
                	var result = "";
                	switch(value){
	                	case "0":result="新增";break;
	                	case "1":result="删除";break;
	                	case "2":result="修改";break;
	                	case "3":result="查询";break;
                	}
                	return result;
                }
            },
            {
            	title: '操作类',
                field: 'class_name',
                align: 'center'
            },
            {
            	title: '操作方法',
                field: 'method_name',
                align: 'center'
            },
            {
            	title: '访问者ip',
                field: 'remote_ip',
                align: 'center'
            },
            {
            	title: '访问者主机名',
                field: 'remote_name',
                align: 'center'
            },
            {
            	title: '访问者端口',
                field: 'remote_port',
                align: 'center'
            },
            {
            	title: '请求标志符',
                field: 'req_uri',
                align: 'center'
            },
            {
            	title: '请求资源',
                field: 'req_url',
                align: 'center'
            },
            {
            	title: '操作者',
                field: 'creator',
                align: 'center'
            },
            {
            	title: '操作时间',
                field: 'create_time',
                align: 'center',
                formatter:function(value,row,index){
                	return dateFormat(value);
                }
            }
        ]
    });
});