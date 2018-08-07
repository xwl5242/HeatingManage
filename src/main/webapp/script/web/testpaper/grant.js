$(function(){
	init();
});
//添加题目
function addQuestion(type){
	var datas = $("#"+getTableListDiv(type)).bootstrapTable('getData');
	var ids = [];
	for(var i=0;i<datas.length;i++){
		ids.push(datas[i].id);
	}
	if(ids.length==0) ids.push(-1);
	layer.open({
        area: ['1000px', '600px'],
        type: 2,
        title: '选择题目',
        skin: 'layer-ext-moon', //样式类名
        closeBtn: 1, //不显示关闭按钮
        shadeClose: false, //开启遮罩关闭
        content: root+'/testpaper/addQuestion/'+type+'/'+ids.join()
    });
}
//初始化

function init(){
	var q0=[],q1=[],q2=[],q3=[];
	var list = JSON.parse(tpqLists);
	for(var i=0;i<list.length;i++){
		var q_type = list[i].q_type;
		var tm = {id:list[i].id,title:list[i].q_title,score:list[i].q_score,
				answer:list[i].q_answer,selItem:list[i].q_sel_item,awkeyword:list[i].q_aw_keyword,
				opt:'<a title="删除" class="btn btn-xs btn-danger" onclick="removeQues(&quot;'+q_type+'&quot;,&quot;'+list[i].id+'&quot;)"><i class="fa fa-trash-o"></i></a>'};
		if(q_type==0){
			q0.push(tm);
		}else if(q_type==1){
			q1.push(tm);
		}else if(q_type==2){
			q2.push(tm);
		}else if(q_type==3){
			q3.push(tm);
		}
	}
	
	$("#singleSel-table-list").bootstrapTable({
      data: q0
    });
	$("#mutilSel-table-list").bootstrapTable({
		data: q1
	});
	$("#fillblank-table-list").bootstrapTable({
		data: q2
	});
	$("#answer-table-list").bootstrapTable({
		data: q3
	});
}
//删除题目
function removeQues(type,QuestionId){
	var ids=[];
	var tableDiv = getTableListDiv(type);
	if(QuestionId){
		ids.push(QuestionId);
	}else{
		var qIds = $("#"+tableDiv).bootstrapTable('getSelections');
		for(var i=0;i<qIds.length;i++){
			ids.push(qIds[i].id);
		}
	}
	$("#"+tableDiv).bootstrapTable('remove',{field:'id',values:ids});
}
//预览
//function preView(){
//	var qtMap = getMapData4AllTables();
//	$.ajax({
//        url: root + '/testpaper/preview',
//        type: 'get',
//        dataType: 'json',
//        data:{
//        	tpId:$("#tpId").val(),
//        	qtMap:qtMap
//        },
//        success: function (result) {
//        	if(result.code){
//        		window.qtMap = result.qtMap;
//        	}
//        	window.open(root+"/testpaper/preview/"+$("#tpId").val()+"/0");
//        }
//    });
//	
//}
//保存并关闭
function saveTQAndClose(){
	saveTQ();
	closeMyTab();
}
//保存
function saveTQ(){
	var qtMap = getMapData4AllTables();
	$.ajax({
        url: root + '/testpaper/grant',
        type: 'post',
        dataType: 'json',
        data:{
        	tpId:$("#tpId").val(),
        	qtMap:qtMap
        },
        success: function (result) {
        	if(result.code){
				layer.msg("操作成功！");
			}else{
				layer.msg(result.msg);
			}
        }
    });
}
//获取当前页面所有tableList的数据，封装成map
function getMapData4AllTables(){
	var qtMap = "";
	var tables = $("table[id$='-table-list']");
	for(var i=0;i<tables.length;i++){
		var tId = $(tables[i]).attr('id');
		var flag = tId.split("-")[0];
		var qType = null;
		switch(flag){
			case "singleSel":qType=0;break;
			case "mutilSel":qType=1;break;
			case "fillblank":qType=2;break;
			case "answer":qType=3;break;
		}
		var tDatas = $(tables[i]).bootstrapTable('getData');
		var qIds = [];
		for(var j=0;j<tDatas.length;j++){
			qIds.push(tDatas[j].id);
		}
		qtMap+=qType+"@"+qIds.join(",")+";";
	}
	qtMap = qtMap.substring(0,qtMap.length-1);
	return qtMap;
}
//取消
function closeTQ(){
	closeMyTab();
}
function getTableListDiv(type){
	var div = "";
	switch(type){
	case "0":div = "singleSel-table-list";break;
	case "1":div = "mutilSel-table-list";break;
	case "2":div = "fillblank-table-list";break;
	case "3":div = "answer-table-list";break;
	}
	return div;
}