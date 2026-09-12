@echo off
setlocal enabledelayedexpansion
title Team Code Rabbits - FS-2604 Full Stack Mesh

echo ===================================================================
echo   FS-2604: Offline-First Parametric Micro-Insurance System
echo   Team Code Rabbits - Full Stack Mesh Launcher
echo ===================================================================
echo.
echo Launching:
echo   [1] Ganesh's Backend & APIs          (Port 8080)
echo   [2] Raja's Rainfall & Trigger Engine (Port 8081)
echo   [3] Anosh's Database & Payout        (Port 8082)
echo   [4] Madhav's Flutter Frontend        (Chrome Web - Port 3000)
echo ===================================================================

:: Check Java
where java >nul 2>&1
if %errorlevel% neq 0 (
    if exist "C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10" (
        set "JAVA_HOME=C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10"
        set "PATH=!JAVA_HOME!\bin;!PATH!"
    )
)

:: Locate Maven
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    set MVN_CMD=mvn
) else (
    if exist "C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd" (
        set MVN_CMD="C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd"
    ) else (
        echo [ERROR] Maven not found on PATH.
        pause
        exit /b 1
    )
)

echo.
echo [1/4] Starting Ganesh's Backend Hub (Port 8080)...
start "Ganesh Backend (8080)" cmd /k "cd /d "%~dp0backend\fs2604-backend" && !MVN_CMD! spring-boot:run"

timeout /t 4 /nobreak >nul

echo [2/4] Starting Raja's Trigger Engine (Port 8081)...
start "Raja Trigger Engine (8081)" cmd /k "cd /d "%~dp0backend\fs2604-raja-trigger" && !MVN_CMD! spring-boot:run"

timeout /t 3 /nobreak >nul

echo [3/4] Starting Anosh's Payout & Audit Service (Port 8082)...
start "Anosh Payout Service (8082)" cmd /k "cd /d "%~dp0backend\fs2604-anosh-payout" && !MVN_CMD! spring-boot:run"

timeout /t 3 /nobreak >nul

echo [4/4] Starting Madhav's Flutter Mobile/Web App (Port 3000)...
start "Flutter Web App (3000)" cmd /k "cd /d "%~dp0" && flutter run -d chrome --web-port 3000"

echo.
echo ===================================================================
echo   All 4 services are starting in separate windows!
echo.
echo   - Flutter App UI:   http://localhost:3000
echo   - Ganesh API Hub:   http://localhost:8080/swagger-ui.html
echo   - Raja Engine:      http://localhost:8081/swagger-ui.html
echo   - Anosh Payout:     http://localhost:8082/swagger-ui.html
echo ===================================================================
echo Press any key to exit this launcher window (services keep running)...
pause >nul
