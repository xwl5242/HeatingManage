package com.zhx.modules.frames;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class BaseJdbcTemplate extends JdbcTemplate {

	@Autowired
	private DataSource dataSource;
	
	public BaseJdbcTemplate(){
		
	}
	
	/**
     * 重写JdbcTemplate里面的queryForObject方法源码调用的requiredSingleResult，当查询到的结果为空时返回null(原来是抛出异常)
     */
    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
        return queryForObject(sql, getSingleColumnRowMapper(requiredType));
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        List<T> results = query(sql, rowMapper);
        return requiredSingleResult(results);
    }

    public static <T> T requiredSingleResult(Collection<T> results) throws IncorrectResultSizeDataAccessException {
        int size = (results != null ? results.size() : 0); 
        if (size == 0) {
            return null; 
        } 
        if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, size); 
        } 
        return results.iterator().next(); 
    }
	
	public Map queryTableList(String sql,int pageNo,int pageSize){
		String rowsSql = pageSql4mysql(sql,pageNo,pageSize);
		String totalSql = "select count(1) from ("+sql+")";
		Map result = new HashMap();
		result.put("total", queryForObject(totalSql, int.class));
		result.put("rows", queryForList(totalSql));
		return result;
	}
	
	private String pageSql4mysql(String sql, int pageNo, int pageSize) {
		int low = (pageNo-1)*pageSize+1;
		int up = pageNo*pageSize;
		StringBuffer sb = new StringBuffer();
		sb.append(sql).append(" LIMIT ").append(low).append(",").append(up);
		return sb.toString();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
}
