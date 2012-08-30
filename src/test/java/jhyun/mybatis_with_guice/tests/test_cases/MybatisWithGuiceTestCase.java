package jhyun.mybatis_with_guice.tests.test_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import jhyun.mybatis_with_guice.injections.Guicer;
import jhyun.mybatis_with_guice.sqlmaps.HelloMapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.google.inject.Injector;

public class MybatisWithGuiceTestCase {

	private Injector getInjector() {
		return Guicer.get();
	}

	@Test
	public void testInjectedSqlSession() {
		assertNotNull(getInjector().getInstance(SqlSession.class));
	}

	@Test
	public void testSqlMapper() {
		HelloMapper helloMapper = getInjector().getInstance(HelloMapper.class);
		assertNotNull(helloMapper);
		//
		assertEquals(3, helloMapper.plus(1, 2));
	}

}
