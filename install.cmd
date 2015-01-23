call mvn install

rmdir /S /Q "D:\JavaTools\Tomcat 7.0\webapps\telemetry"
copy /Y D:\Work\TeamArrow\Software\TeamArrowWeb\target\telemetry-0.2-SNAPSHOT.war "D:\JavaTools\Tomcat 7.0\webapps\telemetry.war"