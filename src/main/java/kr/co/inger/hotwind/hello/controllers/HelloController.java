package kr.co.inger.hotwind.hello.controllers;

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

import kr.co.inger.hotwind.hello.controllers.hello.plus.PlusParams;
import kr.co.inger.hotwind.hello.controllers.hello.plus.PlusResult;
import kr.co.inger.hotwind.hello.sqlmaps.HelloMapper;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.sun.jersey.api.view.Viewable;

/**
 * 테스트용 "Hello" 컨트롤로/리소스.
 * 
 * @author jhyun
 * @since 2012-08-30
 * 
 */
@Path("/hello")
@RequestScoped
public class HelloController {

	private Logger logger = LoggerFactory.getLogger(HelloController.class);

	/** very cliched... */
	@GET
	@Path("/greet")
	@Produces("text/plain")
	public String getClichedMessage() {
		return "Hello World";
	}

	/*
	 * SECTION: Sessions, Connections
	 */

	@Context
	private HttpServletRequest request;

	/** 현재 클라이언트의 HTTP세션 정보를 보여줌. (HTTP세션 연동 예시) */
	@GET
	@Path("/httpSession")
	@Produces(MediaType.TEXT_PLAIN)
	public String showHttpSession() {
		return ObjectUtils.toString(request.getSession());
	}

	@Inject
	private SqlSession sqlSession;

	/** myBatis 세션 객체 얻기 예시. (Guice Injection와 Guice + myBatis의 예시) */
	@GET
	@Path("/sqlsession")
	@Produces("text/plain")
	public String showSqlSession() {
		final String s = String.format("this=%s, sqlSession=%s", this,
				ObjectUtils.toString(sqlSession));
		logger.debug(s);
		return s;
	}

	/*
	 * SECTION: PLUSes
	 */

	/** 덧셈 예시 (Query Parameter을 활용) */
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

	/** JSON으로 인자, 결과 전달하는 덧셈 예시 */
	@POST
	@Path("/plusWithJson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PlusResult plusWithJson(PlusParams params) {
		return new PlusResult(params, params.getA() + params.getB());
	}

	/** POJO을 JSON으로 되돌리는 예시 */
	@GET
	@Path("/examplePlusParams")
	@Produces(MediaType.APPLICATION_JSON)
	public PlusParams examplePlusParams() {
		return new PlusParams(3, 4);
	}

	/** XML으로 POJO 인자와 결과를 주고 받는 덧셈 예시 */
	@POST
	@Path("/plusWithXml")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public PlusResult plusWithXml(PlusParams params) {
		return new PlusResult(params, params.getA() + params.getB());
	}

	/*
	 * SECTION: Transactions
	 */

	@Inject
	private HelloMapper hello;

	/** 트랜잭션 테스트용 테이블 초기화. */
	@GET
	@Path("/transaction/init")
	@Produces(MediaType.TEXT_PLAIN)
	public String initTestingTable() {
		hello.dropTestingTable();
		hello.createTestingTable(true);
		return "Ok?";
	}

	/** 트랜잭션 테스트용 테이블 JSON으로 전체 리스팅. */
	@GET
	@Path("/transaction/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List listAllTestingTable() {
		hello.createTestingTable(true);
		return hello.listAllTestingTable();
	}

	/** 트랜잭션 테이블에 값 하나 넣기. (QueryParam으로 지정한 정수값.) */
	@GET
	@Path("/transaction/add/{n}")
	@Produces(MediaType.TEXT_PLAIN)
	@Transactional
	public String insertIntoTestingTable(@PathParam("n") final String n) {
		hello.createTestingTable(true);
		hello.insertIntoTestingTable(n);
		return "Inserted.";
	}

	/** 올바른 트랜잭션 처리 예시. (3개 넣고, 예외를 발생하여, 하위 트랜잭션 롤백 되도록.) */
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

	/** 틀린 트랜잭션 처리 예시. (3개 넣고, 예외를 발생했지만, @Transactional이 없어서 정상적으로 롤백 되지 않음.) */
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

	/** 테이블의 내용들 전체 지우기. */
	@GET
	@Path("/transaction/deleteAll")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAllTestingTable() {
		hello.createTestingTable(true);
		hello.deleteAllTestingTable();
		return "OK.";
	}

	/** Viewable 활용해서 JSP/MVC 패턴 구현. */
	@GET
	@Path("/viewable/foo")
	@Produces(MediaType.TEXT_HTML)
	public Viewable viewableFoo(@Context HttpServletRequest request) {
		return new Viewable("/foo.jsp",
				new ImmutableMap.Builder<String, Object>().put("value", "42")
						.build());
	}

}
