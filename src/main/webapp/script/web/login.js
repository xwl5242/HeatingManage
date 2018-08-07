$(function(){
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#backgroundform").validate({
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
	            minlength: 6
	        },
	        password: {
	            required: true,
	            minlength: 3
	        }
	    },
	    messages: {
	        userCode: {
	            required: icon + "请输入您的用户名",
	            minlength: icon + "用户名必须6个字符以上"
	        },
	        password: {
	            required: icon + "请输入您的密码",
	            minlength: icon + "密码必须3个字符以上"
	        }
	    },
	    submitHandler:function(){
            $.ajax({
                url:root+'/loginIn?' + Math.random(),
                data:$('form').serialize(),
                type:'POST',
                dataType:'json',
                success:function(result) {
                    if(result.code){
                        window.location.replace(root+"/index");
                    }else{
                        $('.login-error').remove();
                        $("#login").after('<span style="color: red;" class="help-block m-b-none login-error"><i class="fa fa-times-circle"></i> '+result.msg+'</span>');
                        $('.ver-code-img').click();
                    }
                }
            });
	    }
	});
});
