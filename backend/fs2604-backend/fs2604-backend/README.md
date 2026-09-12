# FS-2604: Offline-First Parametric Micro-Insurance for Low-Connectivity Users
## Team: Code Rabbits 🐇
**Members & Modules:**
- **Madhav**: Farmer Mobile App (`Flutter + SQLite + Text-to-Speech`)
- **Ganesh (You)**: Backend & APIs (`Java 21 + Spring Boot 3 + REST API`)
- **Raja**: Rainfall + Trigger Engine (`IMD / CHIRPS + Spring Boot`)
- **Anosh**: Database + Payout + Audit (`PostgreSQL + Spring Boot`)

---

## 1. System Flow & Connections

```
Farmer
  │
  ▼
Madhav (Flutter Mobile App + Offline SQLite + TTS)
  │  ▲
  │  │ (Background Offline Sync: Batch registrations, policies, payouts, & audio scripts)
  ▼  │
Ganesh (Spring Boot API Hub & Orchestrator) ──[Port 8080]
  │           ▲
  │           │ (Thresholds vs Trigger Decisions)
  ▼           │
Raja (Rainfall + Trigger Engine: IMD/CHIRPS) ──[Port 8081]
  │
  ▼ (Trigger Condition Met)
Anosh (Database + Payout Processing + Audit Trail) ──[Port 8082 / PostgreSQL]
  │
  ▼ (Payout Confirmed + Audit Logged)
Ganesh (Backend API)
  │
  ▼ (Delivers Payout & Multilingual TTS notification)
Madhav (Farmer Mobile App plays Voice Notification in Telugu/Hindi/English)
```

---

## 2. Quick Start

### Prerequisites
- Java 21 LTS (Microsoft OpenJDK 21)
- Maven 3.9+

### Running the Backend
From the project directory (`fs2604-backend`):
```powershell
# Quick launch
.\run.bat
```
Or via Maven command:
```powershell
$env:JAVA_HOME = "C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10"
& "C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd" spring-boot:run
```

Once running:
- **Interactive Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **H2 Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:fs2604db`, User: `sa`, Password: empty)

---

## 3. Team API Contracts (Where to Where)

### For Madhav (Flutter Mobile App)
- **Endpoint**: `POST /api/v1/sync`
- **Purpose**: When the farmer enters network coverage, the Flutter app pushes offline data in a single batch.
- **Request Body**:
```json
{
  "deviceId": "mobile-client-uuid",
  "farmerUuid": "f1-anantapur-uuid-001",
  "clientTimestamp": "2026-09-12T10:00:00",
  "offlineFarmers": [ ... ],
  "offlinePolicies": [
    {
      "policyUuid": "uuid-v4",
      "cropType": "Groundnut",
      "district": "Anantapur",
      "season": "Kharif 2026",
      "coverageAmount": 25000.0,
      "premiumAmount": 750.0,
      "thresholdRainfallMm": 45.0,
      "triggerOperator": "LESS_THAN"
    }
  ]
}
```
- **Response**: Returns acknowledged UUIDs, updated policy statuses (`ACTIVE`, `TRIGGERED`, `PAID_OUT`), payouts, and **ready-to-speak voice notifications** in Telugu/Hindi:
```json
{
  "acknowledgedFarmerUuids": ["..."],
  "acknowledgedPolicyUuids": ["..."],
  "updatedPolicies": [ ... ],
  "payouts": [ ... ],
  "voiceNotifications": [
    {
      "languageCode": "te",
      "title": "బీమా పాలసీ ఆమోదించబడింది",
      "spokenMessage": "నమస్కారం రమేష్ గారు, మీ వేరుశనగ పంట బీమా పాలసీ విజయవంతంగా సింక్ అయ్యింది."
    }
  ]
}
```

---

### For Raja (Rainfall + Trigger Engine)
1. **Fetch Policy Thresholds for District**:
   - `GET /api/v1/integration/raja/thresholds?district=Anantapur`
   - Returns active policy count and average rainfall trigger threshold in mm so Raja's engine knows when to trigger.
2. **Post Trigger Result**:
   - `POST /api/v1/integration/raja/trigger-result`
   - Raja posts rainfall readings from IMD / CHIRPS. If rainfall breaches threshold, Ganesh's backend immediately dispatches payouts!

---

### For Anosh (Database + Payout + Audit)
1. **Execute Payout**:
   - `POST /api/v1/integration/anosh/payout`
   - Records disbursement transaction reference and updates policy to `PAID_OUT`.
2. **Audit Trail**:
   - `GET /api/v1/integration/anosh/audit-logs`
   - Fetches complete immutable audit history for regulatory and transparency reporting.

---

## 4. 1-Click Hackathon Pitch Demo
During the hackathon judging, run:
```powershell
.\demo_test.ps1
```
Or trigger the 1-click demo endpoint from Swagger UI:
- `POST /api/v1/demo/simulate-drought?district=Anantapur&rainfallMm=12.5`
It simulates a drought rainfall reading (12.5mm vs 50.0mm threshold), automatically fires the trigger, generates the payout, creates the audit log, and creates the Telugu audio script for Madhav's Flutter TTS!
