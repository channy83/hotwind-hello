package jhyun.mybatis_with_guice.injections;

import jhyun.mybatis_with_guice.config.AppConfig;

import org.apache.commons.configuration.Configuration;
import org.mybatis.guice.XMLMyBatisModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple MyBatis + Guice Configuration By XML Module.
 * 
 * Guicer의 의해서 생성된다. 상속을 통한 Injection의 용도도 있고(SqlMapper, SqlSession등
 * MyBatis특유의), 설정파일을 읽어 MyBatis을 초기화하는 목적도 있다.
 * 
 * 여기서 SqlMapper등의 Injection을 위해서는 XMLMyBatisModule을 상속하는것으로.
 * 
 * 설정 파일을 읽고, 기본 환경을 설정하는것은 initialize 메서드에서.
 * 
 * @author jhyun
 * @since 2012-08-30
 * 
 * @see Guicer
 * @see AppConfig
 * @see XMLMyBatisModule
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
