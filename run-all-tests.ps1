# FS-2604: Automated Full-Stack Test Suite (Frontend + 3 Backend Microservices)

$baseDir = $PSScriptRoot

if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    if (Test-Path "C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10") {
        $env:JAVA_HOME = "C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10"
        $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
    }
}

$mvnPath = "mvn"
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    $fallbackMvn = "C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd"
    if (Test-Path $fallbackMvn) {
        $mvnPath = $fallbackMvn
    }
}

Write-Host "===================================================================" -ForegroundColor Cyan
Write-Host "  FS-2604: Running Full System Health & Test Suite" -ForegroundColor Cyan
Write-Host "===================================================================" -ForegroundColor Cyan

# 1. Flutter Analyze & Tests
Write-Host "`n[1/4] Running Flutter Analysis & Widget/Unit Tests..." -ForegroundColor Magenta
flutter analyze
if ($LASTEXITCODE -ne 0) {
    Write-Error "Flutter analysis failed."
    exit 1
}
flutter test
if ($LASTEXITCODE -ne 0) {
    Write-Error "Flutter test suite failed."
    exit 1
}

# 2. Ganesh Backend Tests
Write-Host "`n[2/4] Testing Ganesh Backend Service..." -ForegroundColor Cyan
& $mvnPath -f "$baseDir\backend\fs2604-backend\pom.xml" test
if ($LASTEXITCODE -ne 0) {
    Write-Error "Ganesh backend tests failed."
    exit 1
}

# 3. Raja Trigger Tests
Write-Host "`n[3/4] Testing Raja Trigger Engine Service..." -ForegroundColor Yellow
& $mvnPath -f "$baseDir\backend\fs2604-raja-trigger\pom.xml" test
if ($LASTEXITCODE -ne 0) {
    Write-Error "Raja trigger tests failed."
    exit 1
}

# 4. Anosh Payout Tests
Write-Host "`n[4/4] Testing Anosh Payout & Audit Service..." -ForegroundColor Green
& $mvnPath -f "$baseDir\backend\fs2604-anosh-payout\pom.xml" test
if ($LASTEXITCODE -ne 0) {
    Write-Error "Anosh payout tests failed."
    exit 1
}

Write-Host "`n===================================================================" -ForegroundColor Green
Write-Host "  SUCCESS: All tests passed across Frontend and all 3 Backends!" -ForegroundColor Green
Write-Host "===================================================================" -ForegroundColor Green
