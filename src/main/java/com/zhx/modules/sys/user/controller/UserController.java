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
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/",method=RequestMethod.POST)
	@ResponseBody
	public void addUser(User user){
		logger.info("addUser start:"+user);
		int addRet = userService.saveUser(user);
		logger.info("addUser end");
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String userList(){
		return "user/list";
	}
	
}
