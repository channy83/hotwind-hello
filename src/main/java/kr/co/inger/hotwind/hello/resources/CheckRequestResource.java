package kr.co.inger.hotwind.hello.resources;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import kr.co.inger.hotwind.jaxrs.JaxRsResource;
import kr.co.inger.hotwind.request_check.backend.RequestCheckKvStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

/**
 * 테스트용 "요청 인증 테스트" 리소스.
 * 
 * @author jhyun
 * @since 2013-Aug-08-Thu
 * 
 */
@JaxRsResource
@Path("/check-request-hello")
@RequestScoped
public class CheckRequestResource {

	private Logger logger = LoggerFactory.getLogger(CheckRequestResource.class);

	@Inject
	private RequestCheckKvStore requestCheckKvStore;

	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login() {
		final String k = UUID.randomUUID().toString();
		requestCheckKvStore.store(k, "true");
		return k;
	}

	@GET
	@Path("/logout/{accessToken}")
	@Produces(MediaType.TEXT_PLAIN)
	public String logout(@PathParam("accessToken") String accessToken) {
		if (requestCheckKvStore.forget(accessToken)) {
			return "OK";
		} else {
			return "FAIL";
		}
	}

	@GET
	@Path("/isLoggedIn/{accessToken}")
	@Produces(MediaType.TEXT_PLAIN)
	public String isLoggedIn(@PathParam("accessToken") String accessToken) {
		if (requestCheckKvStore.isExists(accessToken)) {
			return "LOGGED-IN";
		} else {
			return "NOT-LOGGED-IN";
		}
	}

	// TODO: should-logged-in

}
