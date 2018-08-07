package com.zhx.modules.sys.right.service;

import java.util.Map;

import com.zhx.modules.sys.right.bean.Right;

public interface RightService {

	/**
	 * 新建权限
	 * @param right
	 * @return
	 */
	int saveRight(Right right)  throws Exception;

	/**
	 * 根据权限登录名查询权限信息
	 * @param rightCode
	 * @return
	 */
	Right queryByRightName(String rightName);

	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	Map<String, Object> queryRightList(Map<String, String> params);

	/**
	 * 删除权限
	 * @param params
	 * @return
	 */
	boolean removeRights(Map<String, String> params) throws Exception;

	/**
	 * 修改权限
	 * @param right
	 * @return
	 */
	int editRight(Right right)  throws Exception;

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	Right queryById(String id);

}
