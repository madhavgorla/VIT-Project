import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:sqflite/sqflite.dart';
import 'package:sqflite_common_ffi_web/sqflite_ffi_web.dart';
import 'core/theme/app_theme.dart';
import 'providers/app_provider.dart';
import 'screens/splash/splash_screen.dart';
import 'screens/registration/registration_screen.dart';
import 'screens/settings/settings_screen.dart';
import 'screens/help/voice_help_screen.dart';
import 'widgets/app_shell.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  if (kIsWeb) databaseFactory = databaseFactoryFfiWeb;
  runApp(const CodeRabbitsApp());
}

class CodeRabbitsApp extends StatelessWidget {
  const CodeRabbitsApp({super.key});
  @override
  Widget build(BuildContext c) => ChangeNotifierProvider(
      create: (_) => AppProvider(),
      child: MaterialApp(
          title: 'Code Rabbits • Farm Protection',
          debugShowCheckedModeBanner: false,
          theme: AppTheme.light(),
          initialRoute: '/',
          routes: {
            '/': (_) => const SplashScreen(),
            '/app': (_) => const AppShell(),
            '/register': (_) => const RegistrationScreen(),
            '/settings': (_) => const SettingsScreen(),
            '/help': (_) => const VoiceHelpScreen()
          }));
}
