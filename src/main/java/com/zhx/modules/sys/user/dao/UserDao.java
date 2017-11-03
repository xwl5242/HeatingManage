package com.zhx.modules.sys.user.dao;

import java.util.List;
import java.util.Map;

import com.zhx.modules.sys.user.bean.User;

public interface UserDao {

	/**
	 * 新建用户
	 * @param user
	 * @return
	 */
	int insertUser(User user);

	/**
	 * 根据用户登录名查询用户信息
	 * @param userCode
	 * @return
	 */
	User selectByUserCode(String userCode);

	/**
	 * 根据用户ID查询用户的所有权限信息
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> selectRights(String id);

}
