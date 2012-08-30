package jhyun.mybatis_with_guice.injections;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Guice Injector.
 * 
 * @author jhyun
 * @since 2012-08-30
 */
public class Guicer {
	private static Injector injector;

	static {
		injector = Guice.createInjector(new MyBatisXmlModule());
	}

	/** use this! */
	public static Injector get() {
		return injector;
	}

}
