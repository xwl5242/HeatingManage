$(function(){
	//点击"登录"
	$("#loginBtn").click(function(){
		//表单验证不通过
		if(!formValidations()) return;
		//通过执行登录
		$.ajax({
        	url:root+"/m/login",
        	type:"post",
        	dataType:"json",
        	data:$('#loginForm').serialize(),
        	success:function(result){
        		if(!result.code){
        			showMsg(result.msg);
        		}else{
        			window.location.href=root+"/wx/toIndex";
        		}
        	}
        });
	});
});