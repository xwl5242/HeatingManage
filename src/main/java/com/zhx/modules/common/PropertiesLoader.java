package com.zhx.modules.common;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

public class PropertiesLoader {
	
	public final Properties properties;
	
	public PropertiesLoader(String... locations){
		properties = loadProperties(locations);
	}

	public Properties getProperties(){
		return properties;
	}
	
	private Properties loadProperties(String[] locations) {
		Properties pros = new Properties();
		try {
			for (String location : locations) {
				InputStream inStream = new ClassPathResource(location)
						.getInputStream();
				pros.load(inStream);
			}
		} catch (Exception e) {
		}
		return pros;
	}
	
	public String getProperty(String key){
		String value = null;
		if(StringUtils.isNotEmpty(key)){
			value = properties.getProperty(key);
		}
		return value;
	}
}
