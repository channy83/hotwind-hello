package jhyun.mybatis_with_guice.tests.test_cases;

import static org.junit.Assert.assertNotNull;
import jhyun.hotwind.config.AppConfig;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;

/**
 * app-config 단위 테스트. (초간단..)
 * 
 * @author jhyun
 * @since 2012-09-03
 */
public class AppConfigTestCase {

	@Test
	public void testLoading() {
		Configuration cfg = AppConfig.load();
		assertNotNull(cfg.getString("app.environment", "test"));
	}

}
