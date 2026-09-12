# Launch all 3 microservices concurrently in separate windows

$baseDir = $PSScriptRoot

Write-Host "Starting 1. Ganesh's Backend & APIs (Port 8080)..." -ForegroundColor Cyan
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$baseDir\fs2604-backend'; .\run.ps1"

Start-Sleep -Seconds 4

Write-Host "Starting 2. Anosh's Database & Payout Service (Port 8082)..." -ForegroundColor Green
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$baseDir\fs2604-anosh-payout'; .\run.ps1"

Start-Sleep -Seconds 3

Write-Host "Starting 3. Raja's Rainfall & Trigger Engine (Port 8081)..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$baseDir\fs2604-raja-trigger'; .\run.ps1"

Write-Host "`nAll 3 modules are running!" -ForegroundColor Green
Write-Host "Ganesh API:   http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan
Write-Host "Raja Trigger: http://localhost:8081/swagger-ui.html" -ForegroundColor Yellow
Write-Host "Anosh Payout: http://localhost:8082/swagger-ui.html" -ForegroundColor Green
