@ECHO off
@REM Script run build uaa image

SET tag=
SET /p tag="Enter tag version or leave it (default 'latest') ..."
IF [%tag%] == [] ( SET tag=latest )

ECHO [36mStep 1: Compile Spring...[0m
CALL mvn clean package
IF NOT EXIST .\target\uaa-*.jar GOTO :COMPILE_ERROR

ECHO [36mStep 2: Build image uaa with tag %tag%...[0m
CALL docker build -t nguyen/uaa:%tag% -f .\Dockerfile
IF %ERRORLEVEL% GTR 1 GOTO :BUILD_ERROR

GOTO :END

:COMPILE_ERROR
ECHO [31mError when compile, please see the log...[0m
exit /b 1

:BUILD_ERROR
ECHO [31mError when build image, please see the log...[0m
exit /b 1
:END
ECHO .
ECHO [42mBuild success ...[0m
ECHO You can try deploy image with Docker using command:
ECHO docker run --name uaa -p 8080:8080 --env-file .env nguyen/uaa
exit /b 0
