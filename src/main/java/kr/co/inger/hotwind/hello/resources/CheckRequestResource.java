package kr.co.inger.hotwind.hello.resources;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import kr.co.inger.hotwind.jaxrs.JaxRsResource;
import kr.co.inger.hotwind.request_check.CheckRequest;
import kr.co.inger.hotwind.request_check.RequestProvider;
import kr.co.inger.hotwind.request_check.backend.RequestCheckKvStore;
import kr.co.inger.hotwind.request_check.session.AccessTokenedSession;

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
public class CheckRequestResource implements RequestProvider {

	private Logger logger = LoggerFactory.getLogger(CheckRequestResource.class);

	@Inject
	private RequestCheckKvStore requestCheckKvStore;

	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login() {
		final String k = UUID.randomUUID().toString();
		requestCheckKvStore.create(k);
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

	@Context
	private HttpServletRequest request;

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return request;
	}

	@CheckRequest(targetField = "accessToken")
	@GET
	@Path("/shouldLoggedInWithPathParam/{accessToken}")
	@Produces(MediaType.TEXT_PLAIN)
	public String shouldLoggedInWithPathParam(
			@PathParam("accessToken") String accessToken) {
		return "OK!! (path-param)";
	}

	@CheckRequest(httpHeaderName = "x-hotwind-accesstoken")
	@GET
	@Path("/shouldLoggedInWithHttpHeader")
	@Produces(MediaType.TEXT_PLAIN)
	public String shouldLoggedInWithHttpHeader() {
		return "OK!! (http-header)";
	}

	@CheckRequest(targetField = "accessToken")
	@GET
	@Path("/shouldLoggedInWithQueryParam")
	@Produces(MediaType.TEXT_PLAIN)
	public String shouldLoggedInWithQueryParam() {
		return "OK!! (query-param)";
	}
	
	@CheckRequest(targetField = "accessToken")
	@GET
	@Path("/foo")
	@Produces(MediaType.TEXT_PLAIN) 
	public String foo(@Context AccessTokenedSession v) {
		return "foobar!";
	}
}
