package com.zhx.modules.question.dao;

import java.util.List;
import java.util.Map;

import com.zhx.modules.question.bean.Question;

public interface QuestionDao {

	/**
	 * 查询问题列表
	 * @param params
	 * @return
	 */
	Map<String, Object> selectList(Map<String, String> params);

	/**
	 * 导出
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectList4Export(Map<String, String> params);

	/**
	 * 查询问题和试卷关联个数
	 * @param ids
	 * @return
	 */
	int selectQuestionTestPaperCount(String ids);

	/**
	 * 删除问题
	 * @param ids
	 * @return
	 */
	int deleteQuestions(String ids);

	/**
	 * 根据问题标题查询问题信息
	 * @param qTitle
	 * @return
	 */
	Question selectByQTitle(String qTitle);

	/**
	 * 新增问题
	 * @param question
	 * @return
	 */
	int insertQuestion(Question question);

	/**
	 * 修改问题
	 * @param question
	 * @return
	 */
	int updateQuestion(Question question);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	Question selectById(String id);

	/**
	 * 根据题目类型查询，该类型的所有题目list
	 * @param qType
	 * @return
	 */
	List<Map<String, Object>> selectAllListByType(String qType);

	/**
	 * 批量插入
	 * @param listob
	 * @return
	 */
	int batchInsert(List<List<Object>> listob,String userId);

}
