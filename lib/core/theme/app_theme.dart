import 'package:flutter/material.dart';
import '../constants/app_colors.dart';

class AppTheme {
  static ThemeData light() {
    final scheme = ColorScheme.fromSeed(
            seedColor: AppColors.green, brightness: Brightness.light)
        .copyWith(
      primary: AppColors.green,
      onPrimary: Colors.white,
      secondary: AppColors.gold,
      surface: AppColors.cream,
      onSurface: AppColors.ink,
      outline: AppColors.line,
    );
    return ThemeData(
      useMaterial3: true,
      colorScheme: scheme,
      scaffoldBackgroundColor: AppColors.surface,
      fontFamily: 'Roboto',
      textTheme: const TextTheme(
        headlineSmall: TextStyle(
            fontSize: 26, fontWeight: FontWeight.w900, color: AppColors.ink),
        titleLarge: TextStyle(
            fontSize: 20, fontWeight: FontWeight.w900, color: AppColors.ink),
        titleMedium: TextStyle(
            fontSize: 16, fontWeight: FontWeight.w800, color: AppColors.ink),
        bodyLarge: TextStyle(fontSize: 16, height: 1.35, color: AppColors.ink),
        bodyMedium:
            TextStyle(fontSize: 14, height: 1.35, color: AppColors.muted),
      ),
      appBarTheme: const AppBarTheme(
        backgroundColor: AppColors.cream,
        foregroundColor: AppColors.ink,
        elevation: 0,
        centerTitle: false,
        surfaceTintColor: Colors.transparent,
        titleTextStyle: TextStyle(
            fontSize: 21, fontWeight: FontWeight.w900, color: AppColors.ink),
      ),
      cardTheme: CardThemeData(
        elevation: 0,
        color: AppColors.cream,
        surfaceTintColor: Colors.transparent,
        shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(18),
            side: const BorderSide(color: AppColors.line)),
        margin: EdgeInsets.zero,
      ),
      inputDecorationTheme: InputDecorationTheme(
        filled: true,
        fillColor: AppColors.cream,
        labelStyle: const TextStyle(
            color: AppColors.muted, fontWeight: FontWeight.w600),
        border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(16),
            borderSide: const BorderSide(color: AppColors.line)),
        enabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(16),
            borderSide: const BorderSide(color: AppColors.line)),
        focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(16),
            borderSide: const BorderSide(color: AppColors.green, width: 1.5)),
        contentPadding:
            const EdgeInsets.symmetric(horizontal: 16, vertical: 17),
      ),
      filledButtonTheme: FilledButtonThemeData(
          style: FilledButton.styleFrom(
              minimumSize: const Size.fromHeight(54),
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(16)),
              textStyle:
                  const TextStyle(fontSize: 15, fontWeight: FontWeight.w800))),
      navigationBarTheme: NavigationBarThemeData(
          backgroundColor: AppColors.cream,
          indicatorColor: AppColors.greenSoft,
          elevation: 6,
          labelTextStyle: WidgetStateProperty.all(
              const TextStyle(fontSize: 11, fontWeight: FontWeight.w800))),
    );
  }
}
