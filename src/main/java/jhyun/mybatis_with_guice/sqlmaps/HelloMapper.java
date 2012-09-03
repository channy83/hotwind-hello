package jhyun.mybatis_with_guice.sqlmaps;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Simplest Example MyBatis Mapper.
 * 
 * @author jhyun
 * @since 2012-08-30
 */
public interface HelloMapper {

	/** 1+1 */
	int onePlusOne();

	/** a+b */
	int plus(@Param("a") final int a, @Param("b") final int b);

	/**
	 * 테스트 테이블 (FOO) 생성.
	 * 
	 * @param ifNotExists
	 *            true이면 존재하지 않을때만 생성하도록. (테이블 존재하도록 확증용).
	 */
	void createTestingTable(@Param("ifNotExists") final boolean ifNotExists);

	/** 테스트 테이블 제거. (FOO) */
	void dropTestingTable();

	/** 테스트 테이블(FOO)의 전체 목록. [{ID=..}, ...] */
	List<Map> listAllTestingTable();

	/** 테스트 테이블에 정수 하나를 넣기. (FOO) */
	void insertIntoTestingTable(@Param("n") final String n);

	/** 테스트 테이블의 모든 행들을 삭제. (FOO) */
	void deleteAllTestingTable();

}
