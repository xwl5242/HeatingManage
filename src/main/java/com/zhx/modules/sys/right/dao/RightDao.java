package com.zhx.modules.sys.right.dao;

import java.util.List;
import java.util.Map;

import com.zhx.modules.sys.right.bean.Right;

public interface RightDao {

	/**
	 * 新建权限
	 * @param right
	 * @return
	 */
	int insertRight(Right right);

	/**
	 * 根据权限登录名查询权限信息
	 * @param rightName
	 * @return
	 */
	Right selectByRightName(String rightName);

	/**
	 * 分页查询权限列表
	 * @param params
	 * @return
	 */
	Map<String, Object> selectRightList(Map<String, String> params);

	/**
	 * 删除权限
	 * @param ids
	 * @return
	 */
	int deleteRights(String ids);

	/**
	 * 修改权限
	 * @param right
	 * @return
	 */
	int updateRight(Right right);

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	Right selectById(String id);

	/**
	 * 修改是否末端和icon
	 * @param id
	 * @param isLeaf
	 * @param rightCionDefault
	 * @return
	 */
	int updateLeafAndIcon(String id, String isLeaf, String icon);

	/**
	 * 根据父id查询
	 * @param pid
	 * @return
	 */
	List<?> selectByPid(String pid);

	/**
	 * 查询权限和角色关联个数
	 * @param ids
	 * @return
	 */
	int selectRoleRightCountByRightId(String ids);

}
