@echo off

echo NOTE that this command may throw and error if there are no dangling images (this is expected)
setlocal
:PROMPT
SET /P AREYOUSURE=This will remove all docker images, containers and volumes ARE YOU SURE (Y/[N])?
IF /I "%AREYOUSURE%" NEQ "Y" GOTO END

FOR /f "tokens=*" %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f "tokens=*" %%i IN ('docker images --format "{{.ID}}"') DO docker rmi -f %%i

REM Now attempt to clear any dangling images, this may results in an error if there are none
powershell docker rmi -f $(docker images -q -f dangling=true)

docker system prune --volumes --force

:END
endlocal





