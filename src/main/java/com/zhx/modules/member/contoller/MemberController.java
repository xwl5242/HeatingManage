package com.zhx.modules.member.contoller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhx.log.OpLog;
import com.zhx.modules.constants.Const;
import com.zhx.modules.frames.BaseController;
import com.zhx.modules.member.bean.Member;
import com.zhx.modules.member.service.MemberService;
import com.zhx.modules.utils.DESUtils;
import com.zhx.modules.utils.DateUtils;
import com.zhx.modules.utils.UUIDGenerator;

@Controller
@RequestMapping("/m")
public class MemberController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;
	
	/**
	 * 跳转到web页面的首页
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="",method=RequestMethod.GET)
	public String index(Model model) {
		Map<String,Object> result = memberService.queryTestPaperAndQuestion4Index("0");
		logger.info("跳转到试卷首页面，试卷信息："+result);
		model.addAttribute("tpqMap", result);
		return "web/member/index";
	}
	
	@RequestMapping(value="/dtp",method=RequestMethod.GET)
	@ResponseBody
	public String getDefaultTPInfo(){
		Map<String,Object> map = memberService.getDefaultTPInfo();
		map.put(Const.RESPONSE_CODE, true);
		return returnJson4Custom(map);
	}
	
	/**
	 * 跳转到web登录页面
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "web/member/weblogin";
	}
	
	/**
	 * 跳转到web登录页面
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register(){
		return "web/member/webregister";
	}
	
	/**
	 * 我的答題列表
	 * @return
	 */
	@RequestMapping(value="/mytplist",method=RequestMethod.GET)
	public String mytplist(HttpServletRequest request,Model model){
		Member m = (Member)request.getSession().getAttribute(Const.SESSION_MEMBER);
		List<Map<String,Object>> result = memberService.queryMyTpList(m.getId());
		logger.info("跳转到我的答题页面，试卷信息："+result);
		model.addAttribute("tpqList", result);
		return "web/member/mytplist";
	}
	
	@RequestMapping(value="/hasMember",method=RequestMethod.POST)
	@ResponseBody
	public String hasMember(HttpServletRequest request,String phoneOrCId){
		Map<String,Object> result = new HashMap<String,Object>();
		Member m = memberService.queryByPhoneOrCId(phoneOrCId);
		if(null==m){
			result.put(Const.RESPONSE_CODE, false);
		}else{
			result.put(Const.RESPONSE_CODE, true);
		}
		return returnJson4Custom(result);
	}
	
	/**
	 * 注册
	 * @param request
	 * @param member
	 * @return
	 */
	@OpLog(optName="web前端注册",optKey="web用户登录",optType=Const.OPLOG_TYPE_UPDATE)
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request,Member member){
		logger.info("member register,member info:"+member);
		String result = "";
		try {
			member.setmType("0");
			member.setmPwd(DESUtils.encrypt(member.getmPwd()));
			member.setId(UUIDGenerator.getUUID());
			member.setmUserName(member.getmName());
			member.setCreateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			int r = memberService.registerMember4Web(member);
			result = r==1?returnJson4Success():returnJson4Fail();
		} catch (Exception e) {
			result = returnJson4Exception(e);
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * web前端登录操作
	 * @param request
	 * @param member
	 * @return
	 */
	@OpLog(optName="web前端登录",optKey="web用户登录",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,Member member){
		logger.info("member login,member info:"+member);
		String result = "";
		try {
			HttpSession session = request.getSession();
			//根据输入的用户名查询用户是否存在
			Member loginMember = memberService.queryByUserName(member.getmName());
			if(null!=loginMember){//用户存在
				//用户是否启用
					String pwd = DESUtils.decrypt(loginMember.getmPwd());//获取数据库中该用户的密码
					if(pwd.equals(member.getmPwd())){//如果密码一致，登录成功
						//查询该登录用户的权限信息
						session.setAttribute(Const.SESSION_MEMBER, loginMember);//用户信息
						session.setAttribute(Const.SESSION_MEMBER_ID, loginMember.getId());//用户信息
						session.setAttribute(Const.SESSION_MEMBER_NAME, loginMember.getmName());//用户名称
						//暂时和微信登录一起公用，本来微信不需要登录的，只需要获取微信的openid就好，暂时微信也需要登录
						session.setAttribute(Const.SESSION_WX_MEMBER, loginMember);
						result = returnJson4Success("登录成功！");
						logger.info("login success,user info:"+loginMember);
					}else{
						//密码错误
						result = returnJson4Fail("密码错误！");
					}
			}else{//用户不存在
				result = returnJson4Fail("该用户不存在！");
			}
		} catch (Exception e) {
			result = returnJson4Exception(e);
			logger.error(e.getMessage());
		}
		return result;
	}
	
	@OpLog(optName="web前端退出",optKey="web用户登录",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/loginOut",method=RequestMethod.GET)
	public void loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.getSession().removeAttribute(Const.SESSION_MEMBER);
		request.getSession().removeAttribute(Const.SESSION_MEMBER_ID);
		request.getSession().removeAttribute(Const.SESSION_MEMBER_NAME);
		response.sendRedirect(request.getContextPath()+"/m");
	}
	
	/**
	 * 保存会员的试卷信息
	 * @param request
	 * @param questionAndAnswer
	 * @param tpId
	 * @return
	 */
	@RequestMapping(value="/submitMyTp/{score}",method=RequestMethod.POST)
	@ResponseBody
	public String submitMyTp4Wx(HttpServletRequest request,
			@RequestParam String questionAndAnswer,@RequestParam String tpId,@PathVariable String score){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Member m = (Member)request.getSession().getAttribute(Const.SESSION_MEMBER);
			//微信交卷
			boolean r = memberService.submitMyTp4Web(m.getId(),tpId,questionAndAnswer,score);
			if(!r){
				result.put(Const.RESPONSE_CODE, false);
			}else{
				result.put(Const.RESPONSE_CODE, true);
			}
			result.put(Const.RESPONSE_MSG, wxMsgMap4ExamResult(getSubmitDicK(score)));
		} catch (Exception e) {
			logger.error("提交答案异常："+e.getMessage());
			return returnJson4Exception(e);
		}
		return returnJson4Custom(result);
	}
	
	/**
	 * 查询我的答题，根据交卷时间
	 * @param request
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/queryTp/{date}",method=RequestMethod.GET)
	public String queryTp4Web(HttpServletRequest request,@PathVariable String date,Model model){
		Member m = (Member)request.getSession().getAttribute(Const.SESSION_MEMBER);
		date = URLDecoder.decode(date);
		Map<String,Object> result = memberService.queryMyTpByMIdAndSubmitTime4Web(m.getId(),date);
		logger.info("跳转到我的试卷首页面，试卷信息："+result);
		model.addAttribute("tpqMap", result);
		return "web/member/mytp";
	}
	
}
