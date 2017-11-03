package com.zhx.modules.frames;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	 * 自定义查询对象方法
	 * @param sql 查询语句
	 * @param requiredType 要返回的类型
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryForObject4Custom(String sql, Class<T> requiredType) throws DataAccessException {
		RowMapper<Object> rowmapper = null;
    	try {
			final Object target = requiredType.newInstance();//要返回的对象
			final Class<?> claz = requiredType;
			rowmapper = new RowMapper<Object>() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					try {
						//获取要返回类型的所有属性，通过反射的形式做类型转换，相当于复制
						Field[] fields = claz.getDeclaredFields();
						if(null!=fields&&fields.length>0){
							for(Field field:fields){
								String fieldName = field.getName();
								if(!"serialVersionUID".equals(fieldName)){
									String columnName = field2Column(fieldName);//将属性名称转为数据库列名称
									String value = rs.getString(columnName);//从查询结果集中获取该属性的值
									field.setAccessible(true);
									field.set(target, value);//赋值
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return target;
				}
				
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
        return (T) queryForObject(sql, rowmapper);
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
        return requiredSingleResult(results);//防止异常
    }

    /**
     * 需要返回单个结果，防止异常
     * @param results
     * @return
     * @throws IncorrectResultSizeDataAccessException
     */
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
	
    /**
     * 属性名称转为数据库中列名称
     * @param fieldName 属性名称
     * @return
     */
    public String field2Column(String fieldName){
    	StringBuilder sb = new StringBuilder();
    	if(null!=fieldName){
    		for(int i=0;i<fieldName.length();i++){
    			char c = fieldName.charAt(i);
    			if(Character.isUpperCase(c)){
    				sb.append("_").append(String.valueOf(c).toLowerCase());
    			}else{
    				sb.append(c);
    			}
    		}
    	}
    	return sb.toString();
    }
    
    /**
     * 分页查询table数据
     * @param sql 分页查询语句
     * @param pageNo 页码
     * @param pageSize 页行数
     * @return
     */
	public Map queryTableList(String sql,int pageNo,int pageSize){
		String rowsSql = pageSql4mysql(sql,pageNo,pageSize);
		String totalSql = "select count(1) from ("+sql+")";
		Map result = new HashMap();
		result.put("total", queryForObject(totalSql, int.class));
		result.put("rows", queryForList(totalSql));
		return result;
	}
	
	/**
	 * 获取mysql分页sql
	 * @param sql 源sql
	 * @param pageNo 页码
	 * @param pageSize 页行数
	 * @return
	 */
	private String pageSql4mysql(String sql, int pageNo, int pageSize) {
		int low = (pageNo-1)*pageSize+1;
		int up = pageNo*pageSize;
		StringBuffer sb = new StringBuffer();
		//mysql分页sql用limit
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
