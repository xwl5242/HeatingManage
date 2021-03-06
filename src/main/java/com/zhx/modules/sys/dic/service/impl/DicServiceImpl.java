package com.zhx.modules.sys.dic.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhx.modules.common.DBException;
import com.zhx.modules.sys.dic.Dic;
import com.zhx.modules.sys.dic.dao.DicDao;
import com.zhx.modules.sys.dic.service.DicService;
import com.zhx.modules.utils.IdsUtil;
import com.zhx.modules.utils.UUIDGenerator;

@Service
public class DicServiceImpl implements DicService {

	@Autowired
	private DicDao dicDao;

	@Transactional(readOnly=true)
	@Override
	public Dic queryByNameAndK(String name, String key) {
		return dicDao.selectByNameAndK(name,key);
	}

	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> queryDicList(Map<String, String> params) {
		return dicDao.selectDicList(params);
	}

	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int saveDic(Dic dic) throws Exception{
		int insertRet = 0;
		try {
			dic.setId(UUIDGenerator.getUUID());
			dic.setDcSeq("0");
			insertRet = dicDao.insertDic(dic);
			if(insertRet==0){
				throw new DBException();
			}
		} catch (Exception e) {
			throw e;
		}
		return insertRet;
	}

	@Transactional(readOnly=true)
	@Override
	public Dic queryById(String id) {
		return dicDao.selectById(id);
	}

	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public int editDic(Dic dic) throws Exception{
		int insertRet = 0;
		try {
			insertRet = dicDao.updateDic(dic);
			if(insertRet==0){
				throw new DBException();
			}
		} catch (Exception e) {
			throw e;
		}
		return insertRet;
	}

	@Transactional(readOnly=true)
	@Override
	public Dic queryByDicName(String dicName) {
		return dicDao.selectByDicName(dicName);
	}

	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	@Override
	public boolean removeDics(Map<String, String> params) throws Exception{
		boolean result = false;
		try{
			String ids = params.get("ids");
			int des = dicDao.deleteDics(ids);
			result = (des==IdsUtil.idsStrTrans4Array(ids).length);
			if(!result) throw new DBException();
		}catch(Exception e){
			throw e;
		}
		return result;
	}	
	
}
