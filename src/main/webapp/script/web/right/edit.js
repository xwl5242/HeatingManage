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
	        rightName: {
	            required: true,
	            minlength: 2,
	            isName:true,
	            rightUnique:true
	        },
	        rightDesc: {
	            required: true,
	            minlength: 2,
	            isName:true
	        },
	        rightUrl:{
	        	required: true,
	            minlength: 4
	        }
	    },
	    messages: {
	        rightName: {
	            required: icon + "请输入权限名称",
	            minlength: icon + "权限名称必须2个字符以上",
	            isName: icon + "权限名称必须是汉字",
	            rightUnique:icon + "权限名称已存在"
	        },
	        rightDesc: {
	            required: icon + "请输入权限描述",
	            minlength: icon + "权限描述必须2个字符以上",
	            isName: icon + "必须是汉字"
	        }
	    },
	    submitHandler:function(form){
		    $.ajax({
	        	url:root+"/right/edit",
	        	type:"post",
	        	dataType:"json",
	        	data:$('#addform').serialize(),
	        	success:function(result){
	        		layer.msg("修改权限成功！");
	        		parent.window.location.reload(); 
	        		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            		parent.layer.close(index); //再执行关闭  
	        	}
	        });
	    }
	});
	$.validator.addMethod("isName", function(value, element){
        var tel = /^([\u4E00-\u9FA5])+$/;
        return this.optional(element) || (tel.test(value));
    }, icon + "权限名称必须是汉字");
	
	//登录名称唯一判断
	$.validator.addMethod("rightUnique", function(value, element) {
        var deferred = $.Deferred();//创建一个延迟对象
        if(value!=oldRN){
        	$.ajax({
        		url:root+'/right/rightUnique',
        		type:'POST',
        		async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
        		data: {rightName:$('#rightName').val()},
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
    }, icon + "权限名称已存在");
});

