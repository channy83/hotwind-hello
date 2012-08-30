package jhyun.mybatis_with_guice.injections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jhyun.mybatis_with_guice.config.AppConfig;
import jhyun.mybatis_with_guice.servlets.DefaultWrapperServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

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
		//
		serveRegex("/public/.*").with(DefaultWrapperServlet.class);
		serve("/*").with(GuiceContainer.class, params);
	}

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
