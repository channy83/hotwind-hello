package jhyun.mybatis_with_guice.tests.test_cases;

import static org.junit.Assert.assertNotNull;
import jhyun.mybatis_with_guice.config.AppConfig;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

public class AppConfigTestCase {

	@Test
	public void testLoading() {
		PropertiesConfiguration cfg = AppConfig.load();
		assertNotNull(cfg.getString("environment", "test"));
	}

}
