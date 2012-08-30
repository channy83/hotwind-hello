package jhyun.mybatis_with_guice.config;

import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * load base config files.
 * 
 * @author jhyun
 * @since 2012-08-30
 * 
 */
public class AppConfig {

	private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

	private static Configuration loaded;

	public static synchronized Configuration load() {
		if (loaded == null) {
			DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
			URL url = AppConfig.class.getClassLoader().getResource(
					"app-config-files.xml");
			configBuilder.setURL(url);
			try {
				loaded = configBuilder.getConfiguration(true);
			} catch (ConfigurationException e) {
				logger.warn(
						"load 'app-config-files.xml' failed, this will use a empty properties.",
						e);
				loaded = new PropertiesConfiguration();
			}
		}
		return loaded;
	}

}
