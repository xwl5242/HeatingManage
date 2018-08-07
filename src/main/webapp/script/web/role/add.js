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
	        roleName: {
	            required: true,
	            minlength: 2,
	            isName:true,
	            roleUnique:true
	        },
	        roleDesc: {
	            required: true,
	            minlength: 2,
	            isName:true
	        }
	    },
	    messages: {
	        roleName: {
	            required: icon + "请输入角色名称",
	            minlength: icon + "角色名称必须2个字符以上",
	            isName: icon + "角色名称是汉字或字母开头，汉字、字母和数字组合",
	            roleUnique:icon + "角色名称已存在"
	        },
	        roleDesc: {
	            required: icon + "请输入角色描述",
	            minlength: icon + "角色描述必须2个字符以上",
	            isName: icon + "角色描述是汉字或字母开头，汉字、字母和数字组合"
	        }
	    },
	    submitHandler:function(form){
		    $.ajax({
	        	url:root+"/role/add",
	        	type:"post",
	        	dataType:"json",
	        	data:$('#addform').serialize(),
	        	success:function(result){
	        		layer.msg("新增角色成功！");
					parent.$('#role-table-list').bootstrapTable("refresh");
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            		parent.layer.close(index); //再执行关闭  
	        	}
	        });
	    }
	});
	$.validator.addMethod("isName", function(value, element){
        var tel = /^[\u4e00-\u9fa5a-zA-Z][\u4e00-\u9fa5a-zA-Z\d]+$/;
        return this.optional(element) || (tel.test(value));
    }, icon + "内容是汉字、字母和数字组合");
	
	//角色名称唯一判断
	$.validator.addMethod("roleUnique", function(value, element) {
        var deferred = $.Deferred();//创建一个延迟对象
        $.ajax({
            url:root+'/role/roleUnique',
            type:'POST',
            async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
            data: {roleName:$('#roleName').val()},
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
    }, icon + "角色名称已存在");
});

