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
	
	/**
	 * 数据库表相关
	 */
	public static List<String> tablesList = new ArrayList<String>();//所有表名称集合
	public static Map<String,List<String>> columnsMap = new HashMap<String,List<String>>();//表，表字段map
	public static Map<String,String> columnsStrMap = new HashMap<String,String>();//表，表字段字符串（column1,column2,column3...）
	public static Map<String,String> columnsStrMap4LowerCase = new HashMap<String,String>();//表，表字段字符串（column1,column2,column3...）
	
	/**
	 * 组织机构权限相关
	 */
	public static Map<String,List<Map<String,Object>>> rightsMap = new HashMap<String,List<Map<String,Object>>>();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * select table_name from information_schema.TABLES where table_schema = 'answer';
	 * select column_name from information_schema.COLUMNS where table_schema = 'answer' and table_name='sys_btn'
	 */
	public void init(){
		initDBTables();
		initRights();
	}

	/**
	 * 初始化组织机构权限相关信息
	 */
	public void initRights(){
		logger.info("*******************初始化组织机构权限开始*******************");
		//查询组织结构权限父节点
		String pSQL = "select id from sys_right where is_del='0' and pid='0'";
		List<Map<String,Object>> pList = jdbcTemplate.queryForList(pSQL);
		if(null!=pList&&pList.size()>0){
			for(Map<String,Object> pMap:pList){
				String id = pMap.get("id").toString();
				//查询每个父节点下的子节点
				String cSQL = "select id,pid,right_name,right_url,right_desc from sys_right "
						+ "where FIND_IN_SET(id,getChildList('"+id+"')) order by seq";
				List<Map<String,Object>> cList = jdbcTemplate.queryForList(cSQL);
				
				if(null!=cList&&cList.size()>0){
					List<Map<String,Object>> listmap = treeMenuList(cList,"0");
					rightsMap.put(id, listmap);
				}
			}
		}
		logger.info("所有组织机构权限:"+rightsMap);
		logger.info("*******************初始化组织机构权限开始*******************");
	}
	
	public List<Map<String,Object>> treeMenuList(List<Map<String,Object>> menuList, String parentId) {  
		List<Map<String,Object>> childMenu = new ArrayList<Map<String,Object>>();  
		for (Map<String,Object> jsonMenu : menuList) {  
            String menuId = jsonMenu.get("id").toString();  
            String pid = jsonMenu.get("pid").toString();  
            if (parentId.equals(pid)) {  
        	    List<Map<String,Object>> c_node = treeMenuList(menuList, menuId);  
                jsonMenu.put("childNode", c_node);  
                childMenu.add(jsonMenu);  
            }  
        }  
        return childMenu;  
    }  
	
	/**
	 * 初始化数据库表的所有信息
	 */
	public void initDBTables(){
		logger.info("*******************初始化数据库表开始*******************");
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
				StringBuilder columns4LowerCaseStr = new StringBuilder();
				if(null!=clist&&clist.size()>0){
					for(Map<String,Object> cmap:clist){
						String cn = cmap.get("column_name").toString();//列名称
						String ct = cmap.get("data_type").toString();//列类型
						columnsList.add(cn+"@"+ct);
						columnsStr.append(",").append(cn);//所有列以","分割的字符串
						columns4LowerCaseStr.append(",").append(cn.toLowerCase());
					}
					columnsMap.put(tn, columnsList);//存储
					columnsStrMap.put(tn, columnsStr.substring(1));//存储
					columnsStrMap4LowerCase.put(tn, columns4LowerCaseStr.substring(1));
				}
			}
		}
		logger.info("所有表名称集合:"+tablesList);
		logger.info("所有表表字段map:"+columnsMap);
		logger.info("所有表表字段字符串:"+columnsStrMap);
		logger.info("*******************初始化数据库表结束*******************");
	}
}
