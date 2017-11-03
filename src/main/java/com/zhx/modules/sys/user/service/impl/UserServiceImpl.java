package com.zhx.modules.sys.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.zhx.modules.frames.InitTables;
import com.zhx.modules.sys.right.bean.Right;
import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.dao.UserDao;
import com.zhx.modules.sys.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 新建用户
	 */
	@Override
	public int saveUser(User user) {
		int insertRet = userDao.insertUser(user);
		return 0;
	}

	/**
	 * 根据用户登录名查询用户的信息
	 */
	@Override
	public User queryByUserCode(String userCode) {
		
		return userDao.selectByUserCode(userCode);
	}

	/**
	 * 根据用户ID查询用户的所有权限信息
	 */
	@Override
	public List<Map<String,Object>> queryRights(String id) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listmap = userDao.selectRights(id);
		if(null!=listmap&&listmap.size()>0){
			for(Map<String,Object> map:listmap){
				String rid = map.get("right_id").toString();
				result.addAll(InitTables.rightsMap.get(rid));
			}
		}
		return result;
	}

}
