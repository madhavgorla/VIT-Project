import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/constants/app_colors.dart';

class NotificationCenterScreen extends StatelessWidget {
  const NotificationCenterScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final notifications = context.watch<AppProvider>().notifications;
    return ListView(
        padding: const EdgeInsets.fromLTRB(16, 12, 16, 110),
        children: [
          const Text('Farm alerts',
              style: TextStyle(fontSize: 26, fontWeight: FontWeight.w900)),
          const SizedBox(height: 5),
          const Text('Important updates about your crop protection.',
              style: TextStyle(color: AppColors.muted)),
          const SizedBox(height: 16),
          if (notifications.isEmpty)
            Card(
                child: Padding(
                    padding: const EdgeInsets.all(28),
                    child: Column(children: [
                      Container(
                          width: 60,
                          height: 60,
                          decoration: const BoxDecoration(
                              color: AppColors.greenSoft,
                              shape: BoxShape.circle),
                          child: const Icon(Icons.notifications_none_rounded,
                              color: AppColors.green, size: 31)),
                      const SizedBox(height: 14),
                      const Text('No alerts yet',
                          style: TextStyle(
                              fontSize: 18, fontWeight: FontWeight.w900)),
                      const SizedBox(height: 5),
                      const Text(
                          'We will show policy, rainfall, and payout updates here.',
                          textAlign: TextAlign.center,
                          style: TextStyle(color: AppColors.muted))
                    ])))
          else
            ...notifications.map((notification) {
              final isTrigger = notification.type == 'TRIGGER';
              final isPolicy = notification.type == 'POLICY';
              final icon = isTrigger
                  ? Icons.water_drop_rounded
                  : isPolicy
                      ? Icons.shield_outlined
                      : Icons.notifications_rounded;
              final color = isTrigger
                  ? AppColors.red
                  : isPolicy
                      ? AppColors.green
                      : AppColors.blue;
              return Padding(
                  padding: const EdgeInsets.only(bottom: 10),
                  child: Card(
                      child: ListTile(
                          contentPadding: const EdgeInsets.all(15),
                          leading: Container(
                              width: 46,
                              height: 46,
                              decoration: BoxDecoration(
                                  color: color.withValues(alpha: .1),
                                  borderRadius: BorderRadius.circular(14)),
                              child: Icon(icon, color: color)),
                          title: Text(notification.title,
                              style:
                                  const TextStyle(fontWeight: FontWeight.w900)),
                          subtitle: Padding(
                              padding: const EdgeInsets.only(top: 5),
                              child: Text(notification.message)),
                          trailing: Icon(Icons.chevron_right_rounded,
                              color: color))));
            }),
        ]);
  }
}
