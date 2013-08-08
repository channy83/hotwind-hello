package kr.co.inger.hotwind.hello.resources;

import javax.cache.Cache;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import kr.co.inger.hotwind.cache.InjectCache;
import kr.co.inger.hotwind.jaxrs.JaxRsResource;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.servlet.RequestScoped;

/**
 * 테스트용 "캐쉬 테스트" 리소스.
 * 
 * @author jhyun
 * @since 2013-Aug-08-Thu
 * 
 */
@JaxRsResource
@Path("/cache-hello")
@RequestScoped
public class CacheHelloResource {

	private Logger logger = LoggerFactory.getLogger(CacheHelloResource.class);

	@InjectCache(cacheName = "hello")
	private Cache helloCache;

	@GET
	@Path("/put/{k}/{v}")
	@Produces(MediaType.TEXT_PLAIN)
	public String put(@PathParam("k") String k, @PathParam("v") String v) {
		helloCache.put(k, v);
		return "OK";
	}

	@GET
	@Path("/clear")
	@Produces(MediaType.TEXT_PLAIN)
	public String clear() {
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
