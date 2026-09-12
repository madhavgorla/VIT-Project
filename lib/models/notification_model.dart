class AppNotification {
  final String id, title, message, type;
  final DateTime timestamp;
  final bool isRead;
  AppNotification(
      {required this.id,
      required this.title,
      required this.message,
      required this.type,
      DateTime? timestamp,
      this.isRead = false})
      : timestamp = timestamp ?? DateTime.now();
  Map<String, dynamic> toMap() => {
        'id': id,
        'title': title,
        'message': message,
        'type': type,
        'timestamp': timestamp.toIso8601String(),
        'is_read': isRead ? 1 : 0
      };
  factory AppNotification.fromMap(Map<String, dynamic> m) => AppNotification(
      id: m['id'],
      title: m['title'],
      message: m['message'],
      type: m['type'],
      timestamp: DateTime.tryParse(m['timestamp'] ?? '') ?? DateTime.now(),
      isRead: m['is_read'] == 1);
}
