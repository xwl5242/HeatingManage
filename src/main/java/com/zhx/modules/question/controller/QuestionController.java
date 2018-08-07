package com.zhx.modules.question.controller;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zhx.log.OpLog;
import com.zhx.modules.constants.Const;
import com.zhx.modules.frames.BaseController;
import com.zhx.modules.question.bean.Question;
import com.zhx.modules.question.service.QuestionService;
import com.zhx.modules.utils.CommonDownload;
import com.zhx.modules.utils.CommonExcelExport;
import com.zhx.modules.utils.CommonExcelImport;

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	private QuestionService questionService;
	
	/**
	 * 跳转到问题列表页面
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String questionList(){
		return "web/question/list";
	}
	
	/**
	 * 查询问题列表
	 * @return
	 */
	@OpLog(optName="查询问题列表",optKey="问题管理",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public String questionList(HttpServletRequest request,@RequestParam Map<String,String> params){
		logger.info("查询问题列表页面：params="+params);
		params.putAll(paramsStr2Map(params.get("params")));
		Map<String,Object> result = questionService.queryList(params);
		return returnJson4Custom(result);
	}
	
	/**
	 * 跳转添加到问题页面
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){
		return "web/question/add";
	}
	
	/**
	 * 新增问题
	 * @return
	 */
	@OpLog(optName="新增问题",optKey="问题管理",optType=Const.OPLOG_TYPE_INSERT)
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,Question question){
		logger.info("新增问题：question："+question);
		int result = 0;
		try{
			question.setCreator(getUserId(request));
			result = questionService.saveQuestion(question);
		}catch(Exception e){
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result==1?returnJson4Success("新增问题成功！"):returnJson4Fail("新增问题失败！");
	}
	
	/**
	 * 跳转到修改问题页面
	 * @return
	 */
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public String edit(@PathVariable String id,Model model){
		logger.info("跳转到修改问题页面:id="+id);
		Question question = questionService.queryById(id);
		model.addAttribute("questionId",id);
		model.addAttribute("question",question);
		return "web/question/edit";
	}
	
	/**
	 * 修改问题
	 * @param question
	 * @return
	 */
	@OpLog(optName="修改问题",optKey="问题管理",optType=Const.OPLOG_TYPE_UPDATE)
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,Question question){
		logger.info("修改问题：question："+question);
		int result = 0;
		try{
			question.setUpdator(getUserId(request));
			result = questionService.editQuestion(question);
		}catch(Exception e){
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result==1?returnJson4Success("修改问题成功！"):returnJson4Fail("修改问题失败！");
	}
	
	/**
	 * 删除
	 * @param request
	 * @param params
	 * @return
	 */
	@OpLog(optName="删除问题信息",optKey="问题管理",optType=Const.OPLOG_TYPE_DELETE)
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	@ResponseBody
	public String remove(HttpServletRequest request,@RequestParam Map<String,String> params){
		logger.info("删除问题:params="+params);
		boolean result = false;
		try {
			result = questionService.removeQuestions(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return returnJson4Exception(e);
		}
		return result?returnJson4Success("删除问题成功！"):returnJson4Fail("删除问题失败！");
	}
	
	/**
	 * 问题标题是否唯一
	 * @param request
	 * @param qTitle
	 * @return
	 */
	@OpLog(optName="问题标题是否唯一",optKey="问题管理",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value="/titleUnique",method=RequestMethod.POST)
	@ResponseBody
	public String titleUnique(HttpServletRequest request,String qTitle){
		logger.info("问题标题是否唯一:qTitle:"+qTitle);
		Question q = questionService.queryByQTitle(qTitle);
		if(null==q){
			return returnJson4Success();
		}else {
			return returnJson4Fail();
		}
	}
	
	/**
	 * 导出
	 * @param params
	 * @param response
	 */
	@OpLog(optName="导出问题记录",optKey="问题管理",optType=Const.OPLOG_TYPE_SELECT)
	@RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(@RequestParam Map<String, String> params, HttpServletResponse response) {
        logger.info("导出问题列表EXCEL");
        String workBookName = "问题列表";//文件名
        Map<String, String> cellName = new LinkedHashMap<>();//列标题(有序)
        cellName.put("id", "问题主键");
        cellName.put("q_title", "问题名称");
        cellName.put("q_type", "问题类型");
        cellName.put("q_score", "问题分数");
        cellName.put("q_sel_item", "选项");
        cellName.put("q_answer", "答案");
        cellName.put("q_aw_keyword", "答案关键字");
        //列值
        List<Map<String, Object>> cellValues = null;
        try {
        	params = paramsStr2Map(params.get("pramas"));
            cellValues = questionService.exportQuestion(params);
            CommonExcelExport.excelExport(response,cellName,cellValues,workBookName);
        } catch (Exception e) {
            logger.error("导出问题列表EXCEL异常" + e.getMessage());
        }
    }
	
	/**
	 * 跳转到上传问题excel页面
	 * @return
	 */
	@RequestMapping(value="/uploadQuestion",method=RequestMethod.GET)
	public String uploadQuestion(){
		return "web/question/uploadQuestion";
	}
	
	@RequestMapping(value="/downloadTemplete",method=RequestMethod.GET)
	@ResponseBody
	public void downloadTemplete(HttpServletResponse response){
		CommonDownload.download(response);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadQuestion",method=RequestMethod.POST)
	@ResponseBody
	public String uploadQuestion(HttpServletRequest request) throws Exception{
		boolean result = false;
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
			logger.info("通过传统方式form表单提交方式导入excel文件！导入问题excel");  
			//读取excel中的文件  
			InputStream in =null;  
			List<List<Object>> listob = null;  
			MultipartFile file = multipartRequest.getFile("upfile");  
			if(file.isEmpty()){  
				throw new Exception("文件不存在！");  
			}  
			in = file.getInputStream();  
			listob = CommonExcelImport.getBankListByExcel(in,file.getOriginalFilename());  
			in.close();  
			result = questionService.saveQuestion4Import(listob, getUserId(request));
		}catch(Exception e){
			return returnJson4Exception(e);
		}
        return result?returnJson4Success():returnJson4Fail();
	}
	
}
