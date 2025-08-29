@echo off
echo 🎯 Lottery Scraper Starting...
echo ========================================

echo 📦 Cleaning and compiling...
call mvnw clean compile

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo 🚀 Running Lottery Scraper...
call mvnw spring-boot:run

pause
