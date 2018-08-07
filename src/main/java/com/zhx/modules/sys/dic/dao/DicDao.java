package com.zhx.modules.sys.dic.dao;

import java.util.Map;

import com.zhx.modules.sys.dic.Dic;

public interface DicDao {

	Dic selectByNameAndK(String name, String key);

	Map<String, Object> selectDicList(Map<String, String> params);

	int insertDic(Dic dic);

	Dic selectById(String id);

	int updateDic(Dic dic);

	Dic selectByDicName(String dicName);

	int deleteDics(String ids);

}
