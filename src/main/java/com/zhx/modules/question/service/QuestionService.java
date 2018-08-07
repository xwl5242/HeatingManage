package com.zhx.modules.question.service;

import java.util.List;
import java.util.Map;

import com.zhx.modules.question.bean.Question;

public interface QuestionService {

	/**
	 * 查询问题列表
	 * @param params
	 * @return
	 */
	Map<String, Object> queryList(Map<String, String> params);

	/**
	 * 导出
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> exportQuestion(Map<String, String> params);

	/**
	 * 删除问题
	 * @param params
	 * @return
	 */
	boolean removeQuestions(Map<String, String> params) throws Exception;

	/**
	 * 根据问题标题查询问题信息
	 * @param qTitle
	 * @return
	 */
	Question queryByQTitle(String qTitle);

	/**
	 * 新增问题
	 * @param question
	 * @return
	 */
	int saveQuestion(Question question) throws Exception;

	/**
	 * 修改问题
	 * @param question
	 * @return
	 */
	int editQuestion(Question question) throws Exception;

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	Question queryById(String id);

	/**
	 * 根据题目类型查询，该类型的所有题目list
	 * @param qType 类型
	 * @return
	 */
	List<Map<String, Object>> queryAllListByType(String qType);

	/**
	 * excel导入题目数据
	 * @param listob
	 * @param userId
	 * @return
	 */
	boolean saveQuestion4Import(List<List<Object>> listob, String userId) throws Exception;

}
