package com.zhx.modules.frames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zhx.modules.common.Global;

public class InitTables {
	
	private Logger logger = LoggerFactory.getLogger(InitTables.class); 
	
	public static List<String> tablesList = new ArrayList<String>();//所有表名称集合
	public static Map<String,List<String>> columnsMap = new HashMap<String,List<String>>();//表，表字段map
	public static Map<String,String> columnsStrMap = new HashMap<String,String>();//表，表字段字符串（column1,column2,column3...）
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * select table_name from information_schema.TABLES where table_schema = 'answer';
	 * select column_name from information_schema.COLUMNS where table_schema = 'answer' and table_name='sys_btn'
	 */
	public void init(){
		//获取数据库中所有表名称sql
		String tablesSql = "select table_name from information_schema.TABLES "
				+ "where table_schema = '"+Global.getJdbc2DatabaseName()+"'";
		//查询
		List<Map<String,Object>> list = jdbcTemplate.queryForList(tablesSql);
		//处理查询结果，保存表名称，和各个表含有列的信息
		if(null!=list&&list.size()>0){
			for(Map<String,Object> map:list){
				//存储所有表名称
				String tn = map.get("table_name").toString();
				if(tablesList.contains(tn)){
					tablesList.remove(tn);
				}
				tablesList.add(tn);
				//根据表名称，查询表所含列的sql
				String columnsSql = "select column_name,data_type from information_schema.COLUMNS "
						+ "where table_schema = '"+Global.getJdbc2DatabaseName()+"' and table_name='"+tn+"'";
				//查询
				List<Map<String,Object>> clist = jdbcTemplate.queryForList(columnsSql);
				//存储列名臣和列类型
				List<String> columnsList = new ArrayList<String>();
				StringBuilder columnsStr = new StringBuilder();
				if(null!=clist&&clist.size()>0){
					for(Map<String,Object> cmap:clist){
						String cn = cmap.get("column_name").toString();//列名称
						String ct = cmap.get("data_type").toString();//列类型
						columnsList.add(cn+"@"+ct);
						columnsStr.append(",").append(cn);//所有列以","分割的字符串
					}
					columnsMap.put(tn, columnsList);//存储
					columnsStrMap.put(tn, columnsStr.substring(1));//存储
				}
			}
		}
		logger.info("所有表名称集合:"+tablesList);
		logger.info("所有表表字段map:"+columnsMap);
		logger.info("所有表表字段字符串:"+columnsStrMap);
	}

}
