package com.zhx.wechat.base;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;

public final class WxConfig {

	public final static String WX_CONST_PROPERTY = "wxConst.property";

	public static WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();

	public static WxMpService wxMpService = new WxMpServiceImpl();

	public static String APPID;

	public static String SECRET;

	static {
		PropertiesConfiguration config = null;
		try {
			config = new PropertiesConfiguration(WX_CONST_PROPERTY);
			// Automatic Reloading 自动重新加载
			config.setReloadingStrategy(new FileChangedReloadingStrategy());

			APPID = config.getString("appid");
			if (StringUtils.isEmpty(APPID)) {
				throw new RuntimeException("APPID不能为空");
			}
			wxMpConfigStorage.setAppId(APPID);
			SECRET = config.getString("secret");
			if (StringUtils.isEmpty(SECRET)) {
				throw new RuntimeException("SECRET不能为空");
			}
			wxMpConfigStorage.setSecret(SECRET);
			wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		} catch (ConfigurationException e) {
		}
	}
}
