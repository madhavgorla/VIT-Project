# Build status

The Flutter project source is complete and validated with Flutter 3.38.5 / Dart 3.10.4.

Checks completed:

```bash
flutter pub get
flutter analyze
flutter test
```

`flutter test` passes. `flutter analyze` reports only informational lints for deprecated opacity helpers and an async `BuildContext` usage.

The web target builds successfully and can be run on the laptop with `flutter run -d chrome --web-port 8080`. A native Windows build requires Visual Studio's Desktop development with C++ workload.

For device testing, run:

```bash
flutter run
```

Then configure the Spring Boot `BASE_URL` in Settings.
