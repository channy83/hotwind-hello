package jhyun.mybatis_with_guice.tests.helpers;

import jhyun.mybatis_with_guice.config.AppConfig;

import org.apache.commons.configuration.Configuration;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class JerseyTestingClient {

	public static WebResource webResource() {
		//
		final Configuration config = AppConfig.load();
		final String apiUrlPrefix = config.getString("test.api-url-prefix");
		//
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(apiUrlPrefix);
		return webResource;
	}
}
