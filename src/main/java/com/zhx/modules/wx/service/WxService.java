package com.zhx.modules.wx.service;

import java.util.List;
import java.util.Map;

import com.zhx.modules.member.bean.Member;

public interface WxService {

	/**
	 * 试卷首页面数据，查询试卷信息
	 * @param type
	 * @return
	 */
	Map<String, Object> queryTestPaperAndQuestion4Index(String type);

	/**
	 * 根据openid查询会员信息
	 * @param openId
	 * @return
	 */
	Member queryMemberByOpenId(String openId);

	/**
	 * 微信端注册会员
	 * @param member
	 * @return
	 * @throws Exception
	 */
	int registerMember4Wx(Member member) throws Exception;

	/**
	 * 微信端更新会员信息
	 * @param member
	 * @return
	 * @throws Exception
	 */
	Member updateMember4Wx(Member member) throws Exception;

	/**
	 * 跳转到我的答题页面
	 * @return
	 */
	List<Map<String, Object>> queryMyTpList(String memberId);

	/**
	 * 交卷
	 * @param questionAndAnswer
	 * @param score
	 * @return
	 */
	boolean submitMyTp4Wx(String mId,String tpId,String questionAndAnswer,String score) throws Exception;

	/**
	 * 查询我的答题，根据交卷时间
	 * @param id
	 * @param date
	 * @return
	 */
	Map<String, Object> queryMyTpByMIdAndSubmitTime4Wx(String id, String date);

	/**
	 * 外部接口，根据身份证查询是否答题
	 * @param cId
	 * @return
	 */
	boolean isPassed(String cId);

}
