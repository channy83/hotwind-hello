package jhyun.mybatis_with_guice.tests.test_cases;

import static org.junit.Assert.*;
import jhyun.mybatis_with_guice.injections.Guicer;
import jhyun.mybatis_with_guice.sqlmaps.HelloMapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MybatisWithGuiceTestCase {

	@Test
	public void testInjectedSqlSession() {
		assertNotNull(Guicer.get().getInstance(SqlSession.class));
	}

	public void testSqlMapper() {
		HelloMapper helloMapper = Guicer.get().getInstance(HelloMapper.class);
		assertNotNull(helloMapper);
		//
		assertEquals(3, helloMapper.plus(1, 2));
	}

}
