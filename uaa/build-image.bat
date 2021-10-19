@ECHO off
@REM Script run build uaa image

ECHO [36mStep 1: Install and compile Spring...[0m
SET cmd=mvn clean install

SET skipTest=
SET /p skipTest="Skip test? [y/n] ..."
IF "%skipTest%"=="" GOTO RUN_COMPILE
IF "%skipTest%"=="n" GOTO RUN_COMPILE
IF "%skipTest%"=="N" GOTO RUN_COMPILE
IF "%skipTest%"=="y" GOTO SKIP_TEST
IF "%skipTest%"=="Y" GOTO SKIP_TEST

:SKIP_TEST
ECHO [36mSkip test cases...[0m
SET cmd=mvn clean install -DskipTests

:RUN_COMPILE
ECHO Run command: [32m%cmd% [0m
%cmd%
IF NOT EXIST .\target\uaa-*.jar GOTO :COMPILE_ERROR

ECHO [36mStep 2: Build image uaa with tag %tag%...[0m
SET tag=
SET /p tag="Enter tag version or leave it (default 'latest') ..."
IF [%tag%] == [] ( SET tag=latest )

@REM CALL docker build -t nguyen/uaa:%tag% .
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
