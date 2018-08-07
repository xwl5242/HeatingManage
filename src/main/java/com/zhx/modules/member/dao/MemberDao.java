package com.zhx.modules.member.dao;

import com.zhx.modules.member.bean.Member;

public interface MemberDao {

	/**
	 * web前端登录操作
	 * @param mUserName
	 * @return
	 */
	Member selectByUserName(String mUserName);

	/**
	 * 主键查询
	 * @param mId
	 * @return
	 */
	Member selectById(String mId);

	/**
	 * 根据openId获取会员信息
	 * @param openId
	 * @return
	 */
	Member selectByOpenId(String openId);

	int insertMember(Member member);

	Member updateMember4Wx(Member member);

	Member selectByCid(String getmCId);

	int updateMember4WxRegister(Member member);

	int updateMember4WebRegister(Member member);

	Member selectByPhoneOrCId(String phoneOrCId);

}
