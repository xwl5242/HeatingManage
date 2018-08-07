package com.zhx.modules.wxpay;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wxpay.sdk.WXPayConstants;
import com.wxpay.sdk.WXPayConstants.SignType;
import com.wxpay.sdk.WXPayService;
import com.wxpay.sdk.WXPayUtil;
import com.zhx.modules.utils.ObjectMapperHelper;

/**
 * 
 * @author xlw
 *
 */
@Controller
@RequestMapping("wxpay")
public class WXPayController {
	
	/**
	 * 微信统一下单
	 * @param body
	 * @param total_fee
	 * @param trade_type
	 * @param spbill_create_ip
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/unifiedorder",method=RequestMethod.POST)
	@ResponseBody
	public String unifiedorder(String body,String total_fee,String trade_type,String spbill_create_ip){
		String result = "";
		try {
			result = toPaySignStr(WXPayService.unifiedorder(body, total_fee, trade_type, spbill_create_ip));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 下单成功后，生成前端支付需要的请求参数以及签名信息
	 * @param om
	 * @return
	 * @throws Exception
	 */
	private String toPaySignStr(Map<String,String> om) throws Exception{
		Map<String,String> pm = new HashMap<String, String>();
		pm.put("appid", om.get("appid"));
		pm.put("partnerid", om.get("mch_id"));
		pm.put("prepayid", om.get("prepay_id"));
		pm.put("package", "Sign=WXPay");
		pm.put("noncestr", WXPayUtil.generateNonceStr());
		pm.put("timestamp", System.currentTimeMillis()/1000+"");
		pm.put("sign", WXPayUtil.generateSignature(pm, WXPayService.config.getKey(), SignType.HMACSHA256));
		pm.put("returncode", om.get("return_code"));
		return ObjectMapperHelper.om.writeValueAsString(pm);
	}
	
	/**
	 * 支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
	 * 对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，尽可能提高通知的成功率，但微信不保证通知最终能成功。
	 *  （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）
	 * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
	 * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
	 *   在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
	 * 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notify",method=RequestMethod.POST)
	@ResponseBody
	public String notify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resXml="";
		//获取request中的返回信息
		InputStream inputStream = request.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len=0;
		while((len=inputStream.read(buffer))!=-1){
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inputStream.close();
		//获取微信调用我们notify_url的返回信息
		String result = new String(outStream.toByteArray(),"utf-8");
		//分析微信调用返回的结果
		Map<String,String> resultMap = WXPayUtil.xmlToMap(result);
		//支付成功
		if(resultMap.get("result_code").toUpperCase().equals(WXPayConstants.SUCCESS)){
			//验证签名是否正确
			if(WXPayUtil.isSignatureValid(resultMap, WXPayService.config.getKey(),SignType.HMACSHA256)){
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
                resXml = "<xml><return_code><![CDATA[SUCCESS]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";  
                //TODO:处理业务 -修改订单支付状态 ,需要验证金额是否正确
			}	
            // 处理业务完毕  
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
            out.write(resXml.getBytes());  
            out.flush();  
            out.close();
		}
		return resXml;
	}
	
}
