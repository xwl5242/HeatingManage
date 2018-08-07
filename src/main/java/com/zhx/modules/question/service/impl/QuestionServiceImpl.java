package com.zhx.modules.question.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhx.modules.common.DBException;
import com.zhx.modules.question.bean.Question;
import com.zhx.modules.question.dao.QuestionDao;
import com.zhx.modules.question.service.QuestionService;
import com.zhx.modules.utils.DateUtils;
import com.zhx.modules.utils.IdsUtil;
import com.zhx.modules.utils.UUIDGenerator;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	/**
	 * 查询问题列表
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryList(Map<String, String> params) {
		return questionDao.selectList(params);
	}

	/**
	 * 导出
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> exportQuestion(Map<String, String> params) {
		return questionDao.selectList4Export(params);
	}

	/**
	 * 删除问题
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean removeQuestions(Map<String, String> params) throws Exception{
		boolean result = false;
		try{
			String ids = params.get("ids");
			int count = questionDao.selectQuestionTestPaperCount(ids);
			int des = questionDao.deleteQuestions(ids);
			result = (des==IdsUtil.idsStrTrans4Array(ids).length+count);
			if(!result) throw new DBException();
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 根据问题标题查询问题信息
	 */
	@Transactional(readOnly=true)
	@Override
	public Question queryByQTitle(String qTitle) {
		return questionDao.selectByQTitle(qTitle);
	}

	/**
	 * 新增问题
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int saveQuestion(Question question)  throws Exception{
		int result = 0;
		try{
			question.setqAnswer(question.getqAnswer().replaceAll(",", ""));
			question.setId(UUIDGenerator.getUUID());
			question.setCreateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			question.setUpdateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			result = questionDao.insertQuestion(question);
			if(result==0){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 修改问题
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int editQuestion(Question question) throws Exception{
		int result = 0;
		try{
			question.setqAnswer(question.getqAnswer().replaceAll(",", ""));
			question.setUpdateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			result = questionDao.updateQuestion(question);
			if(result==0){
				throw new DBException();
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	/**
	 * 根据主键查询
	 */
	@Transactional(readOnly=true)
	@Override
	public Question queryById(String id) {
		return questionDao.selectById(id);
	}

	/**
	 * 根据题目类型查询，该类型的所有题目list
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> queryAllListByType(String qType) {
		return questionDao.selectAllListByType(qType);
	}

	/**
	 * excel导入题目数据
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean saveQuestion4Import(List<List<Object>> listob, String userId) throws Exception{
		int result = 0;
		try{
	        //得到数据，持久化到数据库  
	        if(null!=listob&&listob.size()>0){
	        	listob = listob.subList(1, listob.size());
	        	result = questionDao.batchInsert(listob,userId);
	        	if(result!=listob.size()){
	        		throw new DBException();
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result==listob.size();
	}
}
