package com.kjpmj.app.netty.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * 설정처리 유틸 클래스
 * </pre>
 * 
 * @author jhkim
 * @since 2016. 11. 9.
 */
public class ConfigManager {

	public static final String CONFIG_P = "properties";
	public static final String CONFIG_X = "xml";
	public static final String CONFIG_D = "db";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	

	private static ConfigManager configManager = null;

	private static HashMap<String, String> configMap = new HashMap<String, String>();

	public void setValue(String key, String value) {
		configMap.put(key, value);
	}

	public String getValue(String key) {
		return configMap.get(key);
	}

	public static ConfigManager getConfigManager() {
		if (configManager == null) {
			configManager = new ConfigManager();
		}

		return configManager;
	}

	/**
	 * <pre>
	 * 설정 파일 로드 및 타입 별 파싱 메소드 호출
	 * </pre>
	 * 
	 * @param type
	 *            ( XML, DB, PROPERTIES )
	 * @param path
	 *            설정파일경로
	 */
	public void load(String type, String path) throws Exception {

		logger.debug("type:" + type + " " + "path:" + path);

		if (type.equals(CONFIG_P)) {
			loadProperties(path);
		} else if (type.equals(CONFIG_X)) {
			loadXML(path);
		}

	}

	/**
	 * <pre>
	 * PROPERTIES 처리 메소드
	 * </pre>
	 * 
	 * @param path
	 *            설정파일경로
	 */
	public void loadProperties(String path) throws Exception {
		Properties prop = new Properties();
		prop.load(Resources.getResourceAsStream(path));

		Set set;
		String key;

		set = prop.keySet();
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			key = (String) itr.next();
			setValue(key, prop.getProperty(key));
			logger.debug("Key[" + key + "]" + " " + "value[" + prop.getProperty(key) + "]");
		}
	}

	

	/**
	 * <pre>
	 * XML 설정처리 메소드
	 * </pre>
	 * 
	 * @param path
	 *            설정파일경로
	 */
	public void loadXML(String path) {
		Properties properties = new Properties();

		logger.info("======================Load Configuration from XML======================");
		try {

			/*String absolutePath = this.getClass().getResource("").getPath();
			System.out.println("absolutePath:" + absolutePath);*/

			InputStream fis = this.getClass().getResourceAsStream(path);

			properties.loadFromXML(fis);
			// properties.

			properties.keySet().iterator();

			Enumeration<?> proNms = properties.propertyNames();
			while (proNms.hasMoreElements()) {
				String proKey = (String) proNms.nextElement();
				String proValue = properties.getProperty(proKey);

				setValue(proKey, proValue);

				logger.info("KEY:" + proKey + " " + "VALUE:" + proValue);
			}

			logger.info("==================================================================");

		} catch (InvalidPropertiesFormatException e) {
			logger.error("ConfigManager loadXml 실패. InvalidPropertiesFormat 예외");
			logger.error("",e);
		} catch (IOException e) {
			logger.error("ConfigManager loadXml 실패. IOException");
			logger.error("",e);
		}
	}

}
