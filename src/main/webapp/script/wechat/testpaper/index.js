$(function(){
	//初始化swiper
	renderSwiper();
	//更新页面底部，自定义footer的信息
	$("#pageSize").text(mySwiper.slides.length);
});
//单选框点击事件
var rightQIds = [];
function checkChange(_this){
	var qId = $(_this).attr('id').split("-")[0];//问题id
	var rightAnswer = $("#answer"+qId).val();//该问题正确的id
	var myAnswer = $("#ap"+$(_this).attr('id')).text();//考生选择的答案
	myAnswer = myAnswer.split(".")[0];//自己的答案
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
	//保存正确题目个数
	$("#successAnswerNum").val(rightQIds.length);
	mySwiper.slideNext();
}
//绘制swiper
function renderSwiper(){
	//定义swiper，并添加右滑结束事件
	mySwiper = new Swiper('.swiper-container',{
		on:{
			//左滑完成
			slidePrevTransitionStart:function(){
				//swiper当前活动页索引，从0开始
				var rindex = mySwiper.realIndex;
				$("#pageNo").text(rindex+1);
			},
			//右滑开始
			slideNextTransitionStart:function(){
				//swiper当前活动页索引，从0开始
				var rindex = mySwiper.realIndex;
			},
			//右滑完成
			slideNextTransitionEnd:function(){
				//swiper当前活动页索引，从0开始
				var rindex = mySwiper.realIndex;
				$("#pageNo").text(rindex+1);
			}
		}
	});
}
/**
 * 页面内容完全加载完毕后操作
 * 用来添加点击"交卷"阴影效果
 */
window.onload = function() {
    var myShade = document.getElementById("myShade");
    // 触摸
    myShade.ontouchstart = function() {
    	this.style.opacity = 0.4;
        this.style.backgroundColor = "#aaa";
    };
    // 停止触摸
    myShade.ontouchend = function() {
        this.style.opacity = 0;
        this.style.backgroundColor = "#eee";
    };
    //点击"交卷"按钮的提示
    $submitMsg = $('#submitMsg'),
	$('#dialogs').on('click', '.weui-dialog__btn', function(){
	    $(this).parents('.js_dialog').fadeOut(200);
	});
    //"交卷"按钮点击事件
	$('#myShade').on('click', function(){
	    $submitMsg.fadeIn(200);
	});
	$("#successBtn").click(function(){
		var questionAndAnswer = "";
		var allCheck = $("input:checked");
		if(allCheck){
			for(var i=0;i<allCheck.length;i++){
				var checkId = $(allCheck[i]).attr('id');
				var qId = checkId.split("-")[0];
				var ans = String.fromCharCode((65+Number(checkId.split("-")[1])));
				var seq = checkId.split("-")[2];
				questionAndAnswer+=qId+"@"+ans+"@"+seq+";";
			}
			questionAndAnswer=questionAndAnswer.substring(0,questionAndAnswer.length-1);
		}
		var san = $("#successAnswerNum").val();//成功答案个数
		var total = mySwiper.slides.length;//总题目个数
		$.ajax({
        	url:root+"/wx/submitMyTp/"+(san/total*100),
        	type:"post",
        	dataType:"json",
        	data:{
        		tpId:$("#tpId").val(),
        		questionAndAnswer:questionAndAnswer
        	},
        	success:function(result){
        		rightQIds=[];
        		if(result.code){
        			window.location.href=root+"/wx/examresult/"+(san/total*100);
        		}else{
        			window.location.href=root+"/wx/msg/wx_default/fail";
        		}
        	}
        });
	});
};