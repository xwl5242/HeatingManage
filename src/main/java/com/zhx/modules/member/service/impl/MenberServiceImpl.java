package com.zhx.modules.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhx.modules.common.DBException;
import com.zhx.modules.member.bean.Member;
import com.zhx.modules.member.dao.MemberDao;
import com.zhx.modules.member.service.MemberService;
import com.zhx.modules.testpaper.bean.TestPaper;
import com.zhx.modules.testpaper.service.TestPaperService;
import com.zhx.modules.wx.service.WxService;

@Service
public class MenberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private WxService wxService;
	
	@Autowired
	private TestPaperService testPaperService;

	/**
	 * web前端登录操作
	 */
	@Transactional(readOnly=true)
	@Override
	public Member queryByUserName(String mUserName) {
		return memberDao.selectByUserName(mUserName);
	}

	/**
	 * 主键查询
	 */
	@Transactional(readOnly=true)
	@Override
	public Member queryById(String mId) {
		return memberDao.selectById(mId);
	}

	/**
	 * 获取默认的试卷
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String,Object> getDefaultTPInfo() {
		Map<String,Object> result = new HashMap<String, Object>();
		TestPaper tp = testPaperService.queryByIsPublish("1");
		result.put("tp",tp);
		List<Map<String,Object>> tpqList = testPaperService.queryTestPaperQuestion(tp.getId());
		//题目类型list
		List<String> qTypeList = new ArrayList<String>();
		Map<String,Object> tpqTypeMap = new HashMap<String, Object>();
		if(null!=tpqList&&tpqList.size()>0){
			for(Map<String,Object> tpqMap:tpqList){
				String qType = tpqMap.get("q_type").toString();
				if(!qTypeList.contains(qType)){
					qTypeList.add(qType);
				}
			}
			for(String qType:qTypeList){
				List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
				for(Map<String,Object> tpqMap:tpqList){
					String qt = tpqMap.get("q_type").toString();
					if(qType.equals(qt)){
						tList.add(tpqMap);
					}
				}
				tpqTypeMap.put(qType, tList);
			}
			result.put("qType", qTypeList);
			result.put("tpqTypeMap", tpqTypeMap);
		}
		return result;
	}

	/**
	 * 根据openid查询会员信息
	 */
	@Transactional(readOnly=true)
	@Override
	public Member queryByOpenId(String openId) {
		return memberDao.selectByOpenId(openId);
	}

	/**
	 * web端注册会员
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int registerMember4Web(Member member) throws Exception {
		int result = 0;
		try{
			Member cm = memberDao.selectByCid(member.getmCId());
			if(null!=cm){
				member.setId(cm.getId());
				result = memberDao.updateMember4WebRegister(member);
			}else{
				result = memberDao.insertMember(member);
			}
			if(result==0){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	
	/**
	 * 微信端注册会员
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int registerMember4Wx(Member member)  throws Exception{
		int result = 0;
		try{
			Member cm = memberDao.selectByCid(member.getmCId());
			if(null!=cm){
				member.setId(cm.getId());
				result = memberDao.updateMember4WxRegister(member);
			}else{
				result = memberDao.insertMember(member);
			}
			if(result==0){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 微信端更新会员信息
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public Member updateMember4Wx(Member member)  throws Exception{
		Member m = null;
		try{
			m =memberDao.updateMember4Wx(member);
			if(null==m){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return m;
	}

	/**
	 * 查询试卷内容
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryTestPaperAndQuestion4Index(String string) {
		return wxService.queryTestPaperAndQuestion4Index(string);
	}

	/**
	 * 保存会员的试卷信息
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean submitMyTp4Web(String id, String tpId,
			String questionAndAnswer,String score) throws Exception {
		boolean result = false;
		try{
			result = testPaperService.submitMyTp4WebOrWx(id,tpId,questionAndAnswer,score);
			if(!result) {
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 我的答题列表
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> queryMyTpList(String id) {
		return testPaperService.queryMyTpList(id);
	}

	/**
	 * 查询我的答题，根据交卷时间
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryMyTpByMIdAndSubmitTime4Web(String id,
			String date) {
		return testPaperService.queryMyTpByMIdAndSubmitTime4WebOrWx(id,date);
	}

	@Override
	public Member queryByPhoneOrCId(String phoneOrCId) {
		return memberDao.selectByPhoneOrCId(phoneOrCId);
	}
}
