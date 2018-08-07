package com.zhx.modules.utils;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zhx.modules.constants.Const;
import com.zhx.modules.frames.GlobalCache;

public class CreateSqlTools {
	
	public static String updateWhereStr = "";

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
		List<String> columnsList = GlobalCache.columnsMap.get(tableName);
		//表名称后面括号里的字段
		String valuePre = GlobalCache.columnsStrMap.get(tableName);
		//拼接vlaue（）中的值
		sql.append("insert into ").append(tableName).append(" (").append(valuePre)
		.append(") ").append("values(").append(getSqlCommon(o,claz,columnsList,true)).append(")");
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
		List<String> columnsList = GlobalCache.columnsMap.get(tableName);
		//拼接字符串
		sql.append("update ").append(tableName)
		.append(" set ").append(getSqlCommon(o,claz,columnsList,false)).append(updateWhereStr);
		return sql.toString();
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
		try{
			if(null!=columnsList&&columnsList.size()>0){
				for(String column:columnsList){
					//表中列的名称
					String columnName = column.split("@")[0];
					//表中列的类型
					String columnType = column.split("@")[1];
					//根据列的名称获取对象中的相应的get方法，并获取对象该属性的值
					Method getMethod = getRealMethod(claz,columnName,Const.REFLECT_METHODTYPE_GET);
					String value = String.valueOf(getMethod.invoke(o, null));
					value = value==null?"''":"'"+value+"'";
					//转换类型，日期类型
					if(columnType.equals("datetime")){
						valueSub.append(",");
						if(!insert){//获取更新语句
							valueSub.append(columnName).append("=");
						}
						valueSub.append("str_to_date(").append(value)
						.append(",'%Y-%m-%d %H:%i:%s')");
					}else{
						valueSub.append(",");
						if(!insert){//获取更新语句
							if("ID".equals(columnName)){
								updateWhereStr = " where "+columnName+"= "+value;
							}
							valueSub.append(columnName).append("=");
						}
						valueSub.append(value);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return valueSub.substring(1);
	}
	
	private static Method getRealMethod(Class<?> claz,String columnName,String methodType){
		Method realMethod = null;
		Method[] methods = claz.getDeclaredMethods();
		for(Method method:methods){
			String oriMethodName = method.getName();
			if(oriMethodName.indexOf(methodType)==0){
				String mn = oriMethodName.replaceAll(methodType, "");
				columnName = columnName.replaceAll("_", "").toLowerCase();
				if(columnName.equals(mn.toLowerCase())){
					realMethod = method;
					break;
				}
			}
		}
		return realMethod;
	}
}
