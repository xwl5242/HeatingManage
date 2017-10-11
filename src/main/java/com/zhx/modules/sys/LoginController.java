package com.zhx.modules.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhx.modules.common.BaseController;
import com.zhx.modules.common.Global;
import com.zhx.modules.constants.Const;
import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.service.UserService;
import com.zhx.modules.utils.DESUtils;

/**
 * 登录controller
 * @author xwl
 *
 */
@Controller
public class LoginController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param user
	 */
	@RequestMapping(value="/loginIn",method=RequestMethod.GET)
	@ResponseBody
	public void loginIn(HttpServletRequest request,HttpServletResponse response,User user){
		logger.info("user login,user info:"+user);
		try {
			if (Global.getProperty("uname").equals(user.getUserName())
					&& Global.getProperty("pwd").equals(
							DESUtils.encrypt(user.getPassword()))) {
				request.getSession().setAttribute(Const.SESSION_USER, user);//用户信息
				request.getSession().setAttribute(Const.SESSION_USER_NAME, user.getUserName());//用户名
				response.sendRedirect(request.getContextPath()+"/index");
			}else{
				response.sendRedirect(request.getContextPath());
			}
		} catch (Exception e) {
			
		}
		logger.info("login success,user info:"+user);
	}
	
	/**
	 * 用户退出登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/loginOut",method=RequestMethod.GET)
	@ResponseBody
	public void loginOut(HttpServletRequest request,HttpServletResponse response){
		request.getSession().removeAttribute(Const.SESSION_USER);
	}
	
	/**
	 * 跳转到首页
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response,User user){
		return "index";
	}
	
	
}
