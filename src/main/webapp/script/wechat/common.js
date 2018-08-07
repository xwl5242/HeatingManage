//表单校验
function formValidations(){
	var result = true;
	var inputs = $("form input");
	if(inputs){
		for(var i=0;i<inputs.length;i++){
			var value = $(inputs[i]).val();
			var desc = $(inputs[i]).attr('placeholder');
			if(!value){
				showMsg(desc);
				result = false;
			}
		}
	}
	return result;
}
//验证姓名，只能输入汉字
function validName(_this){
	var tel = /^[u4e00-u9fa5],{0,}$/;
	var pass = tel.test($(_this).val());
	if(!pass) showMsg("请输入汉字");
}
//验证手机号码
function validPhone(_this){
	var tel = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9])\d{8}$/;
	var pass = tel.test($(_this).val());
	if(!pass){
		showMsg("手机号码不正确，请重新输入");
		$(_this).val('');
		$(_this).focus();
	}else{
		hasMember(_this,"该手机号已经注册，请直接登录！");
	}
}
//验证身份证号
function validCid(_this){
	//获取身份证的值
	var code = $(_this).val();
	//如果身份证中存在x，将小写x转换为大写X
	code = code.replace('x','X');
	var pass = IdentityCodeValid(code);
	if(!pass){
		showMsg("身份证不正确，请重新输入");
		$(_this).val('');
		$(_this).focus();
	}else{
		hasMember(_this,"该身份证号已经注册，请直接登录！");
	}
}
function hasMember(_this,msg){
	$.ajax({
    	url:root+"/m/hasMember",
    	type:"post",
    	dataType:"json",
    	data:{
    		phoneOrCId:$(_this).val()
    	},
    	success:function(result){
    		if(result.code){
    			showMsg(msg);
    			$(_this).val('');
    			$(_this).focus();
    		}
    	}
    });
}
//弹框提示
function showMsg(desc){
	$("#realMsg").text(desc);
	$requiredMsg = $('#requiredMsg');
	$requiredMsg.fadeIn(200);
	$('#dialogs').on('click', '.weui-dialog__btn', function(){
	    $(this).parents('.js_dialog').fadeOut(200);
	});
}