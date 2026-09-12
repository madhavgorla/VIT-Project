# FS-2604 Live API Demonstration Script
# Tests all inter-module connections

$baseUrl = "http://localhost:8080"

Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "  FS-2604: Offline-First Parametric Micro-Insurance       " -ForegroundColor Yellow
Write-Host "  Backend & APIs Module (Ganesh) - Code Rabbits Team      " -ForegroundColor Green
Write-Host "==========================================================" -ForegroundColor Cyan

# 1. Health & Farmers List
Write-Host "`n[Step 1] Fetching pre-seeded farmers (Ganesh Backend API):" -ForegroundColor Yellow
$farmers = Invoke-RestMethod -Uri "$baseUrl/api/v1/farmers" -Method Get
$farmers | Format-Table id, name, phone, district, preferredLanguage, upiId

# 2. Raja's Module: Query Policy Thresholds for Anantapur
Write-Host "`n[Step 2] Raja's Trigger Engine queries policy thresholds for Anantapur:" -ForegroundColor Yellow
$thresholds = Invoke-RestMethod -Uri "$baseUrl/api/v1/integration/raja/thresholds?district=Anantapur" -Method Get
Write-Host "District: $($thresholds.district) | Active Policies: $($thresholds.activePoliciesCount) | Avg Threshold: $($thresholds.averageThresholdMm) mm" -ForegroundColor Green

# 3. Simulate Madhav's Flutter Offline Sync Batch
Write-Host "`n[Step 3] Madhav's Flutter App performs Offline Sync (Background Sync):" -ForegroundColor Yellow
$syncBody = @{
    deviceId = "redmi-note-11-demo"
    farmerUuid = "f1-anantapur-uuid-001"
    offlinePolicies = @(
        @{
            policyUuid = "pol-offline-" + [guid]::NewGuid().ToString()
            policyNumber = "POL-ANTP-SYNC-999"
            farmerUuid = "f1-anantapur-uuid-001"
            cropType = "Groundnut"
            district = "Anantapur"
            season = "Kharif 2026"
            coverageAmount = 22000
            premiumAmount = 660
            thresholdRainfallMm = 45.0
            triggerOperator = "LESS_THAN"
        }
    )
} | ConvertTo-Json -Depth 5

$syncResponse = Invoke-RestMethod -Uri "$baseUrl/api/v1/sync" -Method Post -ContentType "application/json" -Body $syncBody
Write-Host "Sync Result: $($syncResponse.message)" -ForegroundColor Green
Write-Host "TTS Voice Message Generated for Madhav's Mobile App:" -ForegroundColor Magenta
$syncResponse.voiceNotifications | ForEach-Object {
    Write-Host "  -> Language: $($_.languageCode) | Audio Script: $($_.spokenMessage)" -ForegroundColor White
}

# 4. 1-Click Hackathon Demo: Simulate Drought Rainfall Event
Write-Host "`n[Step 4] Triggering Parametric Drought Event (12.5mm vs 50mm threshold):" -ForegroundColor Yellow
$demoResult = Invoke-RestMethod -Uri "$baseUrl/api/v1/demo/simulate-drought?district=Anantapur&rainfallMm=12.5" -Method Post
Write-Host "Trigger Decision Met: $($demoResult.step3_raja_trigger.conditionMet)" -ForegroundColor Red
Write-Host "Affected Policies: $($demoResult.step3_raja_trigger.affectedPolicies)" -ForegroundColor Yellow
Write-Host "Payout & Audit Status: $($demoResult.step4_anosh_payout_audit)" -ForegroundColor Green

# 5. Fetch Audit Trail (Anosh Integration)
Write-Host "`n[Step 5] Ingesting Immutable Audit Trail from Backend:" -ForegroundColor Yellow
$audit = Invoke-RestMethod -Uri "$baseUrl/api/v1/integration/anosh/audit-logs" -Method Get
$audit | Select-Object -First 5 | Format-Table timestamp, action, actor, entityType, details

Write-Host "`nAll 5 module connections successfully demonstrated!" -ForegroundColor Green
Write-Host "Open Swagger UI in your browser: http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan
