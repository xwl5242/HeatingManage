/**
 * 打开首页（index.jsp）左侧的菜单
 * @param menuId 菜单<a>标签的id
 * @returns {Boolean}
 */
function openMenuItem(menuId) {
	$this = $("#"+menuId);
    // 获取标识数据
    var dataUrl = $this.attr('href'),
        dataIndex = $this.data('index'),
        menuName = $.trim($this.text()),
        flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0)return false;

    // 选项卡菜单已存在
    $('.J_menuTab').each(function () {
        if ($this.data('id') == dataUrl) {
            if (!$this.hasClass('active')) {
                $this.addClass('active').siblings('.J_menuTab').removeClass('active');
                scrollToTab(this);
                // 显示tab对应的内容区
                $('.J_mainContent .J_iframe').each(function () {
                    if ($this.data('id') == dataUrl) {
                        $this.show().siblings('.J_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });

    // 选项卡菜单不存在
    if (flag) {
    	createNewMenuCommon(menuName,dataUrl,dataIndex);
    }
    return false;
}
/**
 * 系统菜单栏不存在要打开的页面菜单信息，这是公共方法，打开新的（系统菜单不存在的）菜单tab页
 */
function createNewMenuCommon(menuName,dataUrl,dataIndex,childern){
	var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
    if(childern){
    	parent.$('.J_menuTab').removeClass('active');
    }else{
    	$('.J_menuTab').removeClass('active');
    }

    // 添加选项卡对应的iframe
    var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
    if(childern){
    	parent.$('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
    }else{
    	$('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
    }
    if(childern){
    	parent.$('.J_menuTabs .page-tabs-content').append(str);
    }else{
    	$('.J_menuTabs .page-tabs-content').append(str);
    }
    return false;
}
/**
 * 关闭自己打开的菜单
 * @returns {Boolean}
 */
function closeMyTab() {
	var Jms = top.$('.J_menuTab');
	var _$JmActive = null;
	for(var i=0;i<Jms.length;i++){
		if($(Jms[i]).hasClass('active')){
			_$JmActive = $(Jms[i]);
		}
	}
    var closeTabId = _$JmActive.data('id');
    var currentWidth = _$JmActive.width();

    // 当前元素处于活动状态
    if (_$JmActive.hasClass('active')) {

        // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
        if (_$JmActive.next('.J_menuTab').size()) {

            var activeId = _$JmActive.next('.J_menuTab:eq(0)').data('id');
            _$JmActive.next('.J_menuTab:eq(0)').addClass('active');

            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.J_iframe').hide();
                    return false;
                }
            });

            var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
            if (marginLeftVal < 0) {
                $('.page-tabs-content').animate({
                    marginLeft: (marginLeftVal + currentWidth) + 'px'
                }, "fast");
            }

            //  移除当前选项卡
            _$JmActive.remove();

            // 移除tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }

        // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
        if (_$JmActive.prev('.J_menuTab').size()) {
            var activeId = _$JmActive.prev('.J_menuTab:last').data('id');
            _$JmActive.prev('.J_menuTab:last').addClass('active');
           top.$('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.J_iframe').hide();
                    return false;
                }
            });

            //  移除当前选项卡
            _$JmActive.remove();

            // 移除tab对应的内容区
            top.$('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }
    }
    // 当前元素不处于活动状态
    else {
        //  移除当前选项卡
        _$JmActive.remove();

        // 移除相应tab对应的内容区
        top.$('.J_mainContent .J_iframe').each(function () {
            if ($(this).data('id') == closeTabId) {
                $(this).remove();
                return false;
            }
        });
    }
    return false;
}