totalPage = $('#totalPage').val();
currentPage=1;
//跳转到登录页面
function goingLogin(){
	$("#fade").show();
	$("#light").show();
	setTimeout(function(){
		$("#fade").hide();
		$("#light").hide();
		window.location.href=root+"/m/login";
	},1500);
}
//下一页 survey_nextpage
$(".survey_nextpage").click(function(){
	if(!member){
		goingLogin();
	}else{
		var cp = currentPage;//当前页
		var np = currentPage+1;//下一页
		//如果没有选择答案，红色提示，并阻断此次点击事件
		if(!isChecked()){
			alertTips();
			return;
		}
		if(np==totalPage){//如果下一页是最后一页
			$(".survey_prevpage").show();//前一页按钮显示
			$(".survey_nextpage").hide();//下一页按钮隐藏
			$(".survey_submit").show();//提交按钮显示
		}else{
			$(".survey_prevpage").show();//前一页按钮显示
			$(".survey_nextpage").show();//下一页按钮显示
		}
		$("#page"+cp).hide();//当前页div隐藏
		$("#page"+np).show();//下一页div显示
		//页码+1
		currentPage++;
	}
});
//上一页 survey_prevpage
$(".survey_prevpage").click(function(){
	var cp = currentPage;//当前页
	var pp = currentPage-1;//前一页
	//任何时候点击上一页，提交按钮隐藏和下一页按钮显示
	$(".survey_submit").hide();
	$(".survey_nextpage").show();//下一页按钮
	if(pp==0){//首页
		$(".survey_prevpage").hide();//前一页按钮显示
	}
	$("#page"+cp).hide();//当前页div隐藏
	$("#page"+pp).show();//前一页div显示
	//页码-1
	currentPage--;
});
//提交 survey_submit
$(".survey_submit").click(function(){
	//如果没有选择答案，红色提示，并阻断此次点击事件
	if(!isChecked()){
		alertTips();
		return;
	}
	//获取题目和答案信息 qId@qAnswer@seq
	var divs = $(".option_item");
	if(divs){
		var num=0;
		var questionAndAnswer = "";
		for(var i=0;i<divs.length;i++){
			if($(divs[i]).attr("aria-checked")=="true"){
				var qId = $(divs[i]).find("input").attr("id").split("_")[1];
				var ans = $(divs[i]).find("input").attr("data-oid");
				questionAndAnswer+=qId+"@"+ans+"@"+num+";";
				num++;
			}
		}
		questionAndAnswer=questionAndAnswer.substring(0,questionAndAnswer.length-1);
	}
	var total = $("div[id*='page']").length;
	var score = rightQIds.length/total*100;
	//提交到后台
	$.ajax({
    	url:root+"/m/submitMyTp/"+score,
    	type:"post",
    	dataType:"json",
    	data:{
    		tpId:$("#tpId").val(),
    		questionAndAnswer:questionAndAnswer
    	},
    	success:function(result){
    		currentPage=1;
    		rightQIds=[];
    		if(result.code){
    			var msg = result.msg;
    			var pre = msg.title;
    			var sub = msg.desc.replace('@',memberName);
    			$(".reg-succ").html(pre);
    			$(".qq").html(sub+",分数："+score);
    			$(".survey_main").hide();
    			$(".survey_control").hide();
    			$(".succ").show();
    		}else{
    			
    		}
    	}
    });
});
var rightQIds = [];
//所有单选按钮点击事件
$(".option_item > input").click(function(){
	var _this = this;//当前单选radio，当前选项
	//当前单选radio父标签div的同级所有div，就是除了当前选项
	var divs = $(_this).parent('div').siblings('div');
	//遍历修改状态
	for(var i=0;i<divs.length;i++){
		$(divs[i]).attr("tabindex","-1");//未选择
		$(divs[i]).attr("aria-checked","false");//未选择
	}
	$(_this).parent('div').attr("tabindex","0");//选择
	$(_this).parent('div').attr("aria-checked","true");//选择
	//清除必填提示语
	var _thisTip = $(_this).closest('div.survey_page').find("span.tips")[0];
	$(_thisTip).html('');
	//统计回答正确个数
	var qId = $(_this).attr('id').split("_")[1];//问题id
	var rightAnswer = $("#trueAnswer"+qId).val();//该问题正确的id
	var myAnswer = $(_this).attr('data-oid');//考生选择的答案
	myAnswer = hex_sha1(hex_md5(myAnswer+'ssdjz'));
	if(rightAnswer==myAnswer){//回答正确
		if($.inArray(qId,rightQIds)==-1){
			rightQIds.push(qId);
		}
	}else{//回答错误
		if($.inArray(qId,rightQIds)!=-1){
			rightQIds.splice($.inArray(qId,rightQIds),1);
		}
	}
});
//根据当前页码判断此页的题目是否回答
function isChecked(){
	var ischecked = false;
	var checks = $("#page"+currentPage).find("div.option_item");
	for(var i=0;i<checks.length;i++){
		if($(checks[i]).attr("aria-checked")=="true"){
			ischecked=true;break;
		}
	}
	return ischecked;
}
//题目必选提示
function alertTips(){
	var tip = $("#page"+currentPage).find("span.tips")[0];
	$(tip).html('<span class="tips_error" tabindex="0">这道题必须回答哦</span>');
}