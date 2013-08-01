package kr.co.inger.hotwind.hello.tests.test_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.ws.rs.core.MediaType;

import kr.co.inger.hotwind.guice.Guicer;
import kr.co.inger.hotwind.hello.tests.helpers.JerseyTestingClient;
import kr.co.inger.hotwind.hello.tests.helpers.SimpleTransactionHelper;

import org.junit.Test;

import com.google.inject.Inject;

/**
 * 병합 트랜잭션 처리가 잘되는지 안되는지에 대한 테스트.
 * 
 * @author jhyun
 * @since 2012-09-03
 */
public class DirtyAndCleanTransactionClientTestCase {

	public DirtyAndCleanTransactionClientTestCase() {
		super();
		Guicer.get().injectMembers(this);
	}

	@Inject
	private SimpleTransactionHelper sth;

	/** 트랜잭션 처리 제대로 안되는 예시 */
	@Test
	public void testSomeDirtyTransactions() {
		sth.resetTable();
		try {
			JerseyTestingClient.webResource()
					.path("/hello/transaction/addThreeDirty")
					.accept(MediaType.TEXT_PLAIN).get(String.class);
			fail("FAIL!!! API SHOULD THROW SOME EXCEPTION!!!");
		} catch (Exception exc) {
			List l = sth.listAll();
			// NOTE: 트랜잭션 취소 처리가 안되고, 잔여 3개가 남아있어야함.
			assertEquals(3, l.size());
		}
	}

	/** 정상적인 트랜잭션 처리 예시 */
	@Test
	public void testSomeCleanTransactions() {
		sth.resetTable();
		try {
			JerseyTestingClient.webResource()
					.path("/hello/transaction/addThreeCleanly")
					.accept(MediaType.TEXT_PLAIN).get(String.class);
			fail("FAIL!!! API SHOULD THROW SOME EXCEPTION!!!");
		} catch (Exception exc) {
			List l = sth.listAll();
			// NOTE: 예외가 발생했고, @Transactional이 정상적으로 적용되었다면, 테이블에는 아무것도 없어야함.
			assertEquals(0, l.size());
		}
	}

}
