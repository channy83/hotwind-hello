package jhyun.mybatis_with_guice.injections;

import java.util.ArrayList;
import java.util.List;

import jhyun.mybatis_with_guice.config.AppConfig;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Guice Injector.
 * 
 * app.guice-modules으로 지정된 모듈 클래스들을 새로운 인스턴스를 만들어 초기화하고 Guice모듈로 활용한다.
 * 
 * @author jhyun
 * @since 2012-08-30
 * 
 * @see <a
 *      href="http://google-guice.googlecode.com/git/javadoc/com/google/inject/Module.html">Guice
 *      / Module</a>
 */
public class Guicer {
	private static Logger logger = LoggerFactory.getLogger(Guicer.class);

	private static Injector injector;

	static {
		// 초기화!
		Configuration config = AppConfig.load();
		List<Module> moduleObjs = bindModules(config);
		injector = Guice.createInjector(moduleObjs);
	}

	/** app.guice-modules에 리스트로 지정된 전체 클래스 경로를 찾아 인스턴스를 생성하고 모듈로 사용. */
	private static List<Module> bindModules(Configuration config) {
		List<Object> moduleClassFqns = config.getList("app.guice-modules",
				new ArrayList<Object>());
		logger.debug(String.format("guice-module-class-fqns = %s",
				moduleClassFqns));
		//
		List<Module> moduleObjs = new ArrayList<Module>();
		for (Object moduleClassFqn : moduleClassFqns) {
			try {
				Class<? extends Module> moduleClass = (Class<? extends Module>) Class
						.forName((String) moduleClassFqn);
				Module moduleObj = moduleClass.newInstance();
				moduleObjs.add(moduleObj);
				logger.info(String.format("guice-module bound = [%s]",
						moduleClass));
			} catch (Exception e) {
				logger.warn(String
						.format("app.guice-modules specified guice-module-class not found.",
								e));
			}
		}
		return moduleObjs;
	}

	/** use this! */
	public static Injector get() {
		return injector;
	}

}
