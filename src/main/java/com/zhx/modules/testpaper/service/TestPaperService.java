package com.zhx.modules.testpaper.service;

import java.util.List;
import java.util.Map;

import com.zhx.modules.testpaper.bean.TestPaper;

public interface TestPaperService {

	/**
	 * 查询试卷列表
	 * @param params
	 * @return
	 */
	Map<String, Object> queryList(Map<String, String> params);

	/**
	 * 新增试卷
	 * @param testpaper
	 * @param number,随机派题的个数，如果不等于-1，则证明是随机派题，否则就是手动派题
	 * @return
	 * @throws Exception
	 */
	int saveTestPaper(TestPaper testpaper) throws Exception;

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	TestPaper queryById(String id);

	/**
	 * 修改试卷
	 * @param testpaper
	 * @return
	 */
	int editTestPaper(TestPaper testpaper,boolean isRefreshQuestion) throws Exception;

	/**
	 * 删除试卷
	 * @param params
	 * @return
	 */
	boolean removeTestPapers(String id) throws Exception;

	/**
	 * 根据试卷名称查询
	 * @param tpName
	 * @return
	 */
	TestPaper queryByTpName(String tpName);
	
	/**
	 * 根据是否发布查询试卷
	 * @param isPublish
	 * @return
	 */
	TestPaper queryByIsPublish(String isPublish);

	/**
	 * 发布试卷
	 * @param id
	 * @return
	 */
	int publishTestPaper(String id,String status) throws Exception;

	/**
	 * 试卷分配题目
	 * @param params
	 * @return
	 */
	boolean grantTestPaper(String tpId,String qtMap) throws Exception;

	/**
	 * 查询试卷和题目关联
	 * @param tpId
	 * @return
	 */
	List<Map<String, Object>> queryTestPaperQuestion(String tpId);

	/**
	 * 预览前查询数据
	 * @param qtMap
	 * @return
	 */
	Map<String, Object> preView(String qtMap,String tpId);

	/**
	 * 根据试卷id和问题类型查询试卷和问题所有信息
	 * @param id
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> queryTestPaperQuestionByIdType(String id,
			String type);

	/**
	 * 查询我的答题列表
	 * @param memberId
	 * @return
	 */
	List<Map<String, Object>> queryMyTpList(String memberId);

	/**
	 * 微信或web端交卷
	 * @param questionAndAnswer
	 * @return
	 * @throws Exception
	 */
	boolean submitMyTp4WebOrWx(String mId,String tpId,
			String questionAndAnswer,String score) throws Exception;

	/**
	 * 查询我的答题，根据交卷时间
	 * @param id
	 * @param date
	 * @return
	 */
	Map<String, Object> queryMyTpByMIdAndSubmitTime4WebOrWx(String id,
			String date);

	/**
	 * 外部接口，根据身份证查询是否答题
	 * @param cId
	 * @return
	 */
	int queryMyTpResultByCId4WebOrWx(String cId);

	/**
	 * 根据题目类型查询该类型的题目的总个数
	 * @param string
	 * @return
	 */
	int queryAllQuestionCountByType(String string);

}
