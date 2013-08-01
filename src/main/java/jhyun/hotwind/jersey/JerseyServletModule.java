package jhyun.hotwind.jersey;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jhyun.hotwind.config.AppConfig;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Jersey요청 처리를 위해서 web.xml 대신에 Guice 바인딩으로 HTTP요청을 JAX-RS으로 처리하도록.
 * 
 * ServletModule을 활용하여, Guice Module들으로 Servlet Mapping을 대신함.
 * 
 * app.jerset-modules에 지정된 클래스FQN들으로 Guice에 바인딩하여 Jersey 리소스 클래스로 활용. 이렇게 Guice에
 * 바인딩한 Jersey 리소스 클래스들을 또한 Guice을 통해서 초기화되고, 이들로부터 멤버들을 주입 받으므로, 아무런 추가 코드 없이
 * Guice으로 @Inject 멤버들을 활용할 수 있다.
 * 
 * @author jhyun
 * @since 2012-09-03
 * 
 * @see ServletModule
 * @see GuiceContainer
 * @link <a href="http://code.google.com/p/google-guice/wiki/ServletModule">
 *       ServletModule</a>
 */
public class JerseyServletModule extends ServletModule {
	private static Logger logger = LoggerFactory
			.getLogger(JerseyServletModule.class);

	@Override
	protected void configureServlets() {
		final List<Object> jerseyModuleClassFqns = AppConfig.load().getList(
				"app.jersey-modules", new ArrayList<Object>());
		bindJerseyModules(jerseyModuleClassFqns);
		//
		Configuration config = AppConfig.load();
		final Map<String, String> jerseyProperties = loadJerseyProperties(config);
		logger.debug(String.format("jersey-properties = %s", jerseyProperties));
		//
		final String jerseyServeUrlPattern = config.getString(
				"jersey-serve-url-pattern", "/*");
		filter(jerseyServeUrlPattern).through(GuiceContainer.class,
				jerseyProperties);
	}

	/** Jersey Servlet Filter 설정 로딩. */
	private Map<String, String> loadJerseyProperties(Configuration config) {
		final String pFilename = config.getString("jersey-properties-file",
				"jersey.properties");
		Properties ps = new Properties();
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(pFilename);
		if (in != null) {
			try {
				ps.load(in);
			} catch (IOException e) {
				logger.warn(String.format("LOAD FAIL = [%s]", pFilename));
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						logger.warn("CLOSE FAIL, WT...", e);
					}
			}
		} else {
			logger.debug(String.format("[%s] not found, skip.", pFilename));
		}
		return new HashMap<String, String>((Map) ps);
	}

	/**
	 * 모든 Jersey 리소스 클래스들을 등록. 실제로 모듈로서 이들을 활용하지 않고(인스턴스로 만들지도 않음.), 단지 바인딩만 하여
	 * 이후 요청에 있어서 처리에만 활용한다.
	 * 
	 * @param jerseyModuleClassFqns
	 */
	private void bindJerseyModules(final List<Object> jerseyModuleClassFqns) {
		for (final Object jerseyModuleClassFqn : jerseyModuleClassFqns) {
			try {
				Class<? extends Module> jerseyModuleClass = (Class<? extends Module>) Class
						.forName((String) jerseyModuleClassFqn);
				bind(jerseyModuleClass);
				logger.info(String.format("jersey-module bound -- %s",
						jerseyModuleClass));
			} catch (Exception exc) {
				logger.warn(String.format("jersey-module fail, skip", exc));
			}
		}
	}
}
