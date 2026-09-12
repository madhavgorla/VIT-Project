import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/constants/app_colors.dart';
import '../../core/utils/formatters.dart';
import '../../widgets/farm_hero_card.dart';
import '../../widgets/rainfall_gauge.dart';
import '../../widgets/info_card.dart';
import '../../widgets/sync_badge.dart';

class HomeDashboardScreen extends StatelessWidget {
  const HomeDashboardScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = context.watch<AppProvider>();
    if (provider.loading ||
        provider.farmer == null ||
        provider.policy == null ||
        provider.rainfall == null) {
      return const Center(child: CircularProgressIndicator());
    }
    final farmer = provider.farmer!;
    final policy = provider.policy!;
    final rainfall = provider.rainfall!;
    final payout = provider.payout;
    final cardWidth = (MediaQuery.sizeOf(context).width - 44) / 2;

    return RefreshIndicator(
      onRefresh: () => provider.refreshFromBackend(),
      child: ListView(
        padding: const EdgeInsets.fromLTRB(16, 12, 16, 120),
        children: [
          Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Expanded(
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                    const Text('Good day,',
                        style: TextStyle(
                            color: AppColors.muted,
                            fontSize: 14,
                            fontWeight: FontWeight.w700)),
                    Text(farmer.name.split(' ').first,
                        style: const TextStyle(
                            fontSize: 28, fontWeight: FontWeight.w900))
                  ])),
              SyncBadge(pending: provider.pending),
            ],
          ),
          const SizedBox(height: 5),
          Row(children: [
            const Icon(Icons.location_on_outlined,
                color: AppColors.leaf, size: 17),
            const SizedBox(width: 5),
            Text('${farmer.village}, ${farmer.district}',
                style: const TextStyle(
                    color: AppColors.muted, fontWeight: FontWeight.w700))
          ]),
          const SizedBox(height: 18),
          FarmHeroCard(
              name: farmer.name,
              status: provider.protectionStatus,
              district: '${farmer.village}, ${farmer.district}',
              crop: policy.crop),
          const SizedBox(height: 22),
          _sectionTitle('Rainfall today', 'Your protection follows the rain'),
          const SizedBox(height: 10),
          _rainfallCard(rainfall, policy),
          const SizedBox(height: 22),
          _sectionTitle('Your cover at a glance', 'Simple protection details'),
          const SizedBox(height: 10),
          Wrap(
            spacing: 12,
            runSpacing: 12,
            children: [
              SizedBox(
                  width: cardWidth,
                  child: InfoCard(
                      title: 'Policy status',
                      value: policy.status,
                      icon: Icons.shield_outlined,
                      color: AppColors.green,
                      onSpeak: () => provider.tts
                          .speak('Your policy is ${policy.status}.'))),
              SizedBox(
                  width: cardWidth,
                  child: InfoCard(
                      title: 'Coverage',
                      value: money(policy.coverage),
                      icon: Icons.currency_rupee_rounded,
                      color: AppColors.gold,
                      onSpeak: () => provider.tts.speak(
                          'Your coverage is ${policy.coverage.toStringAsFixed(0)} rupees.'))),
              SizedBox(
                  width: cardWidth,
                  child: InfoCard(
                      title: 'Rain threshold',
                      value: '${policy.threshold.toStringAsFixed(0)} mm',
                      icon: Icons.flag_outlined,
                      color: AppColors.blue)),
              SizedBox(
                  width: cardWidth,
                  child: InfoCard(
                      title: 'Payout status',
                      value: payout?.status ?? 'Not available',
                      icon: Icons.account_balance_wallet_outlined,
                      color: payout?.status == 'MONITORING'
                          ? AppColors.amber
                          : AppColors.green,
                      onSpeak: provider.speakPayout)),
            ],
          ),
          const SizedBox(height: 18),
          Card(
            color: AppColors.greenSoft,
            child: Padding(
              padding: const EdgeInsets.all(16),
              child: Row(children: [
                const Icon(Icons.cloud_sync_outlined,
                    color: AppColors.greenDark, size: 28),
                const SizedBox(width: 12),
                const Expanded(
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                      Text('Sync Farm Data',
                          style: TextStyle(
                              color: AppColors.greenDark,
                              fontWeight: FontWeight.w900,
                              fontSize: 16)),
                      SizedBox(height: 3),
                      Text(
                          'Keep your farm information ready across online and offline moments.',
                          style:
                              TextStyle(color: AppColors.muted, fontSize: 12))
                    ])),
                const SizedBox(width: 8),
                IconButton(
                    tooltip: 'Sync farm data',
                    onPressed:
                        provider.syncing ? null : () => provider.syncPending(),
                    icon: const Icon(Icons.arrow_forward_rounded,
                        color: AppColors.greenDark)),
              ]),
            ),
          ),
        ],
      ),
    );
  }

  Widget _rainfallCard(dynamic rainfall, dynamic policy) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(18),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Row(children: [
            Container(
                width: 40,
                height: 40,
                decoration: BoxDecoration(
                    color: AppColors.blueSoft,
                    borderRadius: BorderRadius.circular(12)),
                child: const Icon(Icons.cloud_outlined, color: AppColors.blue)),
            const SizedBox(width: 11),
            const Expanded(
                child: Text('Current rainfall',
                    style:
                        TextStyle(fontSize: 16, fontWeight: FontWeight.w900))),
            _statusPill(
                rainfall.triggerMet ? 'Trigger condition met' : 'Monitoring',
                rainfall.triggerMet ? AppColors.red : AppColors.green)
          ]),
          const SizedBox(height: 18),
          RainfallGauge(
              rainfall: rainfall.rainfall,
              threshold: policy.threshold,
              triggered: rainfall.triggerMet),
        ]),
      ),
    );
  }

  Widget _sectionTitle(String title, String subtitle) =>
      Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Text(title,
            style: const TextStyle(fontSize: 19, fontWeight: FontWeight.w900)),
        const SizedBox(height: 3),
        Text(subtitle,
            style: const TextStyle(color: AppColors.muted, fontSize: 13))
      ]);

  Widget _statusPill(String label, Color color) => Container(
      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 7),
      decoration: BoxDecoration(
          color: color.withValues(alpha: .1),
          borderRadius: BorderRadius.circular(20)),
      child: Text(label,
          style: TextStyle(
              color: color, fontSize: 11, fontWeight: FontWeight.w900)));
}
