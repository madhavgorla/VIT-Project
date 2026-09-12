class SyncItem {
  final String id, entityType, entityId, operation, payload, syncStatus;
  final DateTime createdAt;
  final int retryCount;
  final String? errorMessage;
  SyncItem(
      {required this.id,
      required this.entityType,
      required this.entityId,
      required this.operation,
      required this.payload,
      DateTime? createdAt,
      this.retryCount = 0,
      this.syncStatus = 'PENDING',
      this.errorMessage})
      : createdAt = createdAt ?? DateTime.now();
  Map<String, dynamic> toMap() => {
        'id': id,
        'entity_type': entityType,
        'entity_id': entityId,
        'operation': operation,
        'payload': payload,
        'created_at': createdAt.toIso8601String(),
        'retry_count': retryCount,
        'sync_status': syncStatus,
        'error_message': errorMessage
      };
  factory SyncItem.fromMap(Map<String, dynamic> m) => SyncItem(
      id: m['id'],
      entityType: m['entity_type'],
      entityId: m['entity_id'],
      operation: m['operation'],
      payload: m['payload'],
      createdAt: DateTime.tryParse(m['created_at'] ?? '') ?? DateTime.now(),
      retryCount: m['retry_count'] ?? 0,
      syncStatus: m['sync_status'] ?? 'PENDING',
      errorMessage: m['error_message']);
}
