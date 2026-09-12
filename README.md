# Code Rabbits — FS-2604 Farmer Mobile App

Madhav's frontend module for **Offline-First Parametric Micro-Insurance for Low-Connectivity Users**.

## What is included
- Flutter/Dart farmer-facing mobile UI
- SQLite offline cache with six tables
- Offline sync queue and retry handling
- Connectivity banner and last-known-data behavior
- Rainfall threshold visualization
- Payout status visualization
- Text-to-Speech voice help
- Telugu, Hindi and English voice-language architecture
- Configurable Spring Boot `BASE_URL`
- Hackathon demo scenarios: Normal, Trigger, Offline
- REST API client for the team backend

## Team boundaries
- **Madhav:** Flutter, SQLite, TTS, offline UX, sync client and API consumption
- **Ganesh:** Spring Boot REST APIs
- **Raja:** IMD/CHIRPS rainfall ingestion and trigger engine
- **Anosh:** PostgreSQL, payout recording and audit trails

## Run
Install Flutter 3.x and Android Studio, then:

```bash
flutter pub get
flutter analyze
flutter run
```

To run independently on a laptop in Chrome, use:

```bash
flutter run -d chrome --web-port 8080
```

Then open `http://localhost:8080`. The phone and USB cable are not required.
The project also includes a Windows desktop target; building it requires Visual Studio with the **Desktop development with C++** workload.

For an Android emulator, the default backend URL is `http://10.0.2.2:8080`.
For a physical phone, set the backend URL in **Settings → Spring Boot BASE_URL** to the computer's LAN IP, e.g. `http://192.168.1.10:8080`.

## Demo
The app starts with safe local demo data so the frontend can be demonstrated without a backend.

Settings → Demo scenario:
1. **Normal rainfall:** 51 mm vs 45 mm → Monitoring.
2. **Deficit rainfall:** 32 mm vs 45 mm → Trigger detected + payout initiated.
3. **Offline simulation:** shows offline-first UI and cached information.

> Demo data is clearly intended for demonstration. Production trigger/payout decisions must come from the backend.

## Backend contract used by the client
- `POST /api/farmers`
- `GET /api/farmers/{id}`
- `POST /api/policies`
- `GET /api/policies/farmer/{farmerId}`
- `GET /api/rainfall/{district}`
- `GET /api/payout/{policyId}`
- `GET /api/notifications/{farmerId}`
- `POST /api/sync`

## Validation note
The project has been checked with Flutter 3.38.5 and Dart 3.10.4. Dependencies resolve successfully and the existing Flutter test suite passes. Analyzer output contains only informational lints.
