package com.zhx.modules.testpaper.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.zhx.modules.frames.BaseJdbcTemplate;
import com.zhx.modules.frames.GlobalCache;
import com.zhx.modules.testpaper.bean.MemberTPAnswer;
import com.zhx.modules.testpaper.bean.MemberTPResult;
import com.zhx.modules.testpaper.bean.TestPaper;
import com.zhx.modules.testpaper.dao.TestPaperDao;
import com.zhx.modules.utils.CreateSqlTools;
import com.zhx.modules.utils.IdsUtil;
import com.zhx.modules.utils.UUIDGenerator;

@Repository
public class TestPaperDaoImpl extends BaseJdbcTemplate implements TestPaperDao {

	/**
	 * 查询试卷列表
	 */
	@Override
	public Map<String, Object> selectList(Map<String, String> params) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_test_paper");
		String sql="select "+SELECT_COLUMN+" from t_test_paper where 1=1 ";
		if(!StringUtils.isEmpty(params.get("tpName"))){
			sql += " and tp_name like '%"+params.get("tpName")+"%'";
		}
		if(!StringUtils.isEmpty(params.get("tpTitle"))){
			sql += " and tp_title like '%"+params.get("tpTitle")+"%'";
		}
		return queryTableList(sql, Integer.valueOf(params.get("pageNumber")), Integer.valueOf(params.get("pageSize")));
	}

	/**
	 * 新增试卷
	 */
	@Override
	public int insertTestPaper(TestPaper testpaper) {
		String sql = CreateSqlTools.getCreateSql(testpaper, TestPaper.class, "t_test_paper");
		return update(sql);
	}

	/**
	 * 主键查询
	 */
	@Override
	public TestPaper selectById(String id) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_test_paper");
		String sql="select "+SELECT_COLUMN+" from t_test_paper where id = '"+id+"'";
		return queryForObject4Custom(sql, TestPaper.class, "t_test_paper");
	}

	/**
	 * 修改试卷
	 */
	@Override
	public int updateTestPaper(TestPaper testpaper) {
		String sql = CreateSqlTools.getUpdateSql(testpaper, TestPaper.class, "t_test_paper");
		return update(sql);
	}

	/**
	 * 查询试卷和问题关联个数
	 */
	@Override
	public int selectByTestPaperQuestionCount(String id) {
		String sql="select count(1) from t_question_testpaper where tp_id = '"+id+"'";
		return queryForObject(sql, Integer.class);
	}

	/**
	 * 删除试卷
	 */
	@Override
	public int deleteTestPaper(String id) {
		String sql="delete from t_test_paper where id = '"+id+"'";
		String sql0="delete from t_question_testpaper where tp_id='"+id+"'";
		return update(sql)+update(sql0);
	}

	/**
	 * 根据名称查询试卷
	 */
	@Override
	public TestPaper selectByTpName(String tpName) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_test_paper");
		String sql="select "+SELECT_COLUMN+" from t_test_paper where tp_name = '"+tpName+"'";
		return queryForObject4Custom(sql, TestPaper.class, "t_test_paper");
	}

	/**
	 * 根据是否发布查询
	 */
	@Override
	public TestPaper selectByIsPublish(String isPublish) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("t_test_paper");
		String sql="select "+SELECT_COLUMN+" from t_test_paper where is_publish = '"+isPublish+"'";
		return queryForObject4Custom(sql, TestPaper.class, "t_test_paper");
	}

	@Override
	public int update4Publish(String id,String status) {
		String sql = "update t_test_paper set is_publish = '"+status+"',update_time=now() where id='"+id+"'";
		return update(sql);
	}

	/**
	 * 试卷分配题目
	 */
	@Override
	public boolean updateTestPaper4Grant(String tpId,
			Map<String,List<String>> questionAndTypeMap) {
		String dsql = "delete from t_question_testpaper where tp_id = '"+tpId+"'";
		int oriCount = selectByTestPaperQuestionCount(tpId);
		int d = update(dsql);
		int count = 0,successCount=0;
		Set<String> qTypes = questionAndTypeMap.keySet();
		for(String qType:qTypes){
			List<String> qIds = questionAndTypeMap.get(qType);
			for(String qId:qIds){
				count++;
				String id = UUIDGenerator.getUUID();
				String sql = "insert into t_question_testpaper(id,tp_id,q_id,q_type) values('"+id+"','"+tpId+"','"+qId+"','"+qType+"')";
				successCount+=update(sql);
			}
		}
		return count==successCount&&oriCount==d;
	}

	/**
	 * 查询试卷和题目关联
	 */
	@Override
	public List<Map<String, Object>> selectTestPaperQuestion(String tpId) {
		String sql = "select qt.tp_id,q.id,q.q_title,q.q_score,q.q_type,"
				+ "q.q_sel_item,q.q_answer,q.q_aw_keyword from t_question_testpaper qt "
				+ "left join t_question q on qt.q_id = q.id where qt.tp_id = '"+tpId+"'";
		return queryForList(sql);
	}

	/**
	 * 预览前查询数据
	 */
	@Override
	public List<Map<String, Object>> select4PreView(String k, String[] ids) {
		String id = IdsUtil.idsArrayTrans4Str(ids);
		String sql = "select id,q_type,q_title,q_score,q_answer,q_sel_item,q_aw_keyword from t_question where q_type='"+k+"' and id in ("+id+")";
		return queryForList(sql);
	}

	/**
	 * 根据试卷id和问题类型查询试卷和问题所有信息
	 */
	@Override
	public List<Map<String, Object>> selectTestPaperQuestionByIdType(String id,
			String type) {
		String sql = "select qt.tp_id,q.id,replace(q.q_title,\" \",\"\") q_title,q.q_score,q.q_type,replace(q.q_sel_item,\" \",\"\") "
				+ "q_sel_item,SHA1(MD5(CONCAT(q.q_answer,'ssdjz'))) q_answer,q.q_aw_keyword from t_question_testpaper qt left join t_question q on qt.q_id = q.id where "
				+ "qt.tp_id = '"+id+"' and q.q_type='"+type+"'";
		return queryForList(sql);
	}

	/**
	 * 查询我的答题列表
	 */
	@Override
	public List<Map<String, Object>> selectMyTpList(String memberId) {
		String sql = "select mtr.id,mtr.m_id,mtr.tp_id,mtr.score,mtr.exam_time,tp.tp_name from t_member_tp_result mtr "
				+ "left join t_test_paper tp on mtr.tp_id=tp.id where mtr.m_id = '"+memberId+"' order by mtr.exam_time desc";
		return queryForList(sql);
	}

	/**
	 * 微信或web端交卷
	 */
	@Override
	public int insertMyTp4WebOrWx(MemberTPAnswer mtpa) {
		String sql = CreateSqlTools.getCreateSql(mtpa, MemberTPAnswer.class, "t_member_tp_answer");
		return update(sql);
	}

	/**
	 * 查询我的答题，根据交卷时间
	 */
	@Override
	public List<Map<String, Object>> selectMyTpByMIdAndSubmitTime4WebOrWx(String id, String date) {
		String sql = "select distinct mta.q_id,mta.tp_id,q.q_title,q.q_sel_item,ASCII(q.q_answer) q_answer,ASCII(mta.q_answer) my_answer "
				+ "from t_member_tp_answer mta left join t_question q on mta.q_id=q.id where mta.m_id='"+id+"' and "
						+ "mta.submit_time=str_to_date('"+date+"','%Y-%m-%d %H:%i:%s') ORDER BY mta.seq";
		return queryForList(sql);
	}

	/**
	 * 外部接口，根据身份证查询是否答题
	 */
	@Override
	public int selectMyTpResultByCId4WebOrWx(String cId) {
		String sql="select count(1) from t_member_tp_result mtr left join t_member m on mtr.m_id = m.id where m.m_cid ='"+cId+"' and mtr.score>=60";
		return queryForObject(sql, Integer.class);
	}

	/**
	 * 新增试卷和题目关联
	 */
	@Override
	public int insertTpQuestion(String id, List<String> quesIdList,
			String string) {
		int result = 0;
		for(String quesId:quesIdList){
			String sql = "insert into t_question_testpaper(id,tp_id,q_id,q_type) values('"+UUIDGenerator.getUUID()+"','"+id+"','"+quesId+"','"+string+"')";
			result+=update(sql);
		}
		return result;
	}

	@Override
	public int insertMyTpResult(MemberTPResult mtpr) {
		String insertSql = CreateSqlTools.getCreateSql(mtpr, MemberTPResult.class, "t_member_tp_result");
		return update(insertSql);
	}

}
