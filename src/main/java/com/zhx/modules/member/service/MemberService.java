package com.zhx.modules.member.service;

import java.util.List;
import java.util.Map;

import com.zhx.modules.member.bean.Member;

public interface MemberService {

	/**
	 * web前端登录操作
	 * @param getmUserName
	 * @return
	 */
	Member queryByUserName(String mUserName);

	/**
	 * 主键查询
	 * @param mId
	 * @return
	 */
	Member queryById(String mId);

	/**
	 * 获取默认的试卷
	 * @return
	 */
	Map<String,Object> getDefaultTPInfo();

	/**
	 * 根据openId查询会员信息
	 * @param openId
	 * @return
	 */
	Member queryByOpenId(String openId);

	/**
	 * 微信端注册会员
	 * @param member
	 * @return
	 */
	int registerMember4Wx(Member member) throws Exception;

	/**
	 * 微信端更新会员信息
	 * @param member
	 * @return
	 */
	Member updateMember4Wx(Member member) throws Exception;

	/**
	 * 查询试卷内容
	 * @param string
	 * @return
	 */
	Map<String, Object> queryTestPaperAndQuestion4Index(String string);

	/**
	 * web端注册会员
	 * @param member
	 * @return
	 * @throws Exception
	 */
	int registerMember4Web(Member member) throws Exception;

	/**
	 * 保存会员的试卷信息
	 * @param id
	 * @param tpId
	 * @param questionAndAnswer
	 * @return
	 */
	boolean submitMyTp4Web(String id, String tpId,
			String questionAndAnswer,String score) throws Exception;

	/**
	 * 我的答题列表
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> queryMyTpList(String id);

	/**
	 * 查询我的答题，根据交卷时间
	 * @param id
	 * @param date
	 * @return
	 */
	Map<String, Object> queryMyTpByMIdAndSubmitTime4Web(String id, String date);

	Member queryByPhoneOrCId(String phoneOrCId);

}
