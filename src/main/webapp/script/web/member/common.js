/**
 * input框绑定focus和blur事件
 * @param inputId input框id
 * @param reg 正则表达式或者函数
 * @param isValid true：函数，false：正则表达式
 */
function bindEvent(inputId,reg,isValid){
	$("#"+inputId).focus(function(){
		var _this = this;
		$(_this).parent("div").siblings("label").hide();
	});
	$("#"+inputId).blur(function(){
		var _this = this;
		var success = $(_this).parent("div").siblings("div.input-flags");
		var error = $(_this).parent("div").siblings("div.error-tips-wrap");
		if(!$(_this).val()){
			$(_this).parent("div").siblings("label").show();
			$(error).removeClass("slideup");
			$(error).children("div").html("此项为必填项，请填写");
			$(error).children("div").show();
		}else{
			var flag = false;
			flag=isValid?reg($(_this).val()):reg.test($(_this).val());
			if(!flag){
				$(error).removeClass("slideup");
				$(error).children("div").text($(error).children("div").attr("title"));
				$(error).children("div").show();
			}else{
				var classname = $(error).attr('class');
				if(classname.indexOf("slideup")==-1){
					$(error).addClass("slideup");
				}
				$(success).children("div").show();
				
			}
		}
	});
}