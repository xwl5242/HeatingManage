package com.zhx.modules.sys.dic.dao.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.zhx.modules.frames.BaseJdbcTemplate;
import com.zhx.modules.frames.GlobalCache;
import com.zhx.modules.sys.dic.Dic;
import com.zhx.modules.sys.dic.dao.DicDao;
import com.zhx.modules.utils.CreateSqlTools;
import com.zhx.modules.utils.IdsUtil;

@Repository
public class DicDaoImpl extends BaseJdbcTemplate implements DicDao {

	@Override
	public Dic selectByNameAndK(String name, String key) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("sys_dic");
		String sql = "select "+SELECT_COLUMN+" from sys_dic where dc_name='"+name+"' and dc_k='"+key+"'";
		return super.queryForObject4Custom(sql, Dic.class,"sys_dic");
	}

	@Override
	public Map<String, Object> selectDicList(Map<String, String> params) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("sys_dic");
		String sql = "select "+SELECT_COLUMN+" from sys_dic where 1=1 ";
		if(!StringUtils.isEmpty(params.get("dcName"))){
			sql += " and dc_name like '%"+params.get("dcName")+"%'";
		}
		if(!StringUtils.isEmpty(params.get("dcDesc"))){
			sql += " and dc_desc like '%"+params.get("dcDesc")+"%'";
		}
		return queryTableList(sql,Integer.valueOf(params.get("pageNumber")), Integer.valueOf(params.get("pageSize")));
	}

	@Override
	public int insertDic(Dic dic) {
		String insertSql = CreateSqlTools.getCreateSql(dic,Dic.class,"sys_dic");
		return update(insertSql);
	}

	@Override
	public Dic selectById(String id) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("sys_dic");
		String sql = "select "+SELECT_COLUMN+" from sys_dic where id='"+id+"'";
		return super.queryForObject4Custom(sql, Dic.class,"sys_dic");
	}

	@Override
	public int updateDic(Dic dic) {
		String insertSql = CreateSqlTools.getUpdateSql(dic,Dic.class,"sys_dic");
		return update(insertSql);
	}

	@Override
	public Dic selectByDicName(String dicName) {
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("sys_dic");
		String sql = "select "+SELECT_COLUMN+" from sys_dic where dc_name='"+dicName+"'";
		return super.queryForObject4Custom(sql, Dic.class,"sys_dic");
	}

	@Override
	public int deleteDics(String ids) {
		ids = IdsUtil.idsAddSingleQuotes(ids);
		String sql = "delete from sys_dic where id in ("+ids+")";
		return update(sql);
	}

}
