package com.zhx.modules.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhx.modules.common.BaseController;
import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.service.UserService;

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
	
	@RequestMapping(value="loginIn",method=RequestMethod.GET)
	@ResponseBody
	public void loginIn(HttpServletRequest request,HttpServletResponse response,User user){
		logger.info("user login,user info:"+user);
		logger.info("login success,user info:"+user);
	}
}
