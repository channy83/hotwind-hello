package jhyun.mybatis_with_guice.injections;

import jhyun.mybatis_with_guice.config.AppConfig;

import org.apache.commons.configuration.Configuration;
import org.mybatis.guice.XMLMyBatisModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple MyBatis + Guice Configuration By XML Module.
 * 
 * @author jhyun
 * @since 2012-08-30
 */
public class MyBatisXmlModule extends XMLMyBatisModule {
	private static Logger logger = LoggerFactory
			.getLogger(MyBatisXmlModule.class);

	@Override
	protected void initialize() {
		Configuration appConfig = AppConfig.load();
		final String environment = appConfig.getString("app.environment",
				"development");
		logger.debug(String.format("selected-env=[%s]", environment));

		setEnvironmentId(environment);
		setClassPathResource("mybatis-config.xml");
	}
}
