@echo off

ECHO.
ECHO NOTE THIS BUILD REQUIRES JAVA 1.7 TO WORK CORRECTLY.  JAVA 1.8 WILL NOT WORK.
ECHO THIS BUILD IS ALSO DEPENDANT ON HAVE A SPLUNK INSTANCE INSTALLED LOCALLY
ECHO _____________________________________________________________________________
ECHO.

call mvn clean install

rmdir /S /Q "D:\JavaTools\Tomcat 7.0\webapps\telemetry"
copy /Y D:\Work\TeamArrow\TeamArrowWeb\target\telemetry-0.2-SNAPSHOT.war "D:\JavaTools\Tomcat 7.0\webapps\telemetry.war"