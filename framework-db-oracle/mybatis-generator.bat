set M2_HOME=E:\DevTools\apache-maven-3.2.1
set PATH=%M2_HOME%\bin;%PATH%

mvn mybatis-generator:generate -DgeneratorConfig.type=%1