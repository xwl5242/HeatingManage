package com.zhx.modules.wx.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

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
import com.zhx.modules.common.Global;
import com.zhx.modules.constants.Const;
import com.zhx.modules.member.bean.Member;
import com.zhx.modules.utils.DESUtils;
import com.zhx.modules.utils.DateUtils;
import com.zhx.modules.utils.UUIDGenerator;
import com.zhx.modules.wx.service.WxService;
import com.zhx.wechat.base.WxConfig;
import com.zhx.wechat.base.WxSessionKeys;

@Controller
@RequestMapping("/wx")
public class WxController extends WxBaseController {
	
	private Logger logger = LoggerFactory.getLogger(WxController.class);

	@Autowired
	private WxService wxService;
	
	private WxMpService wxMpService = WxConfig.wxMpService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public void indexHome(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		response.sendRedirect(Global.getWxOauth2Url4RealUrl("toIndex"));
		response.sendRedirect(request.getContextPath()+"/wx/login");
	}
	
	@RequestMapping(value="/toIndex",method=RequestMethod.GET)
	public String index(){
		return "wechat/index";
	}
	
	/**
	 * 微信端系统统一入口
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		String retUrl="";
		try{
			HttpSession session = request.getSession();
			//获取请求中的code
			String code = request.getParameter("code");
			logger.info("微信请求返回code，用来获取授权凭证access_token。code="+code);
			if (code == null) {
				response.sendRedirect(Global.getWxOauth2Url4RealUrl("toIndex"));
			}
			//获取凭证
			WxMpOAuth2AccessToken oAuth2AccessToken = wxMpService
					.oauth2getAccessToken(code);
			//获取openid
			String openid = oAuth2AccessToken.getOpenId();
			logger.info("当前微信用户在此微信号中的唯一识别标志openId="+openid);
			//获取微信用户信息
			WxMpUser wxMpUser = null;
			if ("snsapi_base".equals(oAuth2AccessToken.getScope())) {
				wxMpUser = wxMpService.userInfo(openid, null);
			} else if ("snsapi_userinfo".equals(oAuth2AccessToken.getScope())) {
				wxMpUser = wxMpService.oauth2getUserInfo(oAuth2AccessToken,null);
			}
			logger.info("当前微信用户基本信息："+wxMpUser);
			//微信用户信息存放到session中
			session.setAttribute(WxSessionKeys.OPEN_ID, openid);
			session.setAttribute(WxSessionKeys.WX_USER, wxMpUser);
			//根据openId查询该微信用户是否注册过
			Member member = wxService.queryMemberByOpenId(openid);
			logger.info("根据openId查询该微信用户是否注册过:"+member);
			//该微信用户没有注册
			if(null==member){
				retUrl="wechat/register";
			}else{//已经注册
				session.setAttribute(Const.SESSION_WX_MEMBER, member);
				retUrl="wechat/index";//首页
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return retUrl;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(){
		return "wechat/login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register(){
		return "wechat/register";
	}
	
	/**
	 * 注册
	 * @param request
	 * @param member
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request,Member member){
		logger.info("=======微信注册开始=======");
		int ret = 0;
		try{
			WxMpUser wxUser = (WxMpUser)request.getSession().getAttribute(WxSessionKeys.WX_USER);
			logger.info("当前session中的微信用户信息："+wxUser);
			member.setmType("1");
			member.setId(UUIDGenerator.getUUID());
			member.setmUserName(member.getmName());
			if(null!=wxUser){
				member.setmUserName(wxUser.getNickname());
				member.setmWxOpenid(wxUser.getOpenId());
				member.setmWxHeadimg(wxUser.getHeadImgUrl());
			}
			member.setCreateTime(DateUtils.date2yyyyMMddHHmmssStr(null));
			member.setmPwd(DESUtils.encrypt(member.getmPwd()));
			logger.info("注册用户信息："+member);
			ret = wxService.registerMember4Wx(member);
			if(ret==1){
				request.getSession().setAttribute(Const.SESSION_WX_MEMBER, member);
				logger.info("注册成功，新增会员信息写入session");
			}
		}catch(Exception e){
			logger.error("微信注册异常："+e.getMessage());
		}
		logger.info("=======微信注册结束=======");
		return (ret==1?returnJson4Success():returnJson4Fail());
	}
	
	/**
	 * 跳转注册操作结果页面
	 * @param request
	 * @param opt 操作
	 * @param type 0：失败；1：成功
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/msg/{opt}/{type}",method=RequestMethod.GET)
	public String msg(HttpServletRequest request,@PathVariable String opt,
			@PathVariable String type,String reqParam,Model model){
		model.addAttribute(wxMsgMap(opt,type));
		return "wechat/result/"+type;
	}
	
	@RequestMapping(value="/examresult/{score}",method=RequestMethod.GET)
	public String examresult(@PathVariable String score,Model model){
		model.addAttribute(wxMsgMap4ExamResult(getSubmitDicK(score)));
		model.addAttribute("score",score);
		return "wechat/result/examresult";
	}
	
	/**
	 * 跳转到试卷首页
	 * @param model
	 * @param type
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value="/tpIndex/{type}",method=RequestMethod.GET)
	public String tpIndex(Model model,@PathVariable String type) {
		Map<String,Object> result = wxService.queryTestPaperAndQuestion4Index(type);
		logger.info("跳转到试卷首页面，试卷信息："+result);
		model.addAttribute("tpqMap", result);
		return "wechat/testpaper/index";
	}
	
	/**
	 * 个人信息页
	 * @return
	 */
	@RequestMapping(value="/person",method=RequestMethod.GET)
	public String person(){
		return "wechat/person";
	}
	
	/**
	 * 交卷成功，跳转到分数页面
	 * @param model
	 * @param score
	 * @return
	 */
	@RequestMapping(value="/submitMyTp/{score}",method=RequestMethod.POST)
	@ResponseBody
	public String submitMyTp4Wx(HttpServletRequest request,
			@RequestParam String questionAndAnswer,@RequestParam String tpId,@PathVariable String score){
		String result = "";
		try {
			Member m = (Member)request.getSession().getAttribute(Const.SESSION_WX_MEMBER);
			//微信交卷
			boolean ret = wxService.submitMyTp4Wx(m.getId(),tpId,questionAndAnswer,score);
			result = ret?returnJson4Success():returnJson4Fail();
		} catch (Exception e) {
			logger.error("提交答案异常："+e.getMessage());
			return returnJson4Exception(e);
		}
		return result;
	}
	
	/**
	 * 微信端更新会员信息
	 * @param request
	 * @param member
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateMember",method=RequestMethod.POST)
	@ResponseBody
	public String updateMember(HttpServletRequest request,Member member) {
		logger.info("=======微信端更新会员信息开始=======");
		Member ret = null;
		try{
			HttpSession session = request.getSession();
			ret = wxService.updateMember4Wx(member);
			if(null!=ret){
				session.removeAttribute(Const.SESSION_WX_MEMBER);
				session.setAttribute(Const.SESSION_WX_MEMBER, ret);
			}
		}catch(Exception e){
			logger.error("微信端更新会员信息异常："+e.getMessage());
		}
		logger.info("=======微信端更新会员信息结束=======");
		return (null!=ret?returnJson4Success():returnJson4Fail());
	}
	
	/**
	 * 跳转到我的答题页面
	 * @param model
	 * @param type
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value="/myTpList",method=RequestMethod.GET)
	public String myTpList(HttpServletRequest request,Model model){
		Member m = (Member)request.getSession().getAttribute(Const.SESSION_WX_MEMBER);
		List<Map<String,Object>> result = wxService.queryMyTpList(m.getId());
		if(result.size()==0){
			return "wechat/result/tplistresult";
		}else{
			logger.info("跳转到我的答题页面，试卷信息："+result);
			model.addAttribute("tpqList", result);
			return "wechat/testpaper/list";
		}
	}
	
	/**
	 * 查询我的答题，根据交卷时间
	 * @param request
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/queryTp/{date}",method=RequestMethod.GET)
	public String queryTp4Wx(HttpServletRequest request,@PathVariable String date,Model model){
		Member m = (Member)request.getSession().getAttribute(Const.SESSION_WX_MEMBER);
		date = URLDecoder.decode(date);
		Map<String,Object> result = wxService.queryMyTpByMIdAndSubmitTime4Wx(m.getId(),date);
		logger.info("跳转到我的试卷首页面，试卷信息："+result);
		model.addAttribute("tpqMap", result);
		return "wechat/testpaper/myTp";
	}
	
	/**
	 * 外部接口，根据身份证查询是否答题
	 */
	@RequestMapping(value="/isPassed",method=RequestMethod.GET)
	@ResponseBody
	public String isPassed(@RequestParam String cId){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			boolean r = wxService.isPassed(cId);
			result.put(Const.RESPONSE_CODE, "1000");
			result.put(Const.RESPONSE_MSG, "查询成功！");
			result.put("isPassed", r);
		}catch(Exception e){
			result.put(Const.RESPONSE_CODE, "1001");
			result.put(Const.RESPONSE_MSG, e.getMessage());
		}
		return returnJson4Custom(result);
	}
}
