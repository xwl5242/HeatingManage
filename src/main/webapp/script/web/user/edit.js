$(function(){
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#addform").validate({
		onsubmit:true,// 是否在提交是验证
        //移开光标:如果有内容,则进行验证
        onfocusout: function (element) {
            if ($(element).val()==null||$(element).val()=="") {
                $(element).closest('.form-group').removeClass('has-error');
                $(element).siblings('.help-block').remove();
            }else{
                $(element).valid();
            }
        },
        onkeyup :false,// 是否在敲击键盘时验证
	    rules: {
	        userCode: {
	            required: true,
	            minlength: 6,
	            isUserCode:true,
	            loginUnique:true
	        },
	        password: {
	            required: true,
	            minlength: 8,
	            strongPwd:true
	        },
	        userName: {
	            required: true,
	            minlength: 6,
	            isName:true
	        },
	        nickName: {
	            minlength: 6,
	            isName:true
	        },
	        phone:{
	        	isPhone:true
	        },
	        mail:{
	        	email:true
	        }
	    },
	    messages: {
	        userCode: {
	            required: icon + "请输入您的登录名称",
	            minlength: icon + "登录名称必须6个字符以上",
	            isUserCode: icon + "登录名称必须是以字母开头，字母、数字和下划线的组合",
	            loginUnique: icon + "登录账号已存在"
	        },
	        password: {
	            required: icon + "请输入您的密码",
	            minlength: icon + "密码必须8个字符以上",
	            strongPwd: icon + "必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间"
	        },
	        userName: {
	            required: icon + "请输入您的用户名",
	            minlength: icon + "用户名必须6个字符以上",
	            isName: icon + "用户名必须是汉字、字母和数字组合"
	        },
	        nickName: {
	            minlength: icon + "用户昵称必须6个字符以上",
	            isName: icon + "用户昵称必须是汉字、字母和数字组合"
	        },
	        phone:{
	        	isPhone:icon + "手机号码格式不正确"
	        },
	        mail:{
	        	email:icon + "邮箱格式不正确"
	        }
	    },
	    submitHandler:function(form){
		    $.ajax({
	        	url:root+"/user/edit",
	        	type:"post",
	        	dataType:"json",
	        	data:$('#addform').serialize(),
	        	success:function(result){
	        		layer.msg("修改用户成功！");
					parent.$('#user-table-list').bootstrapTable("refresh");
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            		parent.layer.close(index); //再执行关闭  
	        	}
	        });
	    }
	});
	//用户名校验
	$.validator.addMethod("isUserCode", function(value, element){
        var tel = /^[a-zA-Z][a-zA-Z0-9_]{5,17}$/;
        return this.optional(element) || (tel.test(value));
    }, icon + "登录名称必须是以字母开头，字母、数字和下划线的组合");
	//密码校验
	$.validator.addMethod("strongPwd", function(value, element){
        var tel = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$/;
        return this.optional(element) || (tel.test(value));
    }, icon + "必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间");
	
	//用户名和昵称校验
	$.validator.addMethod("isName", function(value, element){
        var tel = /[^a-zA-Z]\w{1,17}$/;
        return this.optional(element) || (tel.test(value));
    }, icon + "登录名称必须是字母、数字和下划线的组合");
	
	$.validator.addMethod("isPhone", function(value, element){
        var tel = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        return this.optional(element) || (tel.test(value));
    }, icon + "手机号码格式不正确");
	
	//登录名称唯一判断
	$.validator.addMethod("loginUnique", function(value, element) {
        var deferred = $.Deferred();//创建一个延迟对象
        if(value!=oldUserCode){
        	$.ajax({
        		url:root+'/user/loginUnique',
        		type:'GET',
        		async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
        		data: {userCode:$('#userCode').val()},
        		dataType: 'json',
        		success:function(result) {
        			if (!result.code) {
        				deferred.reject();
        			} else {
        				deferred.resolve();
        			}
        		}
        	});
        	return deferred.state() == "resolved" ? true : false;
        }
        return true;
    }, icon + "登录账号已存在");
});

