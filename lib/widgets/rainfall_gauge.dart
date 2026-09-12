import 'package:flutter/material.dart';
import '../core/constants/app_colors.dart';

class RainfallGauge extends StatelessWidget {
  final double rainfall, threshold;
  final bool triggered;
  const RainfallGauge(
      {super.key,
      required this.rainfall,
      required this.threshold,
      required this.triggered});

  @override
  Widget build(BuildContext context) {
    final ratio = (rainfall / threshold).clamp(0.0, 1.35);
    final color = triggered ? AppColors.red : AppColors.blue;
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Row(crossAxisAlignment: CrossAxisAlignment.end, children: [
        Icon(Icons.water_drop_rounded, color: color, size: 28),
        const SizedBox(width: 8),
        Text(rainfall.toStringAsFixed(0),
            style: const TextStyle(
                fontSize: 40, height: 1, fontWeight: FontWeight.w900)),
        const SizedBox(width: 6),
        const Padding(
            padding: EdgeInsets.only(bottom: 4),
            child: Text('mm',
                style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w800,
                    color: AppColors.muted)))
      ]),
      const SizedBox(height: 14),
      ClipRRect(
          borderRadius: BorderRadius.circular(10),
          child: LinearProgressIndicator(
              minHeight: 14,
              value: ratio / 1.35,
              backgroundColor: AppColors.blueSoft,
              color: color)),
      const SizedBox(height: 9),
      Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
        const Text('Current rainfall',
            style: TextStyle(
                color: AppColors.muted,
                fontSize: 12,
                fontWeight: FontWeight.w700)),
        Text('Threshold ${threshold.toStringAsFixed(0)} mm',
            style: const TextStyle(
                color: AppColors.ink,
                fontSize: 12,
                fontWeight: FontWeight.w900))
      ]),
    ]);
  }
}
