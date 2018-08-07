package com.zhx.modules.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperHelper {

	public static ObjectMapper om = new ObjectMapper();
	
	/**
	 * map转为javaBean
	 * @param source 带转换的map
	 * @param clazz javaBean 的class
	 * @return
	 */
	public static <T> T MapTrans4JavaBean(Map source,Class<?> clazz){
		T t = null;
		try {
			byte[] rByte = om.writeValueAsBytes(source);
			t = (T) om.readValue(rByte, clazz);
		} catch (Exception e) {
		}
		return t;
	}
	
	/**
	 * list<JavaBean> 转换为 list<map<K,V>>
	 * @param list 带转换的list
	 * @return 转换结果
	 */
	public static List<Map<String,Object>> beanListTrans4MapList(List<?> list){
		List<Map<String,Object>> rlist = new ArrayList<Map<String,Object>>();
		byte[] bytes;
		try {
			bytes = om.writeValueAsBytes(list);
			rlist = (List<Map<String,Object>>)om.readValue(bytes, List.class);
		} catch (Exception e) {
		}
		return rlist;
	}
}
