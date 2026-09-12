# FS-2604: Offline-First Parametric Micro-Insurance System
**Team Code Rabbits** — Hackathon Final Integrated Full-Stack Repository

---

## 👥 Team Roles & Modules
- **Madhav (Frontend):** Flutter Mobile & Web App, SQLite local offline-first caching, offline sync engine, TTS multilingual voice help (Telugu, Hindi, English).
- **Ganesh (Backend & API Hub):** Spring Boot 3 Central Microservice Mesh Hub, Farmer & Policy Management, Flutter REST Integration Adapter, Emergency ₹10,000 Relief Engine (Port `8080`).
- **Raja (Rainfall & Trigger Engine):** Automated IMD AWS & CHIRPS Satellite precipitation ingestion, deficit analysis, parametric trigger events (Port `8081`).
- **Anosh (Payout & Audit Engine):** Automated claim disbursement, instant compensation engine (default ₹10,000), SHA-256 immutable audit trails (Port `8082`).

---

## 🌐 Port Allocations (No Conflicts)
| Service / Component | Port | URL / Health / Documentation |
| :--- | :--- | :--- |
| **Ganesh: Central API Hub** | `8080` | `http://localhost:8080/swagger-ui.html` |
| **Raja: Trigger Engine** | `8081` | `http://localhost:8081/swagger-ui.html` |
| **Anosh: Payout & Audit** | `8082` | `http://localhost:8082/swagger-ui.html` |
| **Madhav: Flutter Web App** | `3000` | `http://localhost:3000` |

---

## 🚀 How to Run in Visual Studio Code (Step-by-Step)

### Option 1: Using VS Code Run & Debug (Recommended 1-Click)
1. Open this repository folder in **VS Code**:
   ```bash
   code .
   ```
2. Open the **Run and Debug** view (`Ctrl + Shift + D` on Windows / Linux).
3. In the dropdown at the top, select:
   - **`📱 Flutter (Chrome Web - Port 3000)`** to launch the Flutter UI in Google Chrome.
   - Or **`🖥️ Flutter (Windows Desktop)`** to launch as a native Windows desktop app.
   - Or **`🚀 Full Mesh: 3 Backends + Flutter Web`** to launch the entire stack together.
4. Press **`F5`** or click the green **Play (▷)** button.

---

### Option 2: Using the Integrated VS Code Terminal (1-Command Launch)
Open the integrated terminal in VS Code (`Ctrl + ` ` `) and run:

```cmd
.\start-all.bat
```
*(or with PowerShell: `.\start-all.ps1`)*

This starts all 3 backend microservices and the Flutter frontend in separate organized terminal windows!

---

### Option 3: Running Services Individually in VS Code Terminals

#### Terminal 1 — Ganesh's Backend Hub (Port 8080):
```bash
cd backend/fs2604-backend
mvn spring-boot:run
```

#### Terminal 2 — Raja's Trigger Engine (Port 8081):
```bash
cd backend/fs2604-raja-trigger
mvn spring-boot:run
```

#### Terminal 3 — Anosh's Payout & Audit Engine (Port 8082):
```bash
cd backend/fs2604-anosh-payout
mvn spring-boot:run
```

#### Terminal 4 — Madhav's Flutter Frontend (Port 3000):
```bash
flutter run -d chrome --web-port 3000
```
*(For Android emulator: simply run `flutter run` - it will automatically use `http://10.0.2.2:8080`)*

---

## 🧪 Verification & Automated Testing
To run the automated health check and complete test suite across both Frontend and all 3 Backend microservices, execute:

```powershell
powershell -ExecutionPolicy Bypass -File .\run-all-tests.ps1
```

Or run tests individually:
```bash
# Frontend analysis & tests
flutter analyze    # 0 issues found
flutter test       # All widget/unit tests pass

# Backend microservices tests
mvn -f backend/fs2604-backend/pom.xml test
mvn -f backend/fs2604-raja-trigger/pom.xml test
mvn -f backend/fs2604-anosh-payout/pom.xml test
```

---

## 💡 Key Features & Integration Highlights
1. **Dynamic Platform Base URL:**
   - Automatically connects to `http://localhost:8080` when running on Web or Desktop.
   - Automatically connects to `http://10.0.2.2:8080` when running inside the Android emulator.
2. **Offline-First SQLite Cache:**
   - 6 local DAOs (`farmer_dao`, `policy_dao`, `rainfall_dao`, `payout_dao`, `notification_dao`, `sync_dao`) ensure the farmer has full offline capability even in zero-connectivity areas.
   - Actions performed offline are queued in SQLite and auto-reconciled with `POST /api/sync` once internet connectivity is restored.
3. **Emergency ₹10,000 Rainfall Relief:**
   - Directly triggered when rainfall drops below the parametric crop threshold (e.g. 34mm vs 45mm).
   - Automated payout record generated with immutable SHA-256 audit reference.
4. **Multilingual Text-to-Speech:**
   - Supports regional voice readouts in Telugu (`te-IN`), Hindi (`hi-IN`), and English (`en-IN`).
