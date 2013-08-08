package kr.co.inger.hotwind.hello.resources;

import javax.ws.rs.Path;

import kr.co.inger.hotwind.jaxrs.JaxRsResource;

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


}
