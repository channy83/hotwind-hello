package jhyun.mybatis_with_guice.tests;

import jhyun.mybatis_with_guice.tests.test_cases.AppConfigTestCase;
import jhyun.mybatis_with_guice.tests.test_cases.MybatisWithGuiceTestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AppConfigTestCase.class, MybatisWithGuiceTestCase.class })
public class AllTests {

}
