import 'package:shared_preferences/shared_preferences.dart';

class DemoService {
  static const _key = 'demo_mode';
  Future<String> getMode() async =>
      (await SharedPreferences.getInstance()).getString(_key) ?? 'NORMAL';
  Future<void> setMode(String mode) async =>
      (await SharedPreferences.getInstance()).setString(_key, mode);
}
