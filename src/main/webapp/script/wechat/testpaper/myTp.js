$(function(){
	//初始化swiper
	renderSwiper();
	//更新页面底部，自定义footer的信息
	$("#pageSize").text(mySwiper.slides.length);
});
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
window.onload=function(){
	var iNum = $("i[class='weui-icon-cancel']");
	$("#failNum").text(iNum.length-1);
	$("#successNum").text(mySwiper.slides.length-iNum.length+1);
}