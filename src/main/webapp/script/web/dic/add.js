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
	        dcName: {
	            required: true,
	            dicNameUnique:true
	        },
	        dcDesc: {
	            required: true
	        },
	        dcK: {
	            required: true
	        },
	        dcV: {
	        	required: true
	        }
	    },
	    messages: {
	        dcName: {
	            required: icon + "请输入字典名称",
	            dicNameUnique: icon + "字典名称已存在"
	        },
	        dcDesc: {
	            required: icon + "请输入字典描述"
	        },
	        dcK: {
	            required: icon + "请输入字典key值"
	        },
	        dcV: {
	        	required: icon + "请输入字典值"
	        }
	    },
	    submitHandler:function(form){
		    $.ajax({
	        	url:root+"/dic/add",
	        	type:"post",
	        	dataType:"json",
	        	data:$('#addform').serialize(),
	        	success:function(result){
	        		layer.msg("新增用户成功！");
					parent.$('#dic-table-list').bootstrapTable("refresh");
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            		parent.layer.close(index); //再执行关闭  
	        	}
	        });
	    }
	});
	
	//字典名称唯一判断
	$.validator.addMethod("dicNameUnique", function(value, element) {
        var deferred = $.Deferred();//创建一个延迟对象
    	$.ajax({
    		url:root+'/dic/dicNameUnique',
    		type:'GET',
    		async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
    		data: {dicName:$('#dcName').val()},
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
    }, icon + "字典名称已存在");
});