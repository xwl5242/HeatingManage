$(function(){
	//昵称输入框绑定事件
	bindEvent("mName",/^[a-zA-Z][a-zA-Z0-9_]{5,17}$/,false);
	//身份证输入框绑定事件
	bindEvent("mCId",IdentityCodeValid,true);
	//手机输入框绑定事件
	bindEvent("mPhone",/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9])\d{8}$/,false);
	//地址输入框绑定事件，随便填写
	bindEvent("mAddress",function(v){return true},true);
});
//注册按钮点击
function webRegister(){
	if(!validForm()){
		$.post(root+'/m/register',$('form').serialize(),function(result){
			if(result.code){//注册成功
				$(".form").hide();
				$(".succ").show();
			}else{//注册失败
				$(".form").hide();
				$(".error").show();
			}
		},'json');
	}
}
//表单验证
function validForm(){
	var result = null;
	var inputs = $(".input-outer > input");
	for(var i=0;i<inputs.length;i++){
		var _this = $(inputs[i]);
		var error = $(_this).parent("div").siblings("div.error-tips-wrap");
		if(!$(_this).val()){
			$(_this).parent("div").siblings("label").show();
			$(error).removeClass("slideup");
			$(error).children("div").html("此项为必填项，请填写");
			$(error).children("div").show();
			result = $(_this).attr("id");
			break;
		}
	}
	return result;
}
function gotoRegister(){
	$("form").show();
	$(".error").hide();
}
//密码输入框获取焦点
$("#mPwd").focus(function(){
	var _this = this;
	$(_this).parent("div").siblings("label").hide();
});
//密码输入框失去焦点
$("#mPwd").blur(function(){
	var _this = this;
	var success = $(_this).parent("div").siblings("div.input-flags");
	var error = $(_this).parent("div").siblings("div.error-tips-wrap");
	if(!$(_this).val()){
		$(_this).parent("div").siblings("label").show();
		$(error).removeClass("slideup");
		$(error).children("div").html("此项为必填项，请填写");
		$(error).children("div").show();
	}else{
		var flag = /(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{8,16}$/.test($(_this).val());
		if(!flag){
			$("#pwdInfo").removeClass("slideup");
		}else{
			var classname = $("#pwdInfo").attr('class');
			if(classname.indexOf("slideup")==-1){
				$("#pwdInfo").addClass("slideup");
			}
			$(error).children("div").html('');
			$(success).children("div").show();
		}
	}
});

function hasMember(_this,desc){
	$.ajax({
    	url:root+"/m/hasMember",
    	type:"post",
    	dataType:"json",
    	data:{
    		phoneOrCId:$(_this).val()
    	},
    	success:function(result){
    		if(result.code){
    			var success = $(_this).parent("div").siblings("div.input-flags");
    			$(success).children("div").hide();
    			$(_this).val('');
    			$(_this).parent("div").siblings("label").show();
    			var error = $(_this).parent("div").siblings("div.error-tips-wrap");
    			$(error).removeClass("slideup");
    			$(error).children("div").html(desc);
    			$(error).children("div").show();
    		}
    	}
    });
}