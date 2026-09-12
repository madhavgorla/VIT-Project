import 'package:flutter/material.dart';
import '../core/constants/app_colors.dart';

class ConnectivityBanner extends StatelessWidget {
  final bool online, syncing;
  final int pending;
  const ConnectivityBanner(
      {super.key,
      required this.online,
      required this.syncing,
      required this.pending});

  @override
  Widget build(BuildContext context) {
    final color = syncing
        ? AppColors.blue
        : online
            ? AppColors.green
            : AppColors.red;
    final title = syncing
        ? 'Syncing farm data'
        : online
            ? 'Connected'
            : 'Offline Mode';
    final detail = syncing
        ? 'Updating your saved farm information'
        : online
            ? (pending > 0
                ? '$pending item${pending == 1 ? '' : 's'} waiting to sync'
                : 'Your latest farm information is up to date')
            : 'Your saved farm information is still available';
    return Container(
        width: double.infinity,
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 11),
        color: color,
        child: Row(children: [
          Icon(
              syncing
                  ? Icons.sync
                  : online
                      ? Icons.cloud_done
                      : Icons.cloud_off,
              color: Colors.white,
              size: 19),
          const SizedBox(width: 10),
          Expanded(
              child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                Text(title,
                    style: const TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.w900,
                        fontSize: 13)),
                Text(detail,
                    style: TextStyle(
                        color: Colors.white.withValues(alpha: .88),
                        fontSize: 11))
              ]))
        ]));
  }
}
