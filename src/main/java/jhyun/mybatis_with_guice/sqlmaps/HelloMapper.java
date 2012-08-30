package jhyun.mybatis_with_guice.sqlmaps;

import org.apache.ibatis.annotations.Param;

public interface HelloMapper {

	int onePlusOne();

	int plus(@Param("a") int a, @Param("b") int b);

}
