package com.zhx.modules.sys.log.dao;

import java.util.Map;

import com.zhx.modules.sys.log.bean.OperateLog;

public interface LogDao {

	/**
	 * 写入操作日志
	 * @param log
	 */
	void insertOperateLog(OperateLog log);

	/**
	 * 查询操作日志列表
	 * @param params
	 * @return
	 */
	Map<String, Object> selectLogList(Map<String, String> params);

}
