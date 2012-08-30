package jhyun.mybatis_with_guice;

import jhyun.mybatis_with_guice.injections.Guicer;
import jhyun.mybatis_with_guice.sqlmaps.HelloMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.google.inject.Inject;

public class App implements Runnable {
	public static void main(String[] args) {
		Guicer.get().getInstance(App.class).run();
	}

	private static Log log = LogFactory.getLog(App.class);

	@Inject
	private SqlSession sqlSession;

	@Inject
	private HelloMapper helloMapper;

	@Override
	public void run() {
		log.info(sqlSession);
		log.info(helloMapper);
		log.info(String.format("1 + 1 = %s", helloMapper.onePlusOne()));
	}
}
