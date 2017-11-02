package com.zhx.modules.sys.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.dao.UserDao;
import com.zhx.modules.sys.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public int saveUser(User user) {
		int insertRet = userDao.insertUser(user);
		return 0;
	}

	@Override
	public String queryPwdByUserCode(String userCode) {
		
		return userDao.selectPwdByUserCode(userCode);
	}

}
