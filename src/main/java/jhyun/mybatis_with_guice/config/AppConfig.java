package jhyun.mybatis_with_guice.config;

import java.net.URL;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppConfig {

	private static Log log = LogFactory.getLog(AppConfig.class);

	private static PropertiesConfiguration loaded;

	public static synchronized PropertiesConfiguration load() {
		if (loaded == null) {
			try {
				URL url = AppConfig.class.getResource("./app-config.properties");
				log.debug(String.format("app-config.properties url = %s", url));
				loaded = new PropertiesConfiguration(url);
			} catch (Exception e) {
				log.warn(
						"load 'app-config.properties' failed, this will use a empty properties.",
						e);
				loaded = new PropertiesConfiguration();
			}
		}
		return loaded;
	}

}
