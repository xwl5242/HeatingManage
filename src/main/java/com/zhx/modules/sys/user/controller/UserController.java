package com.zhx.modules.sys.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.service.UserService;

@Controller
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/user",method=RequestMethod.POST)
	@ResponseBody
	public void addUser(User user){
		logger.info("addUser start:"+user);
		int addRet = userService.saveUser(user);
		logger.info("addUser end");
	}
	
}
