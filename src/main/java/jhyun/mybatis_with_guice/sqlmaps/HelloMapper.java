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

	int onePlusOne();

	int plus(@Param("a") final int a, @Param("b") final int b);

	void createTestingTable(@Param("ifNotExists") final boolean ifNotExists);

	void dropTestingTable();

	List<Map> listAllTestingTable();

	void insertIntoTestingTable(@Param("n") final String n);

}
