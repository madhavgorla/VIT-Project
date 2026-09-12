@echo off
echo ===================================================================
echo   FS-2604: Starting Full Team Code Rabbits Microservice Mesh
echo   1. Ganesh: Backend and API Hub (Port 8080)
echo   2. Raja: Rainfall + Trigger Engine (Port 8081)
echo   3. Anosh: Database + Payout + Audit (Port 8082)
echo ===================================================================

set JAVA_HOME=C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10
set PATH=%JAVA_HOME%\bin;%PATH%
set MVN_BIN="C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd"

echo Starting Ganesh's Service (Port 8080)...
start "1. GANESH - Backend and APIs (8080)" cmd /k "cd /d "%~dp0fs2604-backend" && %MVN_BIN% spring-boot:run"

ping -n 4 127.0.0.1 > nul

echo Starting Anosh's Service (Port 8082)...
start "3. ANOSH - Database and Payout (8082)" cmd /k "cd /d "%~dp0fs2604-anosh-payout" && %MVN_BIN% spring-boot:run"

ping -n 3 127.0.0.1 > nul

echo Starting Raja's Service (Port 8081)...
start "2. RAJA - Rainfall and Trigger (8081)" cmd /k "cd /d "%~dp0fs2604-raja-trigger" && %MVN_BIN% spring-boot:run"

echo.
echo ===================================================================
echo   All 3 services are launching in separate windows!
echo   - Ganesh API: http://localhost:8080/swagger-ui.html
echo   - Raja Engine: http://localhost:8081/swagger-ui.html
echo   - Anosh Payout: http://localhost:8082/swagger-ui.html
echo ===================================================================
