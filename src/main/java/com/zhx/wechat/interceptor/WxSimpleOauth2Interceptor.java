package com.zhx.wechat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhx.modules.common.Global;
import com.zhx.modules.constants.Const;
import com.zhx.modules.member.bean.Member;

public class WxSimpleOauth2Interceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(WxSimpleOauth2Interceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
//		try {
//			// 是否在微信浏览器打开
//			if (!request.getHeader("user-agent").contains("MicroMessenger")
//					&& !request.getHeader("user-agent").contains("MQQBrowser")) {
//				logger.info("请在微信客户端打开！");
//				//请在微信客户端打开
//				response.sendRedirect(request.getContextPath()+"/wx/msg/wx_open_wxbro/prompt");
//				return false;
//			}
//			logger.info("已经在微信客户端打开");
//			HttpSession session = request.getSession();
//			WxMpUser member = (WxMpUser)session.getAttribute(WxSessionKeys.WX_USER);
//			Member wxMember = (Member)session.getAttribute(Const.SESSION_WX_MEMBER);
//			logger.info("session中的微信用户信息："+member);
//			logger.info("session中的登录用户信息："+wxMember);
//			if(null==member||null==wxMember){
//				response.sendRedirect(Global.getWxOauth2Url4RealUrl("index"));
//				return false;
//			}
//			return true;
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return false;
//		}
		
		try{
			HttpSession session = request.getSession();
			Member wxMember = (Member)session.getAttribute(Const.SESSION_WX_MEMBER);
			logger.info("session中的登录用户信息："+wxMember);
			if(null==wxMember){
				response.sendRedirect(request.getContextPath()+"/wx/login");
				return false;
			}
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}

}
