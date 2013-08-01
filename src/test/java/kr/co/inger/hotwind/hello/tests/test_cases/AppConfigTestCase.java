package kr.co.inger.hotwind.hello.tests.test_cases;

import static org.junit.Assert.assertNotNull;
import kr.co.inger.hotwind.config.AppConfig;

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
