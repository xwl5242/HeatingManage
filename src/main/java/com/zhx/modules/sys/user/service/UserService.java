package com.zhx.modules.sys.user.service;

import com.zhx.modules.sys.user.bean.User;

public interface UserService {

	int saveUser(User user);

	String queryPwdByUserCode(String userCode);

}
