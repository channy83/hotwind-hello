package jhyun.mybatis_with_guice.tests.test_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import jhyun.mybatis_with_guice.injections.Guicer;
import jhyun.mybatis_with_guice.tests.helpers.SimpleTransactionHelper;

import org.junit.Test;

import com.google.inject.Inject;

public class SimpleTransactionClientTestCase {

	public SimpleTransactionClientTestCase() {
		super();
		Guicer.get().injectMembers(this);
	}

	@Inject
	private SimpleTransactionHelper sth;

	@Test
	public void testSimpleAddAndList() {
		//
		sth.resetTable();
		//
		List l = sth.listAll();
		assertEquals(0, l.size());
		assertFalse(sth.containsIdIn(l, "1"));
		assertFalse(sth.containsIdIn(l, "2"));
		assertFalse(sth.containsIdIn(l, "3"));
		assertFalse(sth.containsIdIn(l, "4"));
		// adds
		sth.addSome("1");
		sth.addSome("2");
		sth.addSome("3");
		//
		List l2 = sth.listAll();
		assertEquals(3, l2.size());
		assertTrue(sth.containsIdIn(l2, "1"));
		assertTrue(sth.containsIdIn(l2, "2"));
		assertTrue(sth.containsIdIn(l2, "3"));
		assertFalse(sth.containsIdIn(l2, "4"));
	}

}
