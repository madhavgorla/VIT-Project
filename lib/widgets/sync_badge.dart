import 'package:flutter/material.dart';
import '../core/constants/app_colors.dart';

class SyncBadge extends StatelessWidget {
  final int pending;
  const SyncBadge({super.key, required this.pending});

  @override
  Widget build(BuildContext context) {
    final waiting = pending > 0;
    final color = waiting ? AppColors.amber : AppColors.green;
    return Container(
        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 7),
        decoration: BoxDecoration(
            color: waiting ? AppColors.amberSoft : AppColors.greenSoft,
            borderRadius: BorderRadius.circular(20)),
        child: Row(mainAxisSize: MainAxisSize.min, children: [
          Icon(
              waiting ? Icons.cloud_upload_outlined : Icons.cloud_done_outlined,
              size: 16,
              color: color),
          const SizedBox(width: 5),
          Text(waiting ? '$pending waiting' : 'Synced',
              style: TextStyle(
                  fontSize: 11, fontWeight: FontWeight.w900, color: color))
        ]));
  }
}
