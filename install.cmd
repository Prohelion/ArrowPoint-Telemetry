@echo off

ECHO.
ECHO NOTE THIS BUILD REQUIRES JAVA 1.8 TO WORK CORRECTLY.  JAVA 1.7 WILL NOT WORK.
ECHO THIS BUILD IS ALSO DEPENDANT ON HAVE A SPLUNK INSTANCE INSTALLED LOCALLY
ECHO IF YOU WANT TO RUN IT AS A LOCAL SERVER
ECHO
ECHO.

call mvn clean install

rmdir /S /Q "D:\JavaTools\apache-tomcat-9.0.0.M21\webapps\telemetry"
copy /Y D:\Work\TeamArrow\TeamArrowWeb\target\telemetry-0.2-SNAPSHOT.war "D:\JavaTools\apache-tomcat-9.0.0.M21\webapps\telemetry.war"
