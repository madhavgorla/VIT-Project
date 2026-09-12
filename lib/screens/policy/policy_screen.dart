import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../core/utils/formatters.dart';
import '../../core/constants/app_colors.dart';

class PolicyScreen extends StatelessWidget {
  const PolicyScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = context.watch<AppProvider>();
    final policy = provider.policy;
    if (policy == null) return const Center(child: Text('No policy available'));
    return ListView(
        padding: const EdgeInsets.fromLTRB(16, 12, 16, 110),
        children: [
          _heading('Your protection plan',
              'A clear view of what your farm cover includes.'),
          const SizedBox(height: 16),
          Card(
              color: AppColors.greenDark,
              child: Padding(
                  padding: const EdgeInsets.all(20),
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(children: [
                          Container(
                              width: 48,
                              height: 48,
                              decoration: BoxDecoration(
                                  color: Colors.white.withValues(alpha: .12),
                                  shape: BoxShape.circle),
                              child: const Icon(Icons.shield_rounded,
                                  color: Colors.white, size: 28)),
                          const SizedBox(width: 12),
                          Expanded(
                              child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                const Text('FARM PROTECTION POLICY',
                                    style: TextStyle(
                                        color: Colors.white70,
                                        fontSize: 11,
                                        fontWeight: FontWeight.w900,
                                        letterSpacing: 1)),
                                Text(policy.policyId,
                                    style: const TextStyle(
                                        color: Colors.white,
                                        fontSize: 21,
                                        fontWeight: FontWeight.w900))
                              ])),
                          _statusPill(policy.status)
                        ]),
                        const SizedBox(height: 22),
                        Row(children: [
                          const Icon(Icons.grass_rounded,
                              color: AppColors.gold, size: 21),
                          const SizedBox(width: 8),
                          Text(policy.crop,
                              style: const TextStyle(
                                  color: Colors.white,
                                  fontSize: 18,
                                  fontWeight: FontWeight.w800)),
                          const Spacer(),
                          Text(money(policy.coverage),
                              style: const TextStyle(
                                  color: Colors.white,
                                  fontSize: 20,
                                  fontWeight: FontWeight.w900))
                        ]),
                        const SizedBox(height: 4),
                        const Padding(
                            padding: EdgeInsets.only(left: 29),
                            child: Text('protected coverage',
                                style: TextStyle(
                                    color: Colors.white70, fontSize: 12)))
                      ]))),
          const SizedBox(height: 18),
          const Text('Policy details',
              style: TextStyle(fontSize: 19, fontWeight: FontWeight.w900)),
          const SizedBox(height: 10),
          _detailTile(
              Icons.grass_rounded, 'Crop', policy.crop, AppColors.green),
          _detailTile(Icons.location_on_outlined, 'District', policy.district,
              AppColors.leaf),
          _detailTile(Icons.water_drop_outlined, 'Rainfall threshold',
              '${policy.threshold.toStringAsFixed(0)} mm', AppColors.blue),
          _detailTile(Icons.currency_rupee_rounded, 'Premium',
              money(policy.premium), AppColors.gold),
          const SizedBox(height: 10),
          Card(
              child: ListTile(
                  leading: Icon(
                      provider.online
                          ? Icons.cloud_done_outlined
                          : Icons.cloud_off_outlined,
                      color: provider.online ? AppColors.green : AppColors.red),
                  title: Text(
                      provider.online
                          ? 'Latest policy is synchronized'
                          : 'Offline Mode',
                      style: const TextStyle(fontWeight: FontWeight.w900)),
                  subtitle: Text(provider.online
                      ? 'Your policy is safely up to date.'
                      : 'Your saved farm information is still available.'))),
          const SizedBox(height: 14),
          FilledButton.icon(
              onPressed: () => Navigator.pushNamed(context, '/help'),
              icon: const Icon(Icons.volume_up_rounded),
              label: const Text('Hear my policy')),
        ]);
  }

  Widget _heading(String title, String subtitle) =>
      Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Text(title,
            style: const TextStyle(fontSize: 26, fontWeight: FontWeight.w900)),
        const SizedBox(height: 5),
        Text(subtitle, style: const TextStyle(color: AppColors.muted))
      ]);
  Widget _statusPill(String value) => Container(
      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 7),
      decoration: BoxDecoration(
          color: AppColors.greenSoft, borderRadius: BorderRadius.circular(20)),
      child: Text(value,
          style: const TextStyle(
              color: AppColors.greenDark,
              fontSize: 11,
              fontWeight: FontWeight.w900)));
  Widget _detailTile(IconData icon, String title, String value, Color color) =>
      Card(
          child: ListTile(
              contentPadding:
                  const EdgeInsets.symmetric(horizontal: 16, vertical: 5),
              leading: Container(
                  width: 42,
                  height: 42,
                  decoration: BoxDecoration(
                      color: color.withValues(alpha: .1),
                      borderRadius: BorderRadius.circular(12)),
                  child: Icon(icon, color: color)),
              title: Text(title,
                  style: const TextStyle(
                      color: AppColors.muted,
                      fontSize: 12,
                      fontWeight: FontWeight.w700)),
              subtitle: Text(value,
                  style: const TextStyle(
                      fontSize: 16, fontWeight: FontWeight.w900))));
}
