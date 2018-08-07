$(function(){
	//点击"获取验证码"
// 	$(".weui-vcode-btn").click(function(){
// 		var times = 59;
// 		getTimes(times);
// 	});
	
	//点击"注册"
	$("#registerBtn").click(function(){
		//表单验证不通过
		if(!formValidations()) return;
		//通过执行注册
		$.ajax({
        	url:root+"/wx/register",
        	type:"post",
        	dataType:"json",
        	data:$('#registerForm').serialize(),
        	success:function(result){
        		if(result.code){
        			window.location.href=root+"/wx/msg/wx_register/success";
        		}else{
        			window.location.href=root+"/wx/msg/wx_register/fail";
        		}
        	}
        });
	});
});
//获取验证码，秒倒计时
function getTimes(times){
	if(times==0){
		$(".weui-vcode-btn").removeAttr("disabled");
		$(".weui-vcode-btn").text("获取验证码");
		return;
	}
	$(".weui-vcode-btn").attr({"disabled":"disabled"});
	$(".weui-vcode-btn").text((times--)+"s");
	setTimeout(function(){
		getTimes(times);
	},1000);
}