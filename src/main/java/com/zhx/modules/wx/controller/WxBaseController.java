package com.zhx.modules.wx.controller;

import java.util.HashMap;
import java.util.Map;

import com.zhx.modules.constants.Const;
import com.zhx.modules.frames.BaseController;

public class WxBaseController extends BaseController {

	/**
	 * 封装微信所有操作反馈map，成功页、失败页、提示页
	 * @param optType 操作类型
	 * @param resultType 返回结果类型
	 * @param reqParam 必要参数，可有可无，特殊情况会用到
	 * @return
	 */
	public Map<String,Object> wxMsgMap(String optType,String resultType){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String prefix = "",subfix="",desc="";
		if(Const.WX_OPT_DEFAULT.equals(optType)){
			prefix = "操作";
		}else if(Const.WX_OPT_REGISTER.equals(optType)){
			prefix = "注册";
		}else if(Const.WX_OPT_UPDATE.equals(optType)){
			prefix = "更新";
		}else if(Const.WX_OPT_QUERY_EMPTY.equals(optType)){
			desc="没有此记录";
		}else if(Const.WX_OPT_OPEN_WXBRO.equals(optType)){
			desc="请在微信客户端打开，谢谢！";
		}
		if(Const.WX_RET_SUCCESS.equals(resultType)){
			subfix = "成功！";desc="恭喜，"+prefix+subfix+"请前往首页查看您的信息！";
		}else if(Const.WX_RET_FAIL.equals(resultType)){
			subfix = "失败！";desc="抱歉，"+prefix+subfix+"请返回首页重新操作，谢谢！";
		}
		resultMap.put("title", prefix+subfix);
		resultMap.put("desc", desc);
		return resultMap;
	}
	
}
