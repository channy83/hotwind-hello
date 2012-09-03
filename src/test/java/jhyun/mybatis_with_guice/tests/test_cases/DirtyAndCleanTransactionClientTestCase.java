package jhyun.mybatis_with_guice.tests.test_cases;

import jhyun.mybatis_with_guice.injections.Guicer;
import jhyun.mybatis_with_guice.tests.helpers.SimpleTransactionHelper;

import org.junit.Test;

import com.google.inject.Inject;

public class DirtyAndCleanTransactionClientTestCase {

	public DirtyAndCleanTransactionClientTestCase() {
		super();
		Guicer.get().injectMembers(this);
	}

	@Inject
	private SimpleTransactionHelper sth;

	@Test
	public void testSomeDirtyTransactions() {
		// TODO:
	}

	@Test
	public void testSomeCleanTransactions() {
		// TODO:
	}

}
