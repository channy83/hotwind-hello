package kr.co.inger.hotwind.hello.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import kr.co.inger.hotwind.jaxrs.JaxRsResource;
import kr.co.inger.hotwind.jcs.InjectJcs;

import org.apache.commons.lang.ObjectUtils;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.servlet.RequestScoped;

/**
 * 테스트용 "JCS 캐쉬 테스트" 리소스.
 * 
 * @author jhyun
 * @since 2013-Aug-22-Thu
 * 
 */
@JaxRsResource
@Path("/jcs-hello")
@RequestScoped
public class JcsHelloResource {

	private Logger logger = LoggerFactory.getLogger(JcsHelloResource.class);

	@InjectJcs(cacheName = "hello")
	private JCS helloCache;

	@GET
	@Path("/put/{k}/{v}")
	@Produces(MediaType.TEXT_PLAIN)
	public String put(@PathParam("k") String k, @PathParam("v") String v)
			throws CacheException {
		helloCache.put(k, v);
		return "OK";
	}

	@GET
	@Path("/clear")
	@Produces(MediaType.TEXT_PLAIN)
	public String clear() throws CacheException {
		helloCache.clear();
		return "CLEARED.";
	}

	@GET
	@Path("/get/{k}")
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@PathParam("k") String k) {
		return ObjectUtils.toString(helloCache.get(k));
	}

}
