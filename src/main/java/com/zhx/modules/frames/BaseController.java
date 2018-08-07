package com.zhx.modules.frames;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhx.modules.constants.Const;
import com.zhx.modules.sys.dic.Dic;
import com.zhx.modules.sys.right.bean.Right;
import com.zhx.modules.sys.user.bean.User;
import com.zhx.modules.utils.GlobalCacheUtils;

public class BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(BaseController.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	/**
	 * 自定义返回结果
	 * @param map
	 * @return
	 */
	public String returnJson4Custom(Object map){
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
	
	/**
	 * 请求参数字符串转map。例如：param1=1&param2=2&... ==> {param1:1,param2:2,...} 
	 * @param params
	 * @return
	 */
	public Map<String,String> paramsStr2Map(String params){
		Map<String,String> result = new HashMap<String,String>();
		if(!StringUtils.isBlank(params)){
			String[] pramasKVs = params.split("&");
			for(String pramasKV:pramasKVs){
				String[] KVs = pramasKV.split("=");
				result.put(KVs[0], KVs.length==2?(KVs[1].contains("%")?(URLDecoder.decode(KVs[1])):KVs[1]):"");
			}
		}
		return result;
	}
	/**
	 * 获取当前登录用户的id
	 * @param request
	 * @return
	 */
	public String getUserId(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(Const.SESSION_USER);
		return user.getId();
	}
	
	public User getUser(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(Const.SESSION_USER);
		return user;
	}
	
	/**
	 * 权限修改或角色授权修改，刷新session信息，可能当前用户权限修改
	 * @param request
	 * @param rightChangedMenu 触发刷新前，用户在系统中的所处位置（操作菜单）
	 */
	public void refreshSession4Right(HttpServletRequest request,String rightChangedMenu){
		HttpSession session = request.getSession();
		List<?> list = queryRights(session.getAttribute(Const.SESSION_USER_ID).toString());
		session.setAttribute(Const.SESSION_RIGHT, list);
		session.setAttribute(Const.SESSION_RIGHT_CHANGED, true);
		session.setAttribute(Const.SESSION_RIGHT_CHANGED_MENU, rightChangedMenu);
	}
	
	/**
	 * 查询所有权限信息
	 * @param userId
	 * @return
	 */
	public List<?> queryRights(String userId){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		try{
			String pSql = "select right_id from sys_role_right where role_id in (select role_id from sys_user_role ur "
					+ "LEFT JOIN sys_role r on ur.role_id=r.id where user_id = '"+userId+"' and r.is_del='0')";
			List<Map<String,Object>> listmap = jdbcTemplate.queryForList(pSql);
			if(null!=listmap&&listmap.size()>0){
				for(Map<String,Object> map:listmap){
					String rid = map.get("right_id").toString();
					Right right = GlobalCache.rightsMap.get(rid);
					ObjectMapper om = new ObjectMapper();
					Map<String,Object> rMap = (Map<String,Object>)om.readValue(om.writeValueAsBytes(right), Map.class);
					result.add(rMap);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return GlobalCacheUtils.menuTreeList(result, Const.RIGHT_ROOT);
	}
	
	/**
	 * 查询字典表，根据字典的名称和key值
	 * @param name
	 * @param k
	 * @return
	 */
	public Dic queryDicByNameAndK(String name,String k){
		String SELECT_COLUMN = GlobalCache.columnsStrMap4LowerCase.get("sys_dic");
		String sql = "select "+SELECT_COLUMN+" from sys_dic where dc_name='"+name+"' and dc_k='"+k+"'";
		Dic dic = jdbcTemplate.queryForObject(sql, new RowMapper<Dic>(){
			@Override
			public Dic mapRow(ResultSet rs, int arg1) throws SQLException {
				Dic dic = new Dic();
				dic.setId(rs.getString("id"));
				dic.setDcName(rs.getString("dc_name"));
				dic.setDcK(rs.getString("dc_k"));
				dic.setDcV(rs.getString("dc_v"));
				dic.setDcDesc(rs.getString("dc_desc"));
				dic.setDcSeq(rs.getString("dc_seq"));
				return dic;
			}
		});
		return dic;
	}
	
	/**
	 * 根据分数获取提示语
	 * @param score
	 * @return
	 */
	public String getSubmitDicK(String score){
		String mType = "";
		int sc = Integer.valueOf(score);
		if(sc<60){
			mType = "0";
		}else if(sc>=60&&sc<80){
			mType = "1";
		}else if(sc>=80&&sc<90){
			mType = "2";
		}else if(sc>=90&&sc<=100){
			mType = "3";
		}
		return mType;
	}
	
	/**
	 * 考试结果等级评语
	 * @param score
	 * @return
	 */
	public Map<String,Object> wxMsgMap4ExamResult(String score){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Dic dic = queryDicByNameAndK("wx_tp_result_info", score);
		String dicV = dic.getDcV();
		resultMap.put("title", dicV.split(";")[0]);
		resultMap.put("desc", dicV.split(";")[1]);
		return resultMap;
	}
}
