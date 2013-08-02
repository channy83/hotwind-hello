package kr.co.inger.hotwind.hello.tests.test_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;

import kr.co.inger.hotwind.hello.resources.hello.plus.PlusParams;
import kr.co.inger.hotwind.hello.resources.hello.plus.PlusResult;
import kr.co.inger.hotwind.hello.tests.helpers.JerseyTestingClient;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jersey Client을 활용한 덧셈 테스트.
 * 
 * @author jhyun
 * @since 2012-09-03
 * 
 */
public class PlusWithJsonJerseyClientTestCase {

	private static Logger logger = LoggerFactory
			.getLogger(PlusWithJsonJerseyClientTestCase.class);

	@Test
	public void testPlusWithJson() {
		PlusParams params = new PlusParams(3, 4);
		PlusResult result = JerseyTestingClient.webResource()
				.path("/hello/plusWithJson").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(PlusResult.class, params);
		logger.debug(String.format("%s -> %s", params, result));
		assertNotNull(result);
		assertEquals(result.getParams(), params);
		assertEquals(result.getResult(), 7);
	}

}
