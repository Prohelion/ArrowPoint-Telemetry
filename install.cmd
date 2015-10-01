@echo off

ECHO.
ECHO NOTE THIS BUILD REQUIRES JAVA 1.7 TO WORK CORRECTLY.  JAVA 1.8 WILL NOT WORK.
ECHO THIS BUILD IS ALSO DEPENDANT ON HAVE A SPLUNK INSTANCE INSTALLED LOCALLY
ECHO _____________________________________________________________________________
ECHO If you start seeing cannot connect errors and you have a SPLUNK instance
ECHO installed locally check your JDK has not disabled SSLv3... Details are here
ECHO http://answers.splunk.com/answers/209379/no-appropriate-protocol-protocol-is-disabled-or-ci.html
ECHO.

call mvn clean install

rmdir /S /Q "C:\Program Files\Apache Software Foundation\Tomcat 7.0\webapps\telemetry"
copy /Y C:\Work\TeamArrow\TeamArrowWeb\target\telemetry-0.2-SNAPSHOT.war "C:\Program Files\Apache Software Foundation\Tomcat 7.0\webapps\telemetry.war"
