import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/constants/app_colors.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});
  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen>
    with SingleTickerProviderStateMixin {
  late final AnimationController _controller = AnimationController(
      vsync: this, duration: const Duration(milliseconds: 1000))
    ..forward();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) async {
      await context.read<AppProvider>().initialize();
      if (mounted) Navigator.pushReplacementNamed(context, '/app');
    });
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => Scaffold(
      body: SafeArea(
          child: AnimatedBuilder(
              animation: _controller,
              builder: (context, child) => Opacity(
                  opacity: _controller.value,
                  child: Transform.translate(
                      offset: Offset(0, 18 * (1 - _controller.value)),
                      child: child)),
              child: Center(
                  child: Padding(
                      padding: const EdgeInsets.all(28),
                      child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Container(
                                width: 112,
                                height: 112,
                                decoration: BoxDecoration(
                                    color: AppColors.greenDark,
                                    borderRadius: BorderRadius.circular(34),
                                    boxShadow: [
                                      BoxShadow(
                                          color: AppColors.green
                                              .withValues(alpha: .2),
                                          blurRadius: 24,
                                          offset: const Offset(0, 12))
                                    ]),
                                child: const Stack(
                                    alignment: Alignment.center,
                                    children: [
                                      Icon(Icons.shield_rounded,
                                          color: Colors.white, size: 68),
                                      Positioned(
                                          bottom: 21,
                                          child: Icon(Icons.grass_rounded,
                                              color: AppColors.gold, size: 30))
                                    ])),
                            const SizedBox(height: 28),
                            const Text('Code Rabbits',
                                style: TextStyle(
                                    fontSize: 31,
                                    fontWeight: FontWeight.w900,
                                    color: AppColors.ink)),
                            const SizedBox(height: 10),
                            const Text('Protecting Farmers. Protecting Crops.',
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                    fontSize: 17,
                                    color: AppColors.greenDark,
                                    fontWeight: FontWeight.w800)),
                            const SizedBox(height: 8),
                            const Text(
                                'Simple protection for every growing season',
                                textAlign: TextAlign.center,
                                style: TextStyle(color: AppColors.muted)),
                            const SizedBox(height: 34),
                            const SizedBox(
                                width: 28,
                                height: 28,
                                child: CircularProgressIndicator(
                                    strokeWidth: 3, color: AppColors.gold)),
                          ]))))));
}
