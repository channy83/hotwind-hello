package jhyun.mybatis_with_guice.tests.test_cases;

import javax.ws.rs.core.MediaType;
import static org.junit.Assert.*;

import jhyun.mybatis_with_guice.controllers.hello.plus.PlusParams;
import jhyun.mybatis_with_guice.controllers.hello.plus.PlusResult;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class PlusWithJsonJerseyClientTestCase {

	private static Logger logger = LoggerFactory
			.getLogger(PlusWithJsonJerseyClientTestCase.class);

	private WebResource prepareWebResource() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource("http://localhost:8080");
		return webResource;
	}

	@Test
	public void testPlusWithJson() {
		PlusParams params = new PlusParams(3, 4);
		PlusResult result = prepareWebResource().path("hello")
				.path("plusWithJson").accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(PlusResult.class, params);
		logger.debug(String.format("%s -> %s", params, result));
		assertNotNull(result);
		assertEquals(result.getParams(), params);
		assertEquals(result.getResult(), 7);
	}

}
