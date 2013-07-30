package jhyun.mybatis_with_guice.injections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jhyun.mybatis_with_guice.config.AppConfig;
import jhyun.mybatis_with_guice.servlets.DefaultWrapperServlet;

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
 * @see DefaultWrapperServlet
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
		Map<String, String> params = new HashMap<String, String>();
		/**
		 * <code>
		//params.put("com.sun.jersey.config.feature.ImplicitViewables", "true");
		//params.put("com.sun.jersey.config.property.packages", "net.cknudsen.jerseyexample.web");
		 </code>
		 */
		params.put("com.sun.jersey.config.feature.Redirect", "true");
		params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
		
		params.put("com.sun.jersey.config.property.JSPTemplatesBasePath", "/WEB-INF/jsp");
		
		// FIXME:
		params.put("com.sun.jersey.config.property.WebPageContentRegex",
	       "(/(public)/.*)");//|(/.*\\.jsp)|(/WEB-INF/.*\\.jsp)|(/WEB-INF/.*\\.jspf)|(/.*\\.html)|(/favicon\\.ico)|(/robots\\.txt)");
		
		
		
		// serving urls
		Configuration config = AppConfig.load();
		serveRegex(config.getString("default-serve-url-regex", "/public/.*"))
				.with(DefaultWrapperServlet.class);	// FIXME:
        filter("/*").through(GuiceContainer.class, params);

		/*
		serve(config.getString("jersey-serve-url-pattern", "/*")).with(
				GuiceContainer.class, params);
				*/
		
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
