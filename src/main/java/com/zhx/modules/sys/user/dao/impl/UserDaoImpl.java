package com.zhx.modules.sys.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhx.modules.frames.BaseJdbcTemplate;
import com.zhx.modules.frames.InitTables;
import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.dao.UserDao;
import com.zhx.modules.sys.user.service.impl.CreateSqlTools;

@Repository
public class UserDaoImpl extends BaseJdbcTemplate implements UserDao {
	
	/**
	 * 新建用户
	 */
	@Override
	public int insertUser(User user) {
		String insertSql = CreateSqlTools.getCreateSql(user,User.class,"sys_user");
		return update(insertSql);
	}

	/**
	 * 根据用户登录名称查询用户信息
	 */
	@Override
	public User selectByUserCode(String userCode) {
		String select_column = InitTables.columnsStrMap4LowerCase.get("sys_user");
		String sql = "select "+select_column+" from sys_user where user_code='"+userCode+"'";
		return super.queryForObject4Custom(sql, User.class);
	}

	/**
	 * 根据用户ID查询用户的所有权限信息
	 */
	@Override
	public List<Map<String, Object>> selectRights(String id) {
		String pSql = "select right_id from sys_role_right where role_id in (select role_id from sys_user_role ur "
				+ "LEFT JOIN sys_role r on ur.role_id=r.id where user_id = '"+id+"' and r.is_del='0')";
		return queryForList(pSql);
	}
	
}
