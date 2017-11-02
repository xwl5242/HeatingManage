package com.zhx.modules.sys.user.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import com.zhx.modules.frames.InitTables;

public class CreateSqlTools {

	/**
	 * 获取insert语句
	 * @param o
	 * @param claz
	 * @param tableName
	 * @return
	 */
	public static String getCreateSql(Object o,Class<?> claz,String tableName){
		StringBuilder sql = new StringBuilder();
		//获取某张表的所有字段和字段的类型
		List<String> columnsList = InitTables.columnsMap.get(tableName);
		//表名称后面括号里的字段
		String valuePre = InitTables.columnsStrMap.get(tableName);
		//拼接vlaue（）中的值
		sql.append("insert into ").append(tableName).append(" (").append(valuePre)
		.append(") ").append("values(").append(getSqlCommon(o,claz,columnsList,false));
		return sql.toString();
	}
	
	/**
	 * 获取update语句
	 * @param o
	 * @param claz
	 * @param tableName
	 * @return
	 */
	public static String getUpdateSql(Object o,Class<?> claz,String tableName){
		StringBuilder sql = new StringBuilder();
		//获取某张表的所有字段和字段的类型
		List<String> columnsList = InitTables.columnsMap.get(tableName);
		//拼接字符串
		sql.append("update ").append(tableName)
		.append(" set").append(getSqlCommon(o,claz,columnsList,false));
		return sql.toString();
	}
	
	/**
	 * 根据数据库中列的名称获取相应类的属性的get方法
	 * @param columnName
	 * @return
	 */
	public static String getMethodName(String columnName){
		columnName = columnName.toLowerCase();
		if(!columnName.contains("_")){//不包含_的列
			return "get"+columnName.substring(0,1).toUpperCase()+columnName.substring(1);
		}
		String[] columns = columnName.split("_");//去除下划线
		String pre = columns[0].substring(0,1).toUpperCase()+columns[0].substring(1);//下划线前半部分首字母大写
		String sub = columns[1].substring(0,1).toUpperCase()+columns[1].substring(1);//下划线后半部分首字母大写
		return "get"+pre+sub;
	}
	
	/**
	 * 公共方法
	 * @param o
	 * @param claz
	 * @param columnsList
	 * @param insert
	 * @return
	 */
	public static String getSqlCommon(Object o,Class<?> claz,List<String> columnsList,boolean insert) {
		StringBuilder valueSub = new StringBuilder();
		try {
			if(null!=columnsList&&columnsList.size()>0){
				for(String column:columnsList){
					//表中列的名称
					String columnName = column.split("@")[0];
					//表中列的类型
					String columnType = column.split("@")[1];
					//根据列的名称获取对象中的相应的get方法，并获取对象该属性的值
					String getMethodName = getMethodName(columnName);
					Method getMethod = claz.getDeclaredMethod(getMethodName, null);
					String value = String.valueOf(getMethod.invoke(o, null));
					//转换类型，日期类型
					if(columnType.equals("datetime")){
						valueSub.append(",");
						if(!insert){//获取更新语句
							valueSub.append(columnName).append("=");
						}
						valueSub.append("str_to_date('").append(value)
						.append("','%Y-%m-%d %H:%i:%s')");
					}else{
						valueSub.append(",");
						if(!insert){//获取更新语句
							valueSub.append(columnName).append("=");
						}
						valueSub.append(value);
					}
				}
			}
		} catch (Exception e) {
		}
		return valueSub.substring(1);
	}
}
