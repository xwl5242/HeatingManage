package com.zhx.modules.wx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhx.modules.common.DBException;
import com.zhx.modules.member.bean.Member;
import com.zhx.modules.member.service.MemberService;
import com.zhx.modules.sys.dic.service.DicService;
import com.zhx.modules.testpaper.bean.TestPaper;
import com.zhx.modules.testpaper.service.TestPaperService;
import com.zhx.modules.wx.service.WxService;

@Service
public class WxServiceImpl implements WxService {
	
	@Autowired
	private TestPaperService tpService;
	
	@Autowired
	private DicService dicService;
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 试卷首页面数据，查询试卷信息
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryTestPaperAndQuestion4Index(String type) {
		Map<String,Object> result = new HashMap<String, Object>();
		TestPaper tp = tpService.queryByIsPublish("1");
		result.put("tp",tp);
		List<Map<String,Object>> tpqList = tpService.queryTestPaperQuestionByIdType(tp.getId(),type);
		result.put("tpqList", tpqList);
		return result;
	}

	/**
	 * 根据openid查询会员信息
	 */
	@Transactional(readOnly=true)
	@Override
	public Member queryMemberByOpenId(String openId) {
		return memberService.queryByOpenId(openId);
	}

	/**
	 * 微信端注册会员
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int registerMember4Wx(Member member) throws Exception{
		return memberService.registerMember4Wx(member);
	}

	/**
	 * 微信端更新会员信息
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public Member updateMember4Wx(Member member) throws Exception {
		return memberService.updateMember4Wx(member);
	}

	/**
	 * 查询我的答题列表
	 */
	@Override
	public List<Map<String, Object>> queryMyTpList(String memberId) {
		return tpService.queryMyTpList(memberId);
	}

	/**
	 * 微信交卷
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean submitMyTp4Wx(String mId,String tpId,
			String questionAndAnswer,String score) throws Exception{
		boolean result = false;
		try{
			result = tpService.submitMyTp4WebOrWx(mId,tpId,questionAndAnswer,score);
			if(!result) throw new DBException();
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 查询我的答题，根据交卷时间
	 */
	@Override
	public Map<String, Object> queryMyTpByMIdAndSubmitTime4Wx(String id,
			String date) {
		return tpService.queryMyTpByMIdAndSubmitTime4WebOrWx(id,date);
	}

	/**
	 * 外部接口，根据身份证查询是否答题
	 */
	@Override
	public boolean isPassed(String cId) {
		int count = tpService.queryMyTpResultByCId4WebOrWx(cId);
		return count>0?true:false;
	}
}
