package jhyun.mybatis_with_guice.sqlmaps;

import org.apache.ibatis.annotations.Param;

/**
 * Simplest Example MyBatis Mapper.
 * 
 * @author jhyun
 * @since 2012-08-30
 */
public interface HelloMapper {

	int onePlusOne();

	int plus(@Param("a") int a, @Param("b") int b);

}
