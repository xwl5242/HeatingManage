package com.zhx.modules.testpaper.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhx.modules.common.DBException;
import com.zhx.modules.question.service.QuestionService;
import com.zhx.modules.testpaper.bean.MemberTPAnswer;
import com.zhx.modules.testpaper.bean.MemberTPResult;
import com.zhx.modules.testpaper.bean.TestPaper;
import com.zhx.modules.testpaper.dao.TestPaperDao;
import com.zhx.modules.testpaper.service.TestPaperService;
import com.zhx.modules.utils.DateUtils;
import com.zhx.modules.utils.RandomUtil;
import com.zhx.modules.utils.UUIDGenerator;

@Service
public class TestPaperServiceImpl implements TestPaperService {
	
	@Autowired
	private TestPaperDao testPaperDao;
	
	@Autowired
	private QuestionService questionService;

	/**
	 * 查询试卷列表
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryList(Map<String, String> params) {
		return testPaperDao.selectList(params);
	}

	/**
	 * 新增试卷
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int saveTestPaper(TestPaper testpaper) throws Exception {
		int result = 0;
		try{
			testpaper.setId(UUIDGenerator.getUUID());
			String ispublish = testpaper.getIsPublish();
			ispublish = null==ispublish||"".equals(ispublish)?"0":ispublish;
			testpaper.setIsPublish(ispublish);
			testpaper.setCreateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			testpaper.setUpdateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			result = testPaperDao.insertTestPaper(testpaper);
			//随机派题
			if("1".equals(testpaper.getTpIsrandom()))
			testpaper2RandomQuestion(testpaper.getId(),Integer.valueOf(testpaper.getTpRandomNumber()));
			if(result==0){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 主键查询
	 */
	@Transactional(readOnly=true)
	@Override
	public TestPaper queryById(String id) {
		return testPaperDao.selectById(id);
	}

	/**
	 * 修改试卷
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int editTestPaper(TestPaper testpaper,boolean isRefreshQuestion) throws Exception {
		int result = 0;
		try{
			testpaper.setUpdateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			result = testPaperDao.updateTestPaper(testpaper);
			if(isRefreshQuestion){
				testpaper2RandomQuestion(testpaper.getId(),Integer.valueOf(testpaper.getTpRandomNumber()));
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
	 * 删除试卷
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean removeTestPapers(String id) throws Exception{
		boolean result = false;
		try{
			int c = testPaperDao.selectByTestPaperQuestionCount(id);
			int d = testPaperDao.deleteTestPaper(id);
			result=(d==c+1);
			if(!result){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 根据名称查询
	 */
	@Transactional(readOnly=true)
	@Override
	public TestPaper queryByTpName(String tpName) {
		return testPaperDao.selectByTpName(tpName);
	}

	/**
	 * 根据是否发布查询试卷
	 */
	@Transactional(readOnly=true)
	@Override
	public TestPaper queryByIsPublish(String isPublish) {
		return testPaperDao.selectByIsPublish(isPublish);
	}

	/**
	 * 发布试卷
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int publishTestPaper(String id,String status) throws Exception{
		int result = 0;
		try{
			result = testPaperDao.update4Publish(id,status);
			if(result==0){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 试卷分配题目
	 */
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public boolean grantTestPaper(String tpId,String qtMap) throws Exception{
		boolean result = false;
		try{
			Map<String,List<String>> mlist = new HashMap<String, List<String>>();
			String[] qts = qtMap.split(";");
			for(String qt:qts){
				String[] klist = qt.split("@");
				String k = klist[0];
				String[] ids = klist.length>1?klist[1].split(","):new String[]{};
				mlist.put(k, Arrays.asList(ids));
			}
			result = testPaperDao.updateTestPaper4Grant(tpId,mlist);
			if(!result){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 查询试卷和题目关联
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> queryTestPaperQuestion(String tpId) {
		return testPaperDao.selectTestPaperQuestion(tpId);
	}

	/**
	 * 预览前查询数据
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> preView(String qtMap,String tpId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] qts = qtMap.split(";");
		for(String qt:qts){
			String k = qt.split("@")[0];
			String list = qt.split("@")[1];
			String[] ids = list.split(",");
			List<Map<String,Object>> listMap = testPaperDao.select4PreView(k,ids);
			result.put("qType"+k, listMap);
		}
		TestPaper tp = testPaperDao.selectById(tpId);
		result.put("tp", tp);
		return result;
	}

	/**
	 * 根据试卷id和问题类型查询试卷和问题所有信息
	 */
	@Override
	public List<Map<String, Object>> queryTestPaperQuestionByIdType(String id,
			String type) {
		return testPaperDao.selectTestPaperQuestionByIdType(id,type);
	}

	/**
	 * 查询我的答题列表
	 */
	@Override
	public List<Map<String, Object>> queryMyTpList(String memberId) {
		return testPaperDao.selectMyTpList(memberId);
	}

	/**
	 * 微信或web端交卷
	 * @param questionAndAnswer 题目和我的答案的str（qId@myAnswer）
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean submitMyTp4WebOrWx(String mId,String tpId,
			String questionAndAnswer,String score) throws Exception {
		boolean result = false;
		int scount = 0;
		try{
			String[] qaList = questionAndAnswer.split(";");
			String st = DateUtils.date2yyyyMMddHHmmssStr(null);
			if(null!=qaList&&qaList.length>0){
				for(String qa:qaList){
					String id = UUIDGenerator.getUUID();
					String qId = qa.split("@")[0];
					String qAnswer = qa.split("@")[1];
					String seq = qa.split("@")[2];
					MemberTPAnswer mtpa = new MemberTPAnswer(id, mId, tpId, qId, qAnswer, st,seq);
					scount += testPaperDao.insertMyTp4WebOrWx(mtpa);
				}
				result = scount==qaList.length;
			}
			MemberTPResult mtpr = new MemberTPResult(UUIDGenerator.getUUID(), mId, tpId, score, st);
			int insertRet = testPaperDao.insertMyTpResult(mtpr);
			result = result && (insertRet==1);
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 查询我的答题，根据交卷时间
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryMyTpByMIdAndSubmitTime4WebOrWx(String id,
			String date) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String,Object>> tpqList = testPaperDao.selectMyTpByMIdAndSubmitTime4WebOrWx(id,date);
		if(null!=tpqList&&tpqList.size()>0){
			String tpId = tpqList.get(0).get("tp_id").toString();
			TestPaper tp = testPaperDao.selectById(tpId);
			result.put("tp",tp);
			result.put("tpqList", tpqList);
		}
		return result;
	}

	/**
	 * 外部接口，根据身份证查询是否答题
	 */
	@Override
	public int queryMyTpResultByCId4WebOrWx(String cId) {
		return testPaperDao.selectMyTpResultByCId4WebOrWx(cId);
	}

	/**
	 * 随机派题
	 * @param tpId
	 * @param number
	 * @throws DBException
	 */
	private void testpaper2RandomQuestion(String tpId,int number) throws DBException{
		if(number>0){
			//如果是随机派题，查询数据库中所有的题目，然后从题目中随机挑选出指定的个数
			List<Map<String,Object>> quesList = questionService.queryAllListByType("0");//根据类型查询，此类型的所有题目
			//获取随机数
			List<Integer> randomList = RandomUtil.getRandomNumberList(number, quesList.size());
			List<String> quesIdList = new ArrayList<String>();
			if(null!=quesList&&quesList.size()>0){
				for(Integer random:randomList){
					Map<String,Object> quesMap = quesList.get(random);
					String id = quesMap.get("id").toString();
					quesIdList.add(id);
				}
				int iret = testPaperDao.insertTpQuestion(tpId,quesIdList,"0");
				if(iret!=quesIdList.size()){
					throw new DBException();
				}
			}
		}
	}

	/**
	 * 根据题目类型查询该类型的题目的总个数
	 */
	@Override
	public int queryAllQuestionCountByType(String string) {
		return questionService.queryAllListByType(string).size();
	}
}
