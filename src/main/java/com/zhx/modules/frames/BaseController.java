package com.zhx.modules.frames;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhx.modules.constants.Const;

public class BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(BaseController.class);

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 自定义返回结果
	 * @param map
	 * @return
	 */
	public String returnJson4Custom(Map<String,Object> map){
		return object2Str4ObjectMapper(map);
	}
	
	/**
	 * 操作成功，结果字符串
	 * @return
	 */
	public String returnJson4Success(){
		return returnJson4Success("操作成功");
	}
	
	public String returnJson4Success(String msg){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(Const.RESPONSE_CODE, true);
		resultMap.put(Const.RESPONSE_MSG, msg);
		logger.info(msg);
		return object2Str4ObjectMapper(resultMap);
	}
	
	/**
	 * 操作失败，结果字符串
	 * @return
	 */
	public String returnJson4Fail(){
		return returnJson4Fail("操作失败！");
	}
	
	public String returnJson4Fail(String msg){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(Const.RESPONSE_CODE, false);
		resultMap.put(Const.RESPONSE_MSG, msg);
		logger.info(msg);
		return object2Str4ObjectMapper(resultMap);
	}
	
	/**
	 * 操作异常
	 * @param e 异常信息
	 * @return
	 */
	public String returnJson4Exception(Exception e){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(Const.RESPONSE_CODE, false);
		resultMap.put(Const.RESPONSE_MSG, e.getMessage());
		logger.info("操作异常");
		return object2Str4ObjectMapper(resultMap);
	}
	
	/**
	 * 对象转字符串
	 * @param source 源对象
	 * @return
	 */
	public String object2Str4ObjectMapper(Object source){
		String result = null;
		try {
			result = objectMapper.writeValueAsString(source);
		} catch (JsonProcessingException e) {
		}
		return result;
	}
	
}
