package jhyun.mybatis_with_guice.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@Path("/hello")
@RequestScoped
public class HelloController {

	private Logger logger = LoggerFactory.getLogger(HelloController.class);

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
		final String s = String.format("this=%s, sqlSession=%s", this,
				ObjectUtils.toString(sqlSession));
		logger.debug(s);
		return s;
	}

}
