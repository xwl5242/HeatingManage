package com.zhx.modules.testpaper.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.zhx.modules.testpaper.bean.TestPaper;
import com.zhx.modules.testpaper.service.TestPaperService;
import com.zhx.modules.utils.ObjectMapperHelper;

@Controller
@RequestMapping("/testpaper")
public class TestPaperController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(TestPaperController.class);
	
	@Autowired
	private TestPaperService testPaperService;
	
	/**
	 * 跳转到试卷列表页面
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String testpaperList(){
		return "web/testpaper/list";
	}
	
	/**
	 * 查询试卷列表
	 * @return
	 */
	@OpLog(optName="查询试卷列表",optKey="试卷管理",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public String testpaperList(HttpServletRequest request,@RequestParam Map<String,String> params){
		logger.info("查询试卷列表页面：params="+params);
		params.putAll(paramsStr2Map(params.get("params")));
		Map<String,Object> result = testPaperService.queryList(params);
		return returnJson4Custom(result);
	}
	
	/**
	 * 跳转添加到试卷页面
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		int count = testPaperService.queryAllQuestionCountByType("0");
		model.addAttribute("quesCount", count);
		return "web/testpaper/add";
	}
	
	/**
	 * 新增试卷
	 * @return
	 */
	@OpLog(optName="新增试卷",optKey="试卷管理",optType=Const.OPLOG_TYPE_INSERT)
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,TestPaper testpaper){
		logger.info("新增试卷：testpaper："+testpaper);
		int result = 0;
		try{
			testpaper.setCreator(getUserId(request));
			result = testPaperService.saveTestPaper(testpaper);
		}catch(Exception e){
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result==1?returnJson4Success("新增试卷成功！"):returnJson4Fail("新增试卷失败！");
	}
	
	/**
	 * 跳转到修改试卷页面
	 * @return
	 */
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public String edit(@PathVariable String id,Model model){
		logger.info("跳转到修改试卷页面:id="+id);
		TestPaper testpaper = testPaperService.queryById(id);
		int count = testPaperService.queryAllQuestionCountByType("0");
		model.addAttribute("quesCount", count);
		model.addAttribute("tp",testpaper);
		return "web/testpaper/edit";
	}
	
	/**
	 * 修改试卷
	 * @param testpaper
	 * @return
	 */
	@OpLog(optName="修改试卷",optKey="试卷管理",optType=Const.OPLOG_TYPE_UPDATE)
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,TestPaper testpaper,String isRefreshQuestion){
		logger.info("修改试卷：testpaper："+testpaper);
		int result = 0;
		try{
			testpaper.setUpdator(getUserId(request));
			result = testPaperService.editTestPaper(testpaper,"1".equals(isRefreshQuestion)?true:false);
		}catch(Exception e){
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result==1?returnJson4Success("修改试卷成功！"):returnJson4Fail("修改试卷失败！");
	}
	
	/**
	 * 删除
	 * @param request
	 * @param params
	 * @return
	 */
	@OpLog(optName="删除试卷信息",optKey="试卷管理",optType=Const.OPLOG_TYPE_DELETE)
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public String remove(HttpServletRequest request,@PathVariable String id){
		logger.info("删除试卷:tpId="+id);
		boolean result = false;
		try {
			result = testPaperService.removeTestPapers(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result?returnJson4Success("删除试卷成功！"):returnJson4Fail("删除试卷失败！");
	}
	
	/**
	 * 试卷标题是否唯一
	 * @param request
	 * @param qTitle
	 * @return
	 */
	@OpLog(optName="试卷名称是否唯一",optKey="试卷管理",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/tpNameUnique",method=RequestMethod.POST)
	@ResponseBody
	public String titleUnique(HttpServletRequest request,String tpName){
		logger.info("试卷标题是否唯一:qTitle:"+tpName);
		TestPaper q = testPaperService.queryByTpName(tpName);
		if(null==q){
			return returnJson4Success();
		}else {
			return returnJson4Fail();
		}
	}
	
	/**
	 * 试卷标题是否唯一
	 * @param request
	 * @param qTitle
	 * @return
	 */
	@OpLog(optName="是否发布",optKey="试卷管理",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/isPublish/{isPublish}",method=RequestMethod.GET)
	@ResponseBody
	public String queryIsPublish(@PathVariable String isPublish){
		logger.info("试卷是否发布:isPublish:"+isPublish);
		TestPaper q = testPaperService.queryByIsPublish(isPublish);
		if(null==q){
			return returnJson4Success();
		}else {
			return returnJson4Fail();
		}
	}
	
	/**
	 * 发布会议
	 * @param 
	 * @return
	 */
	@OpLog(optName="发布试卷",optKey="试卷管理",optType=Const.OPLOG_TYPE_UPDATE)
	@RequestMapping(value="/publish/{id}/{status}",method=RequestMethod.PUT)
	@ResponseBody
	public String publish(@PathVariable String id,@PathVariable String status){
		logger.info("发布或取消发布试卷:id:"+id+",status:"+status);
		int result = 0;
		try {
			result = testPaperService.publishTestPaper(id,status);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result==1?returnJson4Success():returnJson4Fail();
	}
	
	/**
	 * 跳转添加分配题目页面
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="/grant/{id}",method=RequestMethod.GET)
	public String grant(@PathVariable String id,Model model) throws JsonProcessingException{
		List<Map<String,Object>> tpqList = testPaperService.queryTestPaperQuestion(id);
		model.addAttribute("tpId", id);
		model.addAttribute("tpqList", ObjectMapperHelper.om.writeValueAsString(tpqList));
		return "web/testpaper/grant";
	}
	
	/**
	 * 跳转添加分配题目页面
	 * @return
	 */
	@RequestMapping(value="/addQuestion/{type}/{ids}",method=RequestMethod.GET)
	public String addQuestion(@PathVariable String type,@PathVariable String ids,Model model){
		model.addAttribute("type",type);
		model.addAttribute("hasIds",ids);
		return "web/testpaper/addQuestion";
	}
	
	/**
	 * 试卷分配题目
	 * @param request
	 * @param params
	 * @return
	 */
	@OpLog(optName="试卷分配题目",optKey="试卷管理",optType=Const.OPLOG_TYPE_UPDATE)
	@RequestMapping(value="/grant",method=RequestMethod.POST)
	@ResponseBody
	public String publish(HttpServletRequest request,String tpId,String qtMap){
		logger.info("发布试卷:");
		boolean result = false;
		try {
			result = testPaperService.grantTestPaper(tpId,qtMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result?returnJson4Success("试卷分配成功！"):returnJson4Fail("试卷分配失败！");
	}
	
	/**
	 * 跳转到预览页面
	 * @return
	 */
	@RequestMapping(value="/preview/{tpId}/{type}",method=RequestMethod.GET)
	public String preview(@PathVariable String tpId,@PathVariable String type,Model model) {
		model.addAttribute("tpId", tpId);
		model.addAttribute("type", type);
		return "web/testpaper/preView";
	}
	
	/**
	 * 预览前查询数据
	 * @param qtMap
	 * @return
	 */
	@OpLog(optName="试卷预览",optKey="试卷管理",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/preview",method=RequestMethod.GET)
	@ResponseBody
	public String preview(@RequestParam String qtMap,@RequestParam String tpId) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> ret = testPaperService.preView(qtMap,tpId);
		result.put(Const.RESPONSE_CODE, true);
		result.put("qtMap", ret);
		return returnJson4Custom(result);
	}
}
