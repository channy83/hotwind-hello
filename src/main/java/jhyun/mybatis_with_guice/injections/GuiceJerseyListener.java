package jhyun.mybatis_with_guice.injections;

import javax.servlet.ServletContextListener;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * web.xml에서 Context Listener으로서 지정하며, 이를 통해 기본 Injector을 생성하고 이를 Servlet
 * Context와 연관짓는다.
 * 
 * 
 * @author jhyun
 * @since 2012-09-03
 * 
 * @see ServletContextListener
 * @see GuiceServletContextListener
 * @see Guicer
 */
public class GuiceJerseyListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guicer.get();
	}

}
