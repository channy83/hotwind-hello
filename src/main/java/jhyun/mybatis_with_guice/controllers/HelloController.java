package jhyun.mybatis_with_guice.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.google.inject.Inject;

@Path("/hello")
public class HelloController {

	@GET
	@Path("/greet")
	@Produces("text/plain")
	public String getClichedMessage() {
		return "Hello World";
	}

	@Inject
	private SqlSession sqlSession;

	@GET
	@Path("/sqlsession")
	@Produces("text/plain")
	public String showSqlSession() {
		return ObjectUtils.toString(sqlSession);
	}

	// TODO: plus

}
