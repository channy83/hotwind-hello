package jhyun.mybatis_with_guice.injections;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceJerseyListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guicer.get();
	}

}
