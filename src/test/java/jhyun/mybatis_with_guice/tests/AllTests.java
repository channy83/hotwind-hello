package jhyun.mybatis_with_guice.tests;

import jhyun.mybatis_with_guice.tests.test_cases.AppConfigTestCase;
import jhyun.mybatis_with_guice.tests.test_cases.DirtyAndCleanTransactionClientTestCase;
import jhyun.mybatis_with_guice.tests.test_cases.MybatisWithGuiceTestCase;
import jhyun.mybatis_with_guice.tests.test_cases.PlusWithJsonJerseyClientTestCase;
import jhyun.mybatis_with_guice.tests.test_cases.SimpleTransactionClientTestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 전체 테스트 묶음. 단위 테스트를 모두 수행하려면, 이 클래스를 Test Runner에서 실행하세요.
 * 
 * @author jhyun
 * @since 2012-09-03
 */
@RunWith(Suite.class)
@SuiteClasses({ AppConfigTestCase.class, MybatisWithGuiceTestCase.class,
		PlusWithJsonJerseyClientTestCase.class,
		SimpleTransactionClientTestCase.class,
		DirtyAndCleanTransactionClientTestCase.class })
public class AllTests {

}
