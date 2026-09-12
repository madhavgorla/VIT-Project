class Policy {
  final String id, policyId, farmerId, crop, district, status;
  final double threshold, coverage, premium;
  final DateTime startDate, endDate, lastUpdated;
  final String syncStatus;
  Policy(
      {required this.id,
      required this.policyId,
      required this.farmerId,
      required this.crop,
      required this.district,
      required this.threshold,
      required this.coverage,
      required this.premium,
      required this.startDate,
      required this.endDate,
      required this.status,
      this.syncStatus = 'SYNCED',
      DateTime? lastUpdated})
      : lastUpdated = lastUpdated ?? DateTime.now();
  Map<String, dynamic> toMap() => {
        'id': id,
        'policy_id': policyId,
        'farmer_id': farmerId,
        'crop': crop,
        'district': district,
        'threshold': threshold,
        'coverage': coverage,
        'premium': premium,
        'start_date': startDate.toIso8601String(),
        'end_date': endDate.toIso8601String(),
        'status': status,
        'sync_status': syncStatus,
        'last_updated': lastUpdated.toIso8601String()
      };
  factory Policy.fromMap(Map<String, dynamic> m) => Policy(
      id: m['id'],
      policyId: m['policy_id'],
      farmerId: m['farmer_id'],
      crop: m['crop'],
      district: m['district'],
      threshold: (m['threshold'] as num).toDouble(),
      coverage: (m['coverage'] as num).toDouble(),
      premium: (m['premium'] as num).toDouble(),
      startDate: DateTime.parse(m['start_date']),
      endDate: DateTime.parse(m['end_date']),
      status: m['status'],
      syncStatus: m['sync_status'] ?? 'SYNCED',
      lastUpdated:
          DateTime.tryParse(m['last_updated'] ?? '') ?? DateTime.now());
  Map<String, dynamic> toJson() => toMap();
}
