package com.zhx.modules.sys.user.dao;

import com.zhx.modules.sys.user.bean.User;

public interface UserDao {

	int insertUser(User user);

	String selectPwdByUserCode(String userCode);

}
