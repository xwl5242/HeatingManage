package com.zhx.modules.sys.dic.service;

import java.util.Map;

import com.zhx.modules.sys.dic.Dic;

public interface DicService {

	Dic queryByNameAndK(String name, String key);

	Map<String, Object> queryDicList(Map<String, String> params);

	int saveDic(Dic dic) throws Exception;

	Dic queryById(String id);

	int editDic(Dic dic) throws Exception;

	Dic queryByDicName(String dicName);

	boolean removeDics(Map<String, String> params) throws Exception;

}
