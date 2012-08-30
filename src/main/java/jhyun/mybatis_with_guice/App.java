package jhyun.mybatis_with_guice;

import jhyun.mybatis_with_guice.injections.Guicer;
import jhyun.mybatis_with_guice.sqlmaps.HelloMapper;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class App implements Runnable {
	public static void main(String[] args) {
		Guicer.get().getInstance(App.class).run();
	}

	private static Logger logger = LoggerFactory.getLogger(App.class);

	@Inject
	private SqlSession sqlSession;

	@Inject
	private HelloMapper helloMapper;

	@Override
	public void run() {
		logger.info(ObjectUtils.toString(sqlSession));
		logger.info(ObjectUtils.toString(helloMapper));
		logger.info(String.format("1 + 1 = %s", helloMapper.onePlusOne()));
	}
}
