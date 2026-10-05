@echo off
title Campus Connect - College Management & Event Platform
echo ==============================================================================
echo   🚀 Starting Campus Connect - College Management & Event Platform
echo ==============================================================================
echo.
echo Checking Java Environment...
java -version
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH! Please install JDK 17+ or 21+.
    pause
    exit /b %errorlevel%
)

echo.
echo Starting Spring Boot Application with Maven Wrapper...
echo.
call mvnw.cmd spring-boot:run

pause
