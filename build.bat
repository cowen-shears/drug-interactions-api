@echo off
setlocal enabledelayedexpansion

rem Set Java Home with proper quoting
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
echo Using JAVA_HOME: %JAVA_HOME%

rem Add Java to PATH
set "PATH=%JAVA_HOME%\bin;%PATH%"

rem Verify Java is available
java -version

rem Run Maven build
call mvnw.cmd clean install

rem Preserve exit code
set BUILD_STATUS=%ERRORLEVEL%
echo Build finished with status: %BUILD_STATUS%
exit /b %BUILD_STATUS%
