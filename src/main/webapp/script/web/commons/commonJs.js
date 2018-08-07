$(function(){
	var bodyHeight = $("body").height();
	$(".ibox-content").height(bodyHeight-105);
});
/**
 * list页面，列表上面可多行操作按钮的过滤部分
 * @param tableId table列表的id
 * @param isMutil 是否可以多选
 * @returns null：没有选择记录，rows：所选记录的data数据
 */
function layerCommon(tableId,isMutil){
	var rows = $('#'+tableId).bootstrapTable('getSelections');
	var layerFlag = isMutil?rows.length>=1:rows.length==1;
	var layerMsg = isMutil?'请选择至少一条记录进行操作！':'请选择一条记录进行操作！';
	if(!layerFlag){
		layer.msg(layerMsg);
		return null;
	}
	return rows;
}
/**
 * 日期时间格式化
 * @param date 日期long值
 * @returns {String} 日期字符串
 */
function dateFormat(date){
	date = new Date(date);
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var hh = date.getHours();
	var mi = date.getMinutes();
	var ss = date.getSeconds();
	return y+"-"+(m<10?("0"+m):m)+"-"+(d<10?("0"+d):d)+" "+(hh<10?("0"+hh):hh)+":"+(mi<10?("0"+mi):mi)+":"+(ss<10?("0"+ss):ss);
}
/**
 * 获取列表所选行的ID，格式'id1','id2','id3',...
 * @param listId table表格列表的id
 * @returns {String} ids字符串
 */
function getCheckValue4Ids(listId){
	var ids = "";
	var rows = $('#'+listId).bootstrapTable('getSelections');
	if(rows){
		for(i=0;i<rows.length;i++){
			ids += "'"+rows[i].id+"',";
		}
		ids = ids.substring(0,ids.length-1);
	}
	if(!ids){
		layer.msg("请选择要操作的用户！");
		return;
	}
	return ids;
}
/**
 * 设置table列表的高度
 * @param formId form表单的id
 * @returns {Number} 要设置的高度
 */
function getTableHeight(formId){
	var rowH = window.$(".ibox-content").height();
	var formH = window.$("#"+formId).height();
	return rowH-formH;
}
/**
 * 页面完全加载完成后的操作
 */
window.onload=function(){
	//页面中的搜索按钮
	$(".search").click(function(){
		$("table").bootstrapTable("refresh");
	});
	//页面中组织机构的搜索按钮
	$(".otree-search").click(function(){
		var sk = $(".otree-search").parent().siblings().children().val();
		$("#org-tree").jstree(true).search(sk);
	});
	//页面中的重置按钮
	window.$(".reset").click(function(){
		$("form").form("clear");
	});
	//页面中的导出excel按钮
	window.$(".export").click(function(){
		var params = $("form").serialize();
		var modules = $("#modules").val();
		window.location.href=root+"/"+modules+"/export?params="+params;
	});
	//打开的layer中的取消按钮
	window.$(".layer-cancel").click(function(){
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭  
	});
	//页面中的所有组织机构树
	window.$(".otree").orgTree({core:{multiple:false}});
}