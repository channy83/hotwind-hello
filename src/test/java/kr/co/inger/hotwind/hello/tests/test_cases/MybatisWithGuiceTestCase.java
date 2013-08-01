package kr.co.inger.hotwind.hello.tests.test_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import kr.co.inger.hotwind.guice.Guicer;
import kr.co.inger.hotwind.hello.sqlmaps.HelloMapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.google.inject.Injector;

/**
 * mybatis + guice 연동이 잘 되는지?
 * 
 * @author jhyun
 * @since 2012-09-03
 * 
 */
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
