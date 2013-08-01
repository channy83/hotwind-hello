package jhyun.mybatis_with_guice.tests.helpers;

import jhyun.hotwind.config.AppConfig;

import org.apache.commons.configuration.Configuration;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Jersey Client을 활용하는 단위 테스트 도우미.
 * 
 * app-config.xml등의 설정에서 test.api-url-prefix 설정에 영향을 받음.
 * 
 * @author jhyun
 * @since 2012-09-03
 */
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
