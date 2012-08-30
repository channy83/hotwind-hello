package jhyun.mybatis_with_guice.injections;

import jhyun.mybatis_with_guice.config.AppConfig;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.guice.XMLMyBatisModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Guicer {
	private static Log log = LogFactory.getLog(Guicer.class);
	private static Injector injector;
	private static PropertiesConfiguration appConfig;

	static {
		appConfig = AppConfig.load();
		final String environment = appConfig.getString("environment",
				"development");
		log.info(String.format("selected-env=[%s]", environment));
		injector = Guice.createInjector(new XMLMyBatisModule() {
			@Override
			protected void initialize() {
				setEnvironmentId(environment);
				setClassPathResource("mybatis-config.xml");
			}
		});
	}

	public static Injector get() {
		return injector;
	}

}
