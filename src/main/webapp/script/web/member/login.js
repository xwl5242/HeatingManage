$(function(){
	//绑定事件
	bindEvent("mName",function(v){return true},true);
	bindEvent("mPwd",function(v){return true},true);
});
//立即登录
$("#login").click(function(){
	$.post(root+'/m/login',$('form').serialize(),function(result){
		if(result.code){
            window.location.replace(root+"/m");
        }else{
        	var classname = $("#pwdError").attr('class');
			if(classname.indexOf("slideup")!=-1){
				$("#pwdError").removeClass("slideup");
				$("#pwdError > div").html(result.msg);
				$("#pwdError > div").show();
			}
        }
	},'json');
});