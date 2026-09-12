import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/constants/app_colors.dart';
import '../../core/utils/formatters.dart';

class PayoutScreen extends StatelessWidget {
  const PayoutScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = context.watch<AppProvider>();
    final payout = provider.payout;
    if (payout == null)
      return const Center(child: Text('No payout information available'));
    final triggered = payout.status != 'MONITORING';
    final statusColor = triggered ? AppColors.green : AppColors.amber;
    return ListView(
        padding: const EdgeInsets.fromLTRB(16, 12, 16, 110),
        children: [
          const Text('Insurance payout',
              style: TextStyle(fontSize: 26, fontWeight: FontWeight.w900)),
          const SizedBox(height: 5),
          const Text('A clear record of your farm protection payment.',
              style: TextStyle(color: AppColors.muted)),
          const SizedBox(height: 16),
          Card(
              color: triggered ? AppColors.greenDark : AppColors.cream,
              child: Padding(
                  padding: const EdgeInsets.all(22),
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(children: [
                          Container(
                              width: 52,
                              height: 52,
                              decoration: BoxDecoration(
                                  color: triggered
                                      ? Colors.white.withValues(alpha: .13)
                                      : AppColors.amberSoft,
                                  shape: BoxShape.circle),
                              child: Icon(
                                  triggered
                                      ? Icons.shield_rounded
                                      : Icons.hourglass_top_rounded,
                                  color: triggered
                                      ? Colors.white
                                      : AppColors.amber,
                                  size: 29)),
                          const SizedBox(width: 14),
                          Expanded(
                              child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                Text('PROTECTION STATUS',
                                    style: TextStyle(
                                        color: triggered
                                            ? Colors.white70
                                            : AppColors.muted,
                                        fontSize: 11,
                                        fontWeight: FontWeight.w900,
                                        letterSpacing: 1)),
                                Text(
                                    triggered
                                        ? 'Payout initiated'
                                        : 'Monitoring',
                                    style: TextStyle(
                                        color: triggered
                                            ? Colors.white
                                            : AppColors.ink,
                                        fontSize: 22,
                                        fontWeight: FontWeight.w900))
                              ]))
                        ]),
                        const SizedBox(height: 22),
                        Text(money(payout.amount),
                            style: TextStyle(
                                color: triggered ? Colors.white : AppColors.ink,
                                fontSize: 38,
                                fontWeight: FontWeight.w900)),
                        Text('Policy ${payout.policyId}',
                            style: TextStyle(
                                color: triggered
                                    ? Colors.white70
                                    : AppColors.muted,
                                fontWeight: FontWeight.w700)),
                        const SizedBox(height: 20),
                        _step('Trigger detected', triggered, statusColor, triggered),
                        _step('Payout processing', triggered, statusColor, triggered),
                        _step('Payout completed',
                            payout.status == 'PAYOUT COMPLETED', statusColor, triggered)
                      ]))),
          const SizedBox(height: 16),
          Card(
              child: ListTile(
                  contentPadding: const EdgeInsets.all(16),
                  leading: const Icon(Icons.verified_outlined,
                      color: AppColors.blue, size: 28),
                  title: const Text('Trusted decision path',
                      style: TextStyle(fontWeight: FontWeight.w900)),
                  subtitle: const Padding(
                      padding: EdgeInsets.only(top: 5),
                      child: Text(
                          'Payout decisions are determined by the backend trigger system.')))),
          const SizedBox(height: 10),
          Card(
              color: AppColors.blueSoft,
              child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Text(
                      'Audit reference: ${payout.auditRef ?? 'Pending'}',
                      style: const TextStyle(
                          color: AppColors.ink, fontWeight: FontWeight.w800)))),
          const SizedBox(height: 14),
          FilledButton.icon(
              onPressed: provider.speakPayout,
              icon: const Icon(Icons.volume_up_rounded),
              label: const Text('Hear payout status')),
        ]);
  }

    Widget _step(String text, bool active, Color color, bool onDark) => Padding(
      padding: const EdgeInsets.symmetric(vertical: 3),
      child: Row(children: [
        Icon(active ? Icons.check_circle_rounded : Icons.radio_button_unchecked,
            color: active ? color : (onDark ? Colors.white54 : AppColors.muted), size: 21),
        const SizedBox(width: 10),
        Text(text,
            style: TextStyle(
                color: onDark ? (active ? Colors.white : Colors.white60) : (active ? AppColors.ink : AppColors.muted),
                fontWeight: active ? FontWeight.w800 : FontWeight.w500))
      ]));
}
