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
	        tpName: {
	            required: true,
	            tpNameUnique:true
	        },
	        tpTitle: {
	            required: true
	        },
	        tpRandomNumber:{
	        	maxNum:true
	        }
	    },
	    messages: {
	    	tpName: {
	            required: icon + "请输入试卷名称",
	            tpNameUnique:icon + "该试卷名称已存在"
	        },
	        tpTitle: {
	            required: icon + "请输入试卷标题"
	        },
	        tpRandomNumber:{
	        	maxNum : icon + "题目个数超过上限"
	        }
	    },
	    submitHandler:function(form){
	    	$.ajax({
            	url:root+"/testpaper/edit",
            	type:"post",
            	dataType:"json",
            	data:$('#addform').serialize(),
            	success:function(result){
            		selNum=0;
            		layer.msg("修改试卷成功！");
    				parent.$('#testpaper-table-list').bootstrapTable("refresh");
    				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            		parent.layer.close(index); //再执行关闭  
            	}
            });
	    }
	});
	
	$.validator.addMethod("maxNum", function(value, element){
		var maxNum = $("#maxValue").val();
        return value<=maxNum;
    }, icon + "题目个数超过总题目个数上限");
	//问题标题唯一判断
	$.validator.addMethod("tpNameUnique", function(value, element) {
        var deferred = $.Deferred();//创建一个延迟对象
        if(oldTpName!=value){
        	$.ajax({
        		url:root+'/testpaper/tpNameUnique',
        		type:'POST',
        		async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
        		data: {tpName:$('#tpName').val()},
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
    }, icon + "该试卷标题已存在");
	
	$("#tpIsrandom").change(function(){
		var tpIsrandom = $("#tpIsrandom").val();
		if(tpIsrandom){
			$("input[name='tpRandomNumber']").removeAttr('readonly');
		}else{
			$("input[name='tpRandomNumber']").attr('readonly','readonly');
		}
	});
});