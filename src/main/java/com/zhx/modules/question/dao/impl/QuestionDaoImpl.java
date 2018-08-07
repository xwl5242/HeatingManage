package com.zhx.modules.question.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.zhx.modules.frames.BaseJdbcTemplate;
import com.zhx.modules.frames.GlobalCache;
import com.zhx.modules.question.bean.Question;
import com.zhx.modules.question.dao.QuestionDao;
import com.zhx.modules.utils.CreateSqlTools;
import com.zhx.modules.utils.DateUtils;
import com.zhx.modules.utils.IdsUtil;
import com.zhx.modules.utils.UUIDGenerator;

@Repository
public class QuestionDaoImpl extends BaseJdbcTemplate implements QuestionDao {
	
	/**
	 * 查询问题列表
	 */
	@Override
	public Map<String, Object> selectList(Map<String, String> params) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_question");
		String sql="select "+SELECT_COLUMN+" from t_question where 1=1 ";
		if(!StringUtils.isEmpty(params.get("qTitle"))){
			sql += " and q_title like '%"+params.get("qTitle")+"%'";
		}
		if(!StringUtils.isEmpty(params.get("qType"))){
			sql += " and q_type = '"+params.get("qType")+"'";
		}
		if(!StringUtils.isEmpty(params.get("hasIds"))&&!"-1".equals(params.get("hasIds"))){
			String ids = IdsUtil.idsAddSingleQuotes(params.get("hasIds"));
			sql += " and id not in ("+ids+")";
		}
		sql+=" order by create_time desc";
		return queryTableList(sql, Integer.valueOf(params.get("pageNumber")), Integer.valueOf(params.get("pageSize")));
	}

	/**
	 * 导出
	 */
	@Override
	public List<Map<String, Object>> selectList4Export(
			Map<String, String> params) {
		String sql="select id,q_title,(case q_type when '0' then '单选' when '1' then '多选' when '2' then '填空' when '3' then '问答' end) "
				+ "q_type,q_score,(case when q_type!=0 and q_type!=1 then '该问题不是选择题' else q_sel_item end) q_sel_item,q_answer,"
				+ "(case when q_type!=3 then '该问题不是问答题' else q_aw_keyword end) q_aw_keyword from t_question where 1=1";
		if(!StringUtils.isEmpty(params.get("qTitle"))){
			sql += " and q_title like '%"+params.get("qTitle")+"%'";
		}
		if(!StringUtils.isEmpty(params.get("qType"))){
			sql += " and q_type = '"+params.get("qType")+"'";
		}
		return queryForList(sql);
	}

	/**
	 * 查询问题和试卷关联个数
	 */
	@Override
	public int selectQuestionTestPaperCount(String ids) {
		ids = IdsUtil.idsAddSingleQuotes(ids);
		String sql = "select count(1) from t_question_testpaper where q_id in("+ids+")";
		return queryForObject(sql, Integer.class);
	}

	/**
	 * 删除问题
	 */
	@Override
	public int deleteQuestions(String ids) {
		ids = IdsUtil.idsAddSingleQuotes(ids);
		String sql = "delete from t_question where id in("+ids+")";
		return update(sql);
	}

	/**
	 * 根据问题标题查询问题信息
	 */
	@Override
	public Question selectByQTitle(String qTitle) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_question");
		String sql="select "+SELECT_COLUMN+" from t_question where q_title='"+qTitle+"' ";
		return queryForObject4Custom(sql, Question.class,"t_question");
	}

	/**
	 * 新增问题
	 */
	@Override
	public int insertQuestion(Question question) {
		String insertSql = CreateSqlTools.getCreateSql(question, Question.class, "t_question");
		return update(insertSql);
	}

	/**
	 * 修改问题
	 */
	@Override
	public int updateQuestion(Question question) {
		String updateSql = CreateSqlTools.getUpdateSql(question, Question.class,"t_question");
		return update(updateSql);
	}

	/**
	 * 根据主键查询
	 */
	@Override
	public Question selectById(String id) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_question");
		String sql="select "+SELECT_COLUMN+" from t_question where id='"+id+"' ";
		return queryForObject4Custom(sql, Question.class,"t_question");
	}

	/**
	 * 根据题目类型查询，该类型的所有题目list
	 */
	@Override
	public List<Map<String, Object>> selectAllListByType(String qType) {
		String sql = "select id,q_type from t_question where q_type='"+qType+"'";
		return queryForList(sql);
	}

	@Override
	public int batchInsert(List<List<Object>> listob,String userId) {
		final String creator = userId;
		final List<List<Object>> list = listob;
		String sql= "insert into t_question (ID,Q_TITLE,Q_TYPE,Q_SCORE,Q_SEL_ITEM,Q_ANSWER,Q_AW_KEYWORD,"
				+ "CREATOR,CREATE_TIME,UPDATOR,UPDATE_TIME) values(?,?,?,?,?,?,?,?,?,?,?)";
		int[] result = batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				List<Object> ilist = list.get(i);
				ps.setString(1, UUIDGenerator.getUUID());
				ps.setString(2, ilist.get(0).toString());
				ps.setString(3, ilist.get(1).toString());
				ps.setString(4, ilist.get(2).toString());
				ps.setString(5, ilist.get(3).toString());
				ps.setString(6, ilist.get(4).toString());
				ps.setString(7, ilist.get(5).toString());
				ps.setString(8, creator);
				ps.setString(9, DateUtils.date2yyyyMMddHHmmssStr(null));
				ps.setString(10, creator);
				ps.setString(11, DateUtils.date2yyyyMMddHHmmssStr(null));
			}
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return result.length;
	}

}
