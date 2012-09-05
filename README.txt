# 가이드
	- https://docs.google.com/document/d/1JkuWN1FGLV_BuA6AvFODLuuRhT6smh8Mhh9eHF0yilE/edit#
	
	
# 적용 내용들

	* Guice
		- http://code.google.com/p/google-guice/ 
		- http://en.wikipedia.org/wiki/Google_Guice
		- http://code.google.com/p/google-guice/wiki/GettingStarted
	
	* Commons-Configuration
		- http://commons.apache.org/configuration/
		
	* SLF4J + Log4j
		- http://www.slf4j.org/
		- http://logging.apache.org/log4j/1.2/index.html
	
	* Guice + MyBatis + Transactional
		- http://www.mybatis.org/core/
		- http://code.google.com/p/mybatis/wiki/Guice
		- http://code.google.com/p/google-guice/wiki/Transactions
	
	* Jersey JAX-RS, JSON
		- http://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services
		- http://jersey.java.net/
		- http://jersey.java.net/nonav/documentation/latest/json.html
	
	* Jersey Requests, Sessions
		- https://cwiki.apache.org/WINK/jax-rs-context-information.html
		- http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e516
	
	* Testing RESTful API
		- http://jersey.java.net/nonav/documentation/latest/user-guide.html#d4e609
		- http://junit.sourceforge.net/
		- http://junit.sourceforge.net/doc/cookbook/cookbook.htm
		


	
	
# 이클립스 프로젝트 세팅 및 실행 방법

	1. 이클립스 가능한 최신(Juno등)와 JDK 6+.
		- http://www.eclipse.org/
		
	2. 이클립스에서 Maven 2+ 연동 가능할것. (m2eclipse이나 Eclipse Juno이상에서 Maven Integration이 필요.)
		- http://www.eclipse.org/m2e/
		
	3. 이클립스 "import project / existing maven project"으로 가져오기.
		- http://stackoverflow.com/a/7711398
	
	4. Maven에서 "jetty:run" goal 실행하여 내장 웹서버 기동.
		- http://www.egovframe.org/wiki/doku.php?id=egovframework:dev:tst:%EC%9D%B4%ED%81%B4%EB%A6%BD%EC%8A%A4%EC%97%90%EC%84%9C_maven_%EC%8B%A4%ED%96%89%ED%95%98%EA%B8%B0
		- http://docs.codehaus.org/display/JETTY/Maven+Jetty+Plugin
	
	5. /mybatis-with-guice/src/test/java/jhyun/mybatis_with_guice/tests/AllTests.java 테스트 모음 실행하여 단위테스트 수행.
		- http://www.vogella.com/articles/JUnit/article.html#juniteclipse_eclipse
	
	
# WAS 배치용 WAR 패키징
	> mvn war:war

	
	
# Maven 비지원 라이브러리 지원
	- Oracle JDBC 드라이버와 같은 경우에 jar을 직접 개발자 피씨의 Local Repository에 직접 "mvn install"후 의존하도록.



	
###EOF

