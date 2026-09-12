import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/constants/app_colors.dart';

class VoiceHelpScreen extends StatelessWidget {
  const VoiceHelpScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = context.watch<AppProvider>();
    final actions = [
      (
        'About farm insurance',
        'What is my policy?',
        Icons.grass_rounded,
        AppColors.green,
        () => provider.tts.speak(
            'Your policy is ${provider.policy?.status ?? 'not available'} for ${provider.policy?.crop ?? 'your crop'}.')
      ),
      (
        'Understanding rainfall',
        'What is my rainfall?',
        Icons.water_drop_rounded,
        AppColors.blue,
        () => provider.tts.speak(
            'Current rainfall is ${provider.rainfall?.rainfall.toStringAsFixed(0) ?? 'not available'} millimeters.')
      ),
      (
        'Understanding protection',
        'What is my threshold?',
        Icons.shield_outlined,
        AppColors.gold,
        () => provider.tts.speak(
            'Your rainfall threshold is ${provider.policy?.threshold.toStringAsFixed(0) ?? 'not available'} millimeters.')
      ),
      (
        'Understanding payout',
        'Is my payout triggered?',
        Icons.currency_rupee_rounded,
        AppColors.amber,
        () => provider.tts.speak(
            'Your payout status is ${provider.payout?.status ?? 'not available'}.')
      ),
      (
        'Offline help',
        'Am I offline?',
        Icons.cloud_off_outlined,
        AppColors.red,
        () => provider.tts.speak(provider.online
            ? 'You are online.'
            : 'You are currently offline. Your saved information is available.')
      ),
    ];

    return Scaffold(
      appBar: AppBar(title: const Text('Voice Help')),
      body: SafeArea(
        child: ListView(
          padding: const EdgeInsets.fromLTRB(16, 10, 16, 28),
          children: [
            Container(
              padding: const EdgeInsets.all(20),
              decoration: BoxDecoration(
                  color: AppColors.greenDark,
                  borderRadius: BorderRadius.circular(22)),
              child: Row(children: [
                Container(
                    width: 50,
                    height: 50,
                    decoration: BoxDecoration(
                        color: Colors.white.withValues(alpha: .13),
                        shape: BoxShape.circle),
                    child: const Icon(Icons.volume_up_rounded,
                        color: Colors.white, size: 27)),
                const SizedBox(width: 13),
                const Expanded(
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                      Text('Voice help for your farm',
                          style: TextStyle(
                              color: Colors.white,
                              fontSize: 20,
                              fontWeight: FontWeight.w900)),
                      SizedBox(height: 5),
                      Text('Tap a topic and listen in your chosen language.',
                          style: TextStyle(color: Colors.white70))
                    ])),
              ]),
            ),
            const SizedBox(height: 22),
            const Text('What would you like to know?',
                style: TextStyle(fontSize: 19, fontWeight: FontWeight.w900)),
            const SizedBox(height: 10),
            ...actions.map((action) => _helpCard(action)),
          ],
        ),
      ),
    );
  }

  Widget _helpCard((String, String, IconData, Color, VoidCallback) action) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 10),
      child: Card(
        child: ListTile(
          contentPadding:
              const EdgeInsets.symmetric(horizontal: 15, vertical: 8),
          leading: Container(
              width: 48,
              height: 48,
              decoration: BoxDecoration(
                  color: action.$4.withValues(alpha: .12),
                  borderRadius: BorderRadius.circular(14)),
              child: Icon(action.$3, color: action.$4, size: 25)),
          title: Text(action.$1,
              style:
                  const TextStyle(fontSize: 16, fontWeight: FontWeight.w900)),
          subtitle: Padding(
              padding: const EdgeInsets.only(top: 3),
              child: Text(action.$2,
                  style: const TextStyle(color: AppColors.muted))),
          trailing: IconButton(
              tooltip: 'Listen',
              onPressed: action.$5,
              icon: const Icon(Icons.play_circle_fill_rounded,
                  size: 30, color: AppColors.green)),
        ),
      ),
    );
  }
}
