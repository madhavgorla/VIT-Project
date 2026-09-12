class Rainfall {
  final String id, district, source;
  final double rainfall, threshold;
  final DateTime timestamp;
  final bool triggerMet;
  Rainfall(
      {required this.id,
      required this.district,
      required this.rainfall,
      required this.threshold,
      required this.source,
      DateTime? timestamp,
      this.triggerMet = false})
      : timestamp = timestamp ?? DateTime.now();
  Map<String, dynamic> toMap() => {
        'id': id,
        'district': district,
        'rainfall': rainfall,
        'threshold': threshold,
        'source': source,
        'timestamp': timestamp.toIso8601String(),
        'trigger_met': triggerMet ? 1 : 0
      };
  factory Rainfall.fromMap(Map<String, dynamic> m) => Rainfall(
      id: m['id'],
      district: m['district'],
      rainfall: (m['rainfall'] as num).toDouble(),
      threshold: (m['threshold'] as num).toDouble(),
      source: m['source'],
      timestamp: DateTime.tryParse(m['timestamp'] ?? '') ?? DateTime.now(),
      triggerMet: m['trigger_met'] == 1 || m['trigger_met'] == true);
  Map<String, dynamic> toJson() => toMap();
}
