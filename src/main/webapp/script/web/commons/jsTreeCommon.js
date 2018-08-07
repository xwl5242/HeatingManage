/**
 * json树组件
 * @param $
 */
(function ($) {
    $.fn.extend({
        "orgTree": function (options) {
        	if (!isValid(options))
                return this;
        	myPlugins.push(options.plugins);
        	var cores = options.core;
        	for(opt in cores){
        		myCore[opt] = cores[opt];
        	}
            var opts = $.extend({}, defaults, {core:myCore,plugins:myPlugins}); //使用jQuery.extend 覆盖插件默认参数
            return this.jstree(opts);
        }});
    	var defaults={
    			plugins: myPlugins,
                types: {
                    'default': {
                        'icon': 'fa fa-folder'
                    },
                    'html': {
                        'icon': 'fa fa-file-code-o'
                    },
                    'svg': {
                        'icon': 'fa fa-file-picture-o'
                    },
                    'css': {
                        'icon': 'fa fa-file-code-o'
                    },
                    'img': {
                        'icon': 'fa fa-file-image-o'
                    },
                    'js': {
                        'icon': 'fa fa-file-text-o'
                    }

                },
                core: myCore
    	}
    	var myPlugins = ['types','search'];
    	var myCore = {
    			data:{
    				url:root+"/right/orgTree",
    				dataType:"json"	
    			},
    			checkbox: {
                    keep_selected_style: false,//是否默认选中
                    three_state: false,//父子级别级联选择
                    tie_selection: false
                }
    	}
    	//私有方法，检测参数是否合法
        function isValid(options) {
            return !options || (options && typeof options === "object") ? true : false;
        }
})(jQuery);