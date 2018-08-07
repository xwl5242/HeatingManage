package com.zhx.modules.sys.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhx.modules.common.DBException;
import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.sys.user.dao.UserDao;
import com.zhx.modules.sys.user.service.UserService;
import com.zhx.modules.utils.DESUtils;
import com.zhx.modules.utils.DateUtils;
import com.zhx.modules.utils.IdsUtil;
import com.zhx.modules.utils.UUIDGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 新建用户
	 */
	@Override
	public int saveUser(User user) throws Exception{
		int insertRet = 0;
		try {
			user.setPassword(DESUtils.encrypt(user.getPassword()));
			user.setId(UUIDGenerator.getUUID());
			user.setIsDel("0");
			user.setAge("25");
			user.setType("1");
			user.setCreateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			user.setUpdateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			insertRet = userDao.insertUser(user);
			if(insertRet==0){
				throw new DBException();
			}
		} catch (Exception e) {
			throw e;
		}
		return insertRet;
	}
	
	/**
	 * 修改用户
	 */
	@Override
	public int editUser(User user) throws Exception{
		int insertRet = 0;
		try {
			user.setPassword(DESUtils.encrypt(user.getPassword()));
			user.setUpdateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			insertRet = userDao.updateUser(user);
			if(insertRet==0){
				throw new DBException();
			}
		} catch (Exception e) {
			throw e;
		}
		return insertRet;
	}

	/**
	 * 根据用户登录名查询用户的信息
	 */
	@Transactional(readOnly=true)
	@Override
	public User queryByUserCode(String userCode) {
		
		return userDao.selectByUserCode(userCode);
	}

	/**
	 * 查询用户分页信息
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryUserList(Map<String, String> params) {
		Map<String, Object> ret = userDao.selectUserList(params);
		try {
			if(null!=ret){
				//拿到结果集中的用户列表数据
				List<Map<String,Object>> listMap = (List<Map<String, Object>>) ret.get("rows");
				List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
				if(null!=listMap&&listMap.size()>0){
					//将密码字段换成明文
					for(Map<String,Object> map:listMap){
						String miwen = null==map.get("password")?"":map.get("password").toString();
						String mp = DESUtils.decrypt(miwen);
						map.remove("password");
						map.put("password", mp);
						result.add(map);
					}
					ret.remove("rows");
					ret.put("rows", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 启用或禁用用户
	 */
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public boolean updateUseStatus(Map<String, String> params) throws Exception{
		boolean result = false;
		try {
			String ids = params.get("ids");
			String status = params.get("status");
			int uret = userDao.updateUseStatusByIds(ids,status);
			result = (uret==ids.split(",").length);
			if(!result) throw new DBException();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 重置密码
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean resetPwd(Map<String, String> params) throws Exception{
		boolean result = false;
		try {
			String ids = params.get("ids");
			int uret = userDao.update4ResetPwd(ids);
			result = (uret==ids.split(",").length);
			if(!result) throw new DBException();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 删除用户
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean removeUsers(Map<String, String> params) throws Exception{
		boolean result = false;
		try{
			String ids = params.get("ids");
			int count = userDao.selectUserRoleCount(ids);
			int des = userDao.deleteUsers(ids);
			result = (des==IdsUtil.idsStrTrans4Array(ids).length+count);
			if(!result) throw new DBException();
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 导出用户
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> exportUser(Map<String, String> params) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		try {
			List<Map<String,Object>> listMap = userDao.selectUsers4Export(params);
			if(null!=listMap&&listMap.size()>0){
				//将密码字段换成明文
				for(Map<String,Object> map:listMap){
					String miwen = null==map.get("password")?"":map.get("password").toString();
					String mp = DESUtils.decrypt(miwen);
					map.remove("password");
					map.put("password", mp);
					result.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用户的角色授权
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean grantUser(String userId, String[] roleIds) throws Exception{
		boolean result = false;
		try{
			int u = userDao.updateUserRole(userId,roleIds);
			int c = userDao.selectUserRoleCount(userId);
			result = (u==c+roleIds.length);
			if(!result) throw new DBException();
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	
	/**
	 * 根据userId查询用户信息
	 */
	@Transactional(readOnly=true)
	@Override
	public User queryById(String id) {
		return userDao.selectById(id);
	}

	/**
	 * 根据userId查询用户角色关联
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Map<String,Object>> queryUserRoleByUserId(String id) {
		return userDao.selectUserRoleByUserId(id);
	}

}
