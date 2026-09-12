import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/app_provider.dart';

class VoiceActionFab extends StatelessWidget {
  const VoiceActionFab({super.key});

  @override
  Widget build(BuildContext context) => FloatingActionButton.extended(
      onPressed: () => context.read<AppProvider>().speakSummary(),
      backgroundColor: Theme.of(context).colorScheme.secondary,
      foregroundColor: Colors.white,
      icon: const Icon(Icons.volume_up_rounded),
      label: const Text('Hear update',
          style: TextStyle(fontWeight: FontWeight.w800)));
}
