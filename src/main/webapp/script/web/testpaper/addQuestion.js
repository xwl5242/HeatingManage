//点击"添加"按钮事件
function addQuestion(){
	//获取所选题目
	var rows = $("#addQ-table-list").bootstrapTable('getSelections');
	console.log(rows);
	if(!rows||rows.length==0){
		layer.msg("请选择至少一天题目进行添加操作!");
		return;
	}
	var ls = [];
	var questionType = $("#qType").val();
	var divH = getTableListDiv(questionType);
	var datas = parent.$("#"+divH).bootstrapTable('getData');
	var datasLength = datas.length;
	for(var i=0;i<rows.length;i++){
		var opt = '<a title="删除" class="btn btn-xs btn-danger" onclick="removeQues(&quot;'+questionType+'&quot;,&quot;'+rows[i].id+'&quot;)"><i class="fa fa-trash-o"></i></a>';
		var qo = {id:rows[i].id,title:rows[i].q_title,
				score:rows[i].q_score,selItem:rows[i].q_sel_item,
				answer:rows[i].q_answer,awkeyword:rows[i].q_aw_keyword,opt:opt};
		parent.$("#"+divH).bootstrapTable('insertRow', {index:(datasLength++), row:qo});
	}
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index); //再执行关闭
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