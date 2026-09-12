import 'package:flutter/material.dart';

class InfoCard extends StatelessWidget {
  final String title, value;
  final IconData icon;
  final Color color;
  final VoidCallback? onSpeak;
  const InfoCard(
      {super.key,
      required this.title,
      required this.value,
      required this.icon,
      this.color = Colors.green,
      this.onSpeak});

  @override
  Widget build(BuildContext context) => Card(
      child: Padding(
          padding: const EdgeInsets.all(15),
          child:
              Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
              Container(
                  width: 42,
                  height: 42,
                  decoration: BoxDecoration(
                      color: color.withValues(alpha: .12),
                      borderRadius: BorderRadius.circular(13)),
                  child: Icon(icon, color: color)),
              if (onSpeak != null)
                IconButton(
                    tooltip: 'Listen',
                    visualDensity: VisualDensity.compact,
                    onPressed: onSpeak,
                    icon: const Icon(Icons.volume_up_outlined, size: 19))
            ]),
            const SizedBox(height: 12),
            Text(title,
                style: const TextStyle(
                    color: Colors.black54,
                    fontSize: 12,
                    fontWeight: FontWeight.w700)),
            const SizedBox(height: 3),
            Text(value,
                style:
                    const TextStyle(fontSize: 17, fontWeight: FontWeight.w900))
          ])));
}
