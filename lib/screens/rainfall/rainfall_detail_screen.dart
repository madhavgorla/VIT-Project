import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/app_provider.dart';
import '../../widgets/rainfall_gauge.dart';
import '../../core/constants/app_colors.dart';
import '../../core/utils/formatters.dart';

class RainfallDetailScreen extends StatelessWidget {
  const RainfallDetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final provider = context.watch<AppProvider>();
    if (provider.rainfall == null || provider.policy == null) {
      return const Center(child: CircularProgressIndicator());
    }
    final rainfall = provider.rainfall!;
    final policy = provider.policy!;
    final gap = policy.threshold - rainfall.rainfall;
    final statusColor = rainfall.triggerMet ? AppColors.red : AppColors.green;
    return ListView(
        padding: const EdgeInsets.fromLTRB(16, 12, 16, 110),
        children: [
          const Text('Rainfall monitor',
              style: TextStyle(fontSize: 26, fontWeight: FontWeight.w900)),
          const SizedBox(height: 5),
          const Text('Rain is the signal that protects your crop.',
              style: TextStyle(color: AppColors.muted)),
          const SizedBox(height: 16),
          Card(
              child: Padding(
                  padding: const EdgeInsets.all(20),
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(children: [
                          Container(
                              width: 46,
                              height: 46,
                              decoration: BoxDecoration(
                                  color: AppColors.blueSoft,
                                  borderRadius: BorderRadius.circular(14)),
                              child: const Icon(Icons.cloud_outlined,
                                  color: AppColors.blue, size: 27)),
                          const SizedBox(width: 12),
                          const Expanded(
                              child: Text('Rainfall today',
                                  style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.w900))),
                          _pill(
                              rainfall.triggerMet
                                  ? 'Trigger condition met'
                                  : 'Monitoring',
                              statusColor)
                        ]),
                        const SizedBox(height: 24),
                        RainfallGauge(
                            rainfall: rainfall.rainfall,
                            threshold: policy.threshold,
                            triggered: rainfall.triggerMet),
                        const SizedBox(height: 18),
                        Text(
                            rainfall.triggerMet
                                ? 'Rainfall is ${gap.abs().toStringAsFixed(0)} mm below the threshold.'
                                : 'Rainfall is ${gap.toStringAsFixed(0)} mm above the threshold.',
                            style: const TextStyle(
                                color: AppColors.muted,
                                fontWeight: FontWeight.w700))
                      ]))),
          const SizedBox(height: 18),
          const Text('What this means',
              style: TextStyle(fontSize: 19, fontWeight: FontWeight.w900)),
          const SizedBox(height: 10),
          _tile(Icons.water_drop_rounded, 'Current rain', mm(rainfall.rainfall),
              AppColors.blue),
          _tile(Icons.flag_outlined, 'Protection threshold',
              mm(policy.threshold), AppColors.gold),
          _tile(Icons.cloud_outlined, 'Data source', rainfall.source,
              AppColors.leaf),
          _tile(Icons.schedule_rounded, 'Last updated',
              dateTimeText(rainfall.timestamp), AppColors.muted),
        ]);
  }

  Widget _pill(String label, Color color) => Container(
      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 7),
      decoration: BoxDecoration(
          color: color.withValues(alpha: .1),
          borderRadius: BorderRadius.circular(20)),
      child: Text(label,
          style: TextStyle(
              color: color, fontSize: 11, fontWeight: FontWeight.w900)));
  Widget _tile(IconData icon, String title, String value, Color color) => Card(
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
              style:
                  const TextStyle(fontWeight: FontWeight.w900, fontSize: 16))));
}
