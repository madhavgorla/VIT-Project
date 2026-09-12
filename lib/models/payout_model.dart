class Payout {
  final String id, policyId, farmerId, status;
  final double amount;
  final DateTime triggerDate, lastUpdated;
  final DateTime? payoutDate;
  final String? auditRef;
  Payout(
      {required this.id,
      required this.policyId,
      required this.farmerId,
      required this.amount,
      required this.status,
      DateTime? triggerDate,
      this.payoutDate,
      this.auditRef,
      DateTime? lastUpdated})
      : triggerDate = triggerDate ?? DateTime.now(),
        lastUpdated = lastUpdated ?? DateTime.now();
  Map<String, dynamic> toMap() => {
        'id': id,
        'policy_id': policyId,
        'farmer_id': farmerId,
        'amount': amount,
        'status': status,
        'trigger_date': triggerDate.toIso8601String(),
        'payout_date': payoutDate?.toIso8601String(),
        'audit_ref': auditRef,
        'last_updated': lastUpdated.toIso8601String()
      };
  factory Payout.fromMap(Map<String, dynamic> m) => Payout(
      id: m['id'],
      policyId: m['policy_id'],
      farmerId: m['farmer_id'],
      amount: (m['amount'] as num).toDouble(),
      status: m['status'],
      triggerDate: DateTime.tryParse(m['trigger_date'] ?? '') ?? DateTime.now(),
      payoutDate:
          m['payout_date'] == null ? null : DateTime.tryParse(m['payout_date']),
      auditRef: m['audit_ref'],
      lastUpdated:
          DateTime.tryParse(m['last_updated'] ?? '') ?? DateTime.now());
  Map<String, dynamic> toJson() => toMap();
}
