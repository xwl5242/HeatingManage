package com.zhx.modules.sys.user.service;

import java.util.List;
import java.util.Map;

import com.zhx.modules.sys.right.bean.Right;
import com.zhx.modules.sys.user.bean.User;

public interface UserService {

	/**
	 * 新建用户
	 * @param user
	 * @return
	 */
	int saveUser(User user);

	/**
	 * 根据用户登录名查询用户信息
	 * @param userCode
	 * @return
	 */
	User queryByUserCode(String userCode);

	/**
	 * 查询用户的所有权限信息
	 * @param id 用户id
	 * @return
	 */
	List<Map<String,Object>> queryRights(String id);

}
