import 'package:flutter/material.dart';
import '../core/constants/app_colors.dart';

class FarmHeroCard extends StatelessWidget {
  final String name;
  final String status;
  final String district;
  final String crop;
  const FarmHeroCard(
      {super.key,
      required this.name,
      required this.status,
      required this.district,
      this.crop = 'Your crop'});

  @override
  Widget build(BuildContext context) {
    final triggered = status.contains('TRIGGER');
    final offline = status.contains('OFFLINE');
    final color = offline
        ? AppColors.blue
        : triggered
            ? AppColors.red
            : AppColors.greenDark;
    final statusLabel = offline
        ? 'Saved protection available'
        : triggered
            ? 'Trigger condition met'
            : 'Protected and monitoring';
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
          color: color,
          borderRadius: BorderRadius.circular(24),
          boxShadow: [
            BoxShadow(
                color: color.withValues(alpha: .18),
                blurRadius: 18,
                offset: const Offset(0, 10))
          ]),
      child: Stack(children: [
        Positioned(
            right: -12,
            bottom: -18,
            child: Icon(Icons.grass_rounded,
                size: 116, color: Colors.white.withValues(alpha: .08))),
        Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Row(children: [
            Container(
                width: 48,
                height: 48,
                decoration: BoxDecoration(
                    color: Colors.white.withValues(alpha: .15),
                    shape: BoxShape.circle),
                child: const Icon(Icons.agriculture_rounded,
                    color: Colors.white, size: 27)),
            const SizedBox(width: 12),
            Expanded(
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                  Text('YOUR FARM PROTECTION',
                      style: TextStyle(
                          color: Colors.white70,
                          fontSize: 11,
                          fontWeight: FontWeight.w900,
                          letterSpacing: 1)),
                  Text(name,
                      style: const TextStyle(
                          color: Colors.white,
                          fontSize: 20,
                          fontWeight: FontWeight.w900)),
                  Text(district,
                      style:
                          const TextStyle(color: Colors.white70, fontSize: 13))
                ]))
          ]),
          const SizedBox(height: 20),
          Row(children: [
            const Icon(Icons.grass_rounded, color: AppColors.gold, size: 20),
            const SizedBox(width: 7),
            Text(crop,
                style: const TextStyle(
                    color: Colors.white,
                    fontSize: 16,
                    fontWeight: FontWeight.w800))
          ]),
          const SizedBox(height: 10),
          Row(children: [
            Icon(
                offline
                    ? Icons.cloud_off
                    : triggered
                        ? Icons.warning_amber_rounded
                        : Icons.verified_rounded,
                color: Colors.white,
                size: 23),
            const SizedBox(width: 8),
            Text(statusLabel,
                style: const TextStyle(
                    color: Colors.white,
                    fontSize: 21,
                    fontWeight: FontWeight.w900))
          ]),
          const SizedBox(height: 5),
          Text(
              offline
                  ? 'Your saved farm information is still available.'
                  : triggered
                      ? 'Rainfall has crossed the protection condition.'
                      : 'Your policy is being monitored automatically.',
              style: const TextStyle(color: Colors.white70, fontSize: 13)),
        ]),
      ]),
    );
  }
}
