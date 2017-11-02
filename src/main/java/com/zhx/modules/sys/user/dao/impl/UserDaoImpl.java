package com.zhx.modules.sys.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.zhx.modules.frames.BaseJdbcTemplate;
import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.dao.UserDao;
import com.zhx.modules.sys.user.service.impl.CreateSqlTools;

@Repository
public class UserDaoImpl extends BaseJdbcTemplate implements UserDao {

	@Override
	public int insertUser(User user) {
		String insertSql = CreateSqlTools.getCreateSql(user,User.class,"sys_user");
		return update(insertSql);
	}

	@Override
	public String selectPwdByUserCode(String userCode) {
		String sql = "select password from sys_user where user_code='"+userCode+"'";
		return super.queryForObject(sql, String.class);
	}
	
}
