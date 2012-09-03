package jhyun.mybatis_with_guice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import jhyun.mybatis_with_guice.controllers.hello.plus.PlusParams;
import jhyun.mybatis_with_guice.controllers.hello.plus.PlusResult;
import jhyun.mybatis_with_guice.sqlmaps.HelloMapper;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
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
	
	/* SECTION: Sessions, Connections ********************************************* */

	@Context
	private HttpServletRequest request;

	@GET
	@Path("/httpSession")
	@Produces(MediaType.TEXT_PLAIN)
	public String showHttpSession() {
		return ObjectUtils.toString(request.getSession());
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

	/* SECTION: PLUSes ********************************************* */

	@GET
	@Path("/plusWithQueryParams")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> plusWithQueryParams(
			@QueryParam("a") final int a, @QueryParam("b") final int b) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.putAll(new ImmutableMap.Builder<String, Object>()
				.put("a", Integer.valueOf(a)).put("b", Integer.valueOf(b))
				.put("result", Integer.valueOf(a + b)).build());
		return m;
	}

	@POST
	@Path("/plusWithJson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlusResult plusWithJson(PlusParams params) {
		return new PlusResult(params, params.getA() + params.getB());
	}

	@GET
	@Path("/examplePlusParams")
	@Produces(MediaType.APPLICATION_JSON)
	public PlusParams examplePlusParams() {
		return new PlusParams(3, 4);
	}
	
	@POST
	@Path("/plusWithXml")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public PlusResult plusWithXml(PlusParams params) {
		return new PlusResult(params, params.getA() + params.getB());
	}
	

	
	
	/* SECTION: Transactions ********************************************* */

	@Inject
	private HelloMapper hello;

	@GET
	@Path("/transaction/init")
	@Produces(MediaType.TEXT_PLAIN)
	public String initTestingTable() {
		hello.dropTestingTable();
		hello.createTestingTable(true);
		return "Ok?";
	}

	@GET
	@Path("/transaction/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List listAllTestingTable() {
		hello.createTestingTable(true);
		return hello.listAllTestingTable();
	}

	@GET
	@Path("/transaction/add/{n}")
	@Produces(MediaType.TEXT_PLAIN)
	@Transactional
	public String insertIntoTestingTable(@PathParam("n") final String n) {
		hello.createTestingTable(true);
		hello.insertIntoTestingTable(n);
		return "Inserted.";
	}

	@GET
	@Path("/transaction/addThreeCleanly")
	@Produces(MediaType.TEXT_PLAIN)
	@Transactional
	public String addThreeCleanly() throws Exception {
		hello.createTestingTable(true);
		hello.insertIntoTestingTable("1");
		hello.insertIntoTestingTable("2");
		hello.insertIntoTestingTable("3");
		//
		throw new Exception("JUST ABORTED FOR TESTING-PURPOSES.");
	}

	@GET
	@Path("/transaction/addThreeDirty")
	@Produces(MediaType.TEXT_PLAIN)
	// NOTE: without-@Transactional
	public String addThreeDirty() throws Exception {
		hello.createTestingTable(true);
		hello.insertIntoTestingTable("1");
		hello.insertIntoTestingTable("2");
		hello.insertIntoTestingTable("3");
		//
		throw new Exception("JUST ABORTED FOR TESTING-PURPOSES.");
	}

}
