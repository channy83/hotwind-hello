package jhyun.mybatis_with_guice.tests.test_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import jhyun.mybatis_with_guice.tests.helpers.JerseyTestingClient;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;

import com.google.common.base.Objects;

public class SimpleTransactionTestCase {

	private List listAll() {
		return JerseyTestingClient.webResource()
				.path("/hello/transaction/list")
				.accept(MediaType.APPLICATION_JSON).get(List.class);
	}

	private void resetTable() {
		JerseyTestingClient.webResource().path("/hello/transaction/init")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
	}

	private void addSome(final String s) {
		JerseyTestingClient.webResource().path("/hello/transaction/add")
				.path(s).accept(MediaType.TEXT_PLAIN).get(String.class);
	}

	private boolean containsIdIn(final List l, final String s) {
		for (Object o : l) {
			Map m = (Map) o;
			if (Objects.equal(ObjectUtils.toString(m.get("ID")), s))
				return true;
		}
		return false;
	}

	@Test
	public void testSimpleAddAndList() {
		//
		this.resetTable();
		//
		List l = this.listAll();
		assertEquals(0, l.size());
		assertFalse(this.containsIdIn(l, "1"));
		assertFalse(this.containsIdIn(l, "2"));
		assertFalse(this.containsIdIn(l, "3"));
		assertFalse(this.containsIdIn(l, "4"));
		// adds
		this.addSome("1");
		this.addSome("2");
		this.addSome("3");
		//
		List l2 = this.listAll();
		assertEquals(3, l2.size());
		assertTrue(this.containsIdIn(l2, "1"));
		assertTrue(this.containsIdIn(l2, "2"));
		assertTrue(this.containsIdIn(l2, "3"));
		assertFalse(this.containsIdIn(l2, "4"));
	}

}
