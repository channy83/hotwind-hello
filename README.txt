# 적용 내용들

	* Guice + MyBatis + Commons-Configuration
	
	* SLF4J + Log4j
	
	* Jersey JAX-RS + JSON + Guice + MyBatis + Transactional
	
	* Jersey Requests, Sessions
	
	* Testing RESTful API


	
	
# 이클립스 프로젝트 세팅 및 실행 방법

	1. 이클립스 가능한 최신(Juno등)와 JDK 6+.
		- http://www.eclipse.org/
		
	2. 이클립스에서 Maven 2+ 연동 가능할것. (m2eclipse이나 Eclipse Juno이상에서 Maven Integration이 필요.)
		- http://www.eclipse.org/m2e/
		
	3. 이클립스 "import project / existing maven project"으로 가져오기.
		- 
	
	4. Maven에서 jetty:run 타겟 실행하여 내장 웹서버 기동.
	
	5. /mybatis-with-guice/src/test/java/jhyun/mybatis_with_guice/tests/AllTests.java 테스트 모음 실행하여 단위테스트 수행.
	
	
	
###EOF

