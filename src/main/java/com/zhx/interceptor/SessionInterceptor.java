package com.zhx.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhx.modules.constants.Const;
import com.zhx.modules.sys.user.bean.User;

/**
 * springMVC
 * @author xwl
 *
 */
public class SessionInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object arg2) throws Exception {
		User user = (User)req.getSession().getAttribute(Const.SESSION_USER);
		if(null==user){
			logger.info("user not login!redirect to login.jsp");
			res.sendRedirect(req.getContextPath());
			return false;
		}
		return true;
	}

}
