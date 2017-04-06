package com.sec.mis.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 读取配置信息
 */
public class SysConf {
	private static final String PATH = "SysConf.properties";
	private static PropertiesConfiguration properties = null;

	static {
		try {
			properties = new PropertiesConfiguration();
			properties.setEncoding("UTF-8");
			properties.load(PATH);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 得到一个整数属性
	 * 
	 * @param key
	 *            String
	 * @return Integer
	 * */
	public static Integer getInt(String key) {
		return properties.getInt(key);
	}

	/**
	 * <p>
	 * 得到一个长整数属性
	 * 
	 * @param key
	 *            String
	 * @return Integer
	 * */
	public static Long getLong(String key) {
		return properties.getLong(key);
	}

	/**
	 * <p>
	 * 从property中得到一个属性
	 * 
	 * @param key
	 *            String
	 * @return String
	 */
	public static String get(String key) {
		return properties.getString(key);
	}

	/**
	 * <p>
	 * 从property中得到一个属性
	 * 
	 * @param param
	 *            String
	 * @param String
	 *            default value
	 * @return String
	 */
	public String get(String key, String defaultValue) {
		return properties.getString(key, defaultValue);
	}

	public static void main(String[] args) {
		System.out.println(SysConf.get("env.mode"));
	}
}
