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
	        qType: {
	            required: true
	        },
	        qTitle: {
	            required: true,
	            titleUnique:true
	        },
	        qScore: {
	            required: true
	        },
	        qAnswer: {
	            required: true
	        }
	    },
	    messages: {
	        qType: {
	            required: icon + "请选择问题类型",
	        },
	        qTitle: {
	            required: icon + "请输入问题标题",
	            titleUnique:icon + "该问题标题已存在",
	        },
	        qScore: {
	            required: icon + "请输入问题分数"
	        },
	        qAnswer: {
	            required: icon + "请输入问题答案"
	        }
	    },
	    submitHandler:function(form){
	    	//提交表单前，整理选项，将多个选项以A.111;B.222;C.333格式保存提交
	    	var qSelItem = [];
	    	if($("#qType").val()<=1){
	    		var inputs = $("input[id*='qSelItem']");
	    		for(var i=0;i<inputs.length;i++){
	    			if($(inputs[i]).attr("id")!="qSelItem"){
	    				var subId = $(inputs[i]).attr("id").replace("qSelItem","");
	    				qSelItem.push(subId+"."+$(inputs[i]).val());
	    			}
	    		}
	    		$("#qSelItem").val(qSelItem.join(";"));
	    	}
	    	$.ajax({
            	url:root+"/question/edit",
            	type:"post",
            	dataType:"json",
            	data:$('#addform').serialize(),
            	success:function(result){
            		selNum=0;
            		layer.msg("修改问题成功！");
    				parent.$('#question-table-list').bootstrapTable("refresh");
    				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            		parent.layer.close(index); //再执行关闭  
            	}
            });
	    }
	});
	//问题标题唯一判断
	$.validator.addMethod("titleUnique", function(value, element) {
        var deferred = $.Deferred();//创建一个延迟对象
        if(oldQuestionTitle!=value){
        	$.ajax({
                url:root+'/question/titleUnique',
                type:'POST',
                async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
                data: {qTitle:$('#qTitle').val()},
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
    }, icon + "该问题标题已存在");
});

//记录选项个数
selNum=0;
/**
 * 问题类型下拉框，选择事件
 */
function selectQType(_this){
	var type = $(_this).val();//获取问题类型
	if(type<=1&&type!=''){//单选或多选
		qTypeSwitchOpt();
		$("#sel_up").after(renderSelDivHtml());
		$("#autoAnswerSmall").show();
	}else if(type==3){//问答
		qTypeSwitchOpt();
		$("#autoAnswerMax").show();
		$("#autoAwKeyword").show();
	}else if(type=2){//填空
		qTypeSwitchOpt();
		$("#autoAnswerSmall").show();
	}else{//全部
		qTypeSwitchOpt();
	}
}
/**
 * 获取动态添加的选项框
 * @returns {String} 动态添加的选项行的html
 * String.fromCharCode((65+inputId)) 将数字转为英文26个大写字母
 */
function renderSelDivHtml(value){
	value=(value?value:"");
	var inputId = selNum++;//没添加一次，个数加一
	return '<div name="autoAdd" class="row"><div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div>'+
    '<div class="col-sm-8 col-xs-8 col-md-8 col-lg-8"><div class="form-group">'+
    '<label class="control-label col-sm-4 col-xs-4 col-md-4 col-lg-4">选项'+String.fromCharCode((65+inputId))+'</label>'+//label：选项A|选项B...
    '<div class="col-sm-7 col-xs-7 col-md-7 col-lg-7"><div class="input-group">'+
    //添加的选项行，为选项行中的文本框、+按钮、×按钮设置id属性
    '<input type="text" value="'+value+'" id="qSelItem'+String.fromCharCode((65+inputId))+'" class="form-control"> <span class="input-group-btn">'+
    '<button type="button" id="addBtn'+String.fromCharCode((65+inputId))+'" onclick="addSel(this)" class="btn btn-success"><i class="fa fa-plus"></i></button>'+
    '<button type="button" id="removeBtn'+String.fromCharCode((65+inputId))+'" onclick="removeSel(this)" class="btn btn-default"><i class="fa fa-times"></i></button></span></div>'+
    '</div></div></div><div class="col-sm-2 col-xs-2 col-md-2 col-lg-2"></div></div>';
}
/**
 * 问题类型，下拉框，切换操作，切换时需要删除额外的元素
 */
function qTypeSwitchOpt(){
	selNum=0;//切换时，选项个数置0
	$("div[name='autoAdd']").remove();//自动添加的选项统一删除掉
	$("#autoAnswerMax").hide();//答案框删除掉
	$("#autoAnswerSmall").hide();//答案框删除掉
	$("#autoAwKeyword").hide();//答案关键字框删除掉
}
/**
 * 增加选择题选项，就是点击"+"按钮操作
 * @param _this "+"按钮dom对象
 */
function addSel(_this){
	//"+"按钮所在行后面添加一行
	$(_this).closest('.row').after(renderSelDivHtml());
	//"+"按钮被禁用
	$(_this).attr("disabled","disabled");
	//"×"按钮被禁用
	$(_this).siblings("button").attr("disabled","disabled");
	if(selNum>=5){
		$(".ibox-content").height($(".ibox-content").height()+54);
	}
}
/**
 * 删除选择题选项，就是点击"×"按钮操作
 * @param _this "×"按钮dom对象
 */
function removeSel(_this){
	//如果选项行为1，即只有一个选项时，不可再删除
	if($("div[name='autoAdd']").length==1){
		//删除不了
		layer.msg("选择题至少有一个选项！");
		return;
	}else{//还有多个选项
		selNum--;//选项行数减一
		$(_this).closest('.row').remove();//向上找最近的一个div，class属性是row的，即该选项所在行
		$("#addBtn"+String.fromCharCode((65+selNum-1))).removeAttr("disabled");//该选项上一个选项"+"按钮激活
		$("#addBtn"+String.fromCharCode((65+selNum-1))).siblings("button").removeAttr("disabled");//该选项上一个选项"×"按钮激活
		if(selNum>=4){
			$(".ibox-content").height($(".ibox-content").height()-54);
		}
	}
}