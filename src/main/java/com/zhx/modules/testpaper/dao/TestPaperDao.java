package com.zhx.modules.testpaper.dao;

import java.util.List;
import java.util.Map;

import com.zhx.modules.testpaper.bean.MemberTPAnswer;
import com.zhx.modules.testpaper.bean.MemberTPResult;
import com.zhx.modules.testpaper.bean.TestPaper;

public interface TestPaperDao {

	/**
	 * 查询试卷列表
	 * @param params
	 * @return
	 */
	Map<String, Object> selectList(Map<String, String> params);

	/**
	 * 新增试卷
	 * @param testpaper
	 * @return
	 */
	int insertTestPaper(TestPaper testpaper);

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	TestPaper selectById(String id);

	/**
	 * 修改试卷
	 * @param testpaper
	 * @return
	 */
	int updateTestPaper(TestPaper testpaper);

	/**
	 * 查询试卷和问题关联个数
	 * @return
	 */
	int selectByTestPaperQuestionCount(String id);

	/**
	 * 删除试卷
	 * @param id
	 * @return
	 */
	int deleteTestPaper(String id);

	/**
	 * 根据名称查询试卷
	 * @param tpName
	 * @return
	 */
	TestPaper selectByTpName(String tpName);
	
	/**
	 * 根据是否发布查询试卷
	 * @param isPublish
	 * @return
	 */
	TestPaper selectByIsPublish(String isPublish);

	int update4Publish(String id,String status);

	/**
	 * 试卷分配题目
	 * @param tpId
	 * @param questionAndTypeMap
	 * @return
	 */
	boolean updateTestPaper4Grant(String tpId,Map<String,List<String>> qtList);

	/**
	 * 查询试卷和题目关联
	 * @param tpId
	 * @return
	 */
	List<Map<String, Object>> selectTestPaperQuestion(String tpId);

	/**
	 * 预览前查询数据
	 * @param k
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> select4PreView(String k, String[] ids);

	/**
	 * 根据试卷id和问题类型查询试卷和问题所有信息
	 * @param id
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> selectTestPaperQuestionByIdType(String id,
			String type);

	/**
	 * 查询我的答题列表
	 * @param memberId
	 * @return
	 */
	List<Map<String, Object>> selectMyTpList(String memberId);

	/**
	 * 微信或web端交卷
	 * @param qaList
	 * @return
	 */
	int insertMyTp4WebOrWx(MemberTPAnswer mtpa);

	/**
	 * 查询我的答题，根据交卷时间
	 * @param id
	 * @param date
	 */
	List<Map<String,Object>> selectMyTpByMIdAndSubmitTime4WebOrWx(String id, String date);

	/**
	 * 外部接口，根据身份证查询是否答题
	 * @param cId
	 */
	int selectMyTpResultByCId4WebOrWx(String cId);

	/**
	 * 新增试卷和题目关联
	 * @param id 试卷id
	 * @param quesIdList 题目id集合
	 * @param string 题目类型
	 * @return
	 */
	int insertTpQuestion(String id, List<String> quesIdList, String string);

	/**
	 * 新增考试结果
	 * @param mtpr
	 * @return
	 */
	int insertMyTpResult(MemberTPResult mtpr);

}
