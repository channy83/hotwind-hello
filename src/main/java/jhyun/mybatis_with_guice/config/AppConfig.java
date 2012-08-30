package jhyun.mybatis_with_guice.config;

import java.net.URL;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig {

	private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

	private static PropertiesConfiguration loaded;

	public static synchronized PropertiesConfiguration load() {
		if (loaded == null) {
			try {
				URL url = AppConfig.class
						.getResource("./app-config.properties");
				logger.debug(String.format("app-config.properties url = %s",
						url));
				loaded = new PropertiesConfiguration(url);
			} catch (Exception e) {
				logger.warn(
						"load 'app-config.properties' failed, this will use a empty properties.",
						e);
				loaded = new PropertiesConfiguration();
			}
		}
		return loaded;
	}

}
