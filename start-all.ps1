# FS-2604: Full Team Code Rabbits Microservice Mesh + Flutter Launcher

$baseDir = $PSScriptRoot

# Configure Java Home if not in path
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    if (Test-Path "C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10") {
        $env:JAVA_HOME = "C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10"
        $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
    }
}

# Locate Maven
$mvnPath = "mvn"
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    $fallbackMvn = "C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd"
    if (Test-Path $fallbackMvn) {
        $mvnPath = $fallbackMvn
    }
}

Write-Host "===================================================================" -ForegroundColor Cyan
Write-Host "  FS-2604: Starting Full Team Code Rabbits Full-Stack Mesh" -ForegroundColor Cyan
Write-Host "===================================================================" -ForegroundColor Cyan

Write-Host "Starting [1/4] Ganesh Backend & APIs (Port 8080)..." -ForegroundColor Cyan
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$baseDir\backend\fs2604-backend'; & '$mvnPath' spring-boot:run"

Start-Sleep -Seconds 4

Write-Host "Starting [2/4] Raja Rainfall & Trigger Engine (Port 8081)..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$baseDir\backend\fs2604-raja-trigger'; & '$mvnPath' spring-boot:run"

Start-Sleep -Seconds 3

Write-Host "Starting [3/4] Anosh Database & Payout Service (Port 8082)..." -ForegroundColor Green
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$baseDir\backend\fs2604-anosh-payout'; & '$mvnPath' spring-boot:run"

Start-Sleep -Seconds 3

Write-Host "Starting [4/4] Madhav Flutter Mobile/Web App (Port 3000)..." -ForegroundColor Magenta
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$baseDir'; flutter run -d chrome --web-port 3000"

Write-Host "`nAll 4 services launched successfully!" -ForegroundColor Green
Write-Host "Flutter App:  http://localhost:3000" -ForegroundColor Magenta
Write-Host "Ganesh API:   http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan
Write-Host "Raja Trigger: http://localhost:8081/swagger-ui.html" -ForegroundColor Yellow
Write-Host "Anosh Payout: http://localhost:8082/swagger-ui.html" -ForegroundColor Green
