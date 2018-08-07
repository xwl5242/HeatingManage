package com.zhx.modules.frames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zhx.modules.common.Global;
import com.zhx.modules.constants.Const;
import com.zhx.modules.sys.right.bean.Right;
import com.zhx.modules.utils.ObjectMapperHelper;

public class GlobalCache {
	
	private Logger logger = LoggerFactory.getLogger(GlobalCache.class); 
	
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
	public static Map<String,Right> rightsMap = new HashMap<String,Right>();
	public static List<Right> rightsList = new ArrayList<Right>();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void init(){
		initDBTables();
		initRights();
	}

	/**
	 * 初始化组织机构权限相关信息
	 */
	public void initRights(){
		logger.info("*******************初始化权限信息开始*******************");
		try {
			//系统菜单总集sql
			String rightSql = "select id,pid,right_name,right_desc,right_url,is_leaf,icon,seq "
					+ "from sys_right where FIND_IN_SET(id,getChildList('"+Const.RIGHT_ROOT+"')) "
					+ "and pid!= '"+Const.RIGHT_ROOT_PID+"' order by pid,seq";
			//所有'系统菜单'sql
			String orgRightSql = "select id,pid,right_name,right_desc,right_url,is_leaf,icon,seq "
					+ "from sys_right where pid='"+Const.RIGHT_ROOT_PID+"'";
			
			/**
			 * 所有权限,将list遍历转换为Map格式
			 * 作用：用户登录，根据用户角色获取该角色下的权限，权限从这个内存中获取
			 * 注意：权限的增删改需要更新此内存数据
			 */
			List<Map<String,Object>> rights = jdbcTemplate.queryForList(rightSql);
			if(null!=rights&&rights.size()>0){
				for(Map<String,Object> rightMap:rights){
					String rightId = rightMap.get("id").toString();
					rightMap = dbMapTrans4BeanMap(rightMap);
					Right right = ObjectMapperHelper.MapTrans4JavaBean(rightMap, Right.class);
					rightsMap.put(rightId, right);
				}
			}
			/**
			 * 组织机构树list
			 * 作用：页面中所有有组织机构树的地方，都从这块内存中获取
			 * 注意：权限的增删改需要更新此内存数据
			 */
			List<Map<String,Object>> rightList = new ArrayList<Map<String,Object>>();
			rightList.add(jdbcTemplate.queryForList(orgRightSql).get(0));
			rightList.addAll(rights);
			if(null!=rightList&&rightList.size()>0){
				for(Map<String,Object> rightMap:rightList){
					rightMap = dbMapTrans4BeanMap(rightMap);
					Right right = ObjectMapperHelper.MapTrans4JavaBean(rightMap, Right.class);
					rightsList.add(right);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("所有权限信息："+rightsMap);
		logger.info("组织机构树信息："+rightsList);
		logger.info("*******************初始化权限信息结束*******************");
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
	
	/**
	 * 由于数据库字段和系统bean字段名称不同，转换一下
	 * @param dbMap
	 * @return
	 */
	public Map<String,Object> dbMapTrans4BeanMap(Map<String,Object> dbMap){
		Map<String,Object> beanMap = new HashMap<String,Object>();
		if(null!=dbMap&&!dbMap.isEmpty()){
			Set<String> sets = dbMap.keySet();
			for(String set:sets){
				Object dbO = dbMap.get(set);
				if(set.indexOf("_")>0){
					int index = set.indexOf("_")+1;
					String old = String.valueOf(set.charAt(index));
					String news = old.toUpperCase();
					beanMap.put(set.replace("_"+old, news),dbO);
				}else{
					beanMap.put(set, dbO);
				}
			}
		}
		return beanMap;
	}
}
