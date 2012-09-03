package jhyun.mybatis_with_guice.tests.helpers;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.ObjectUtils;

import com.google.common.base.Objects;

public class SimpleTransactionHelper {

	public List listAll() {
		return JerseyTestingClient.webResource()
				.path("/hello/transaction/list")
				.accept(MediaType.APPLICATION_JSON).get(List.class);
	}

	public void resetTable() {
		JerseyTestingClient.webResource().path("/hello/transaction/init")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
	}

	public void addSome(final String s) {
		JerseyTestingClient.webResource().path("/hello/transaction/add")
				.path(s).accept(MediaType.TEXT_PLAIN).get(String.class);
	}

	public boolean containsIdIn(final List l, final String s) {
		for (Object o : l) {
			Map m = (Map) o;
			if (Objects.equal(ObjectUtils.toString(m.get("ID")), s))
				return true;
		}
		return false;
	}
}
