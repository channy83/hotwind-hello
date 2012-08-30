package jhyun.mybatis_with_guice.injections;

import jhyun.mybatis_with_guice.config.AppConfig;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.mybatis.guice.XMLMyBatisModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Guice Injector.
 * 
 * @author jhyun
 * @since 2012-08-30
 */
public class Guicer {
	private static Logger logger = LoggerFactory.getLogger(Guicer.class);
	private static Injector injector;
	private static PropertiesConfiguration appConfig;

	static {
		appConfig = AppConfig.load();
		final String environment = appConfig.getString("environment",
				"development");
		logger.debug(String.format("selected-env=[%s]", environment));
		injector = Guice.createInjector(new XMLMyBatisModule() {
			@Override
			protected void initialize() {
				setEnvironmentId(environment);
				setClassPathResource("mybatis-config.xml");
			}
		});
	}

	/** use this! */
	public static Injector get() {
		return injector;
	}

}
