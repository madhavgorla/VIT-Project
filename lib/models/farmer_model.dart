class Farmer {
  final String id;
  final String name;
  final String phone;
  final String district;
  final String village;
  final String language;
  final String syncStatus;
  final DateTime lastUpdated;

  Farmer(
      {required this.id,
      required this.name,
      required this.phone,
      required this.district,
      required this.village,
      required this.language,
      this.syncStatus = 'SYNCED',
      DateTime? lastUpdated})
      : lastUpdated = lastUpdated ?? DateTime.now();

  Map<String, dynamic> toMap() => {
        'id': id,
        'name': name,
        'phone': phone,
        'district': district,
        'village': village,
        'language': language,
        'sync_status': syncStatus,
        'last_updated': lastUpdated.toIso8601String()
      };
  factory Farmer.fromMap(Map<String, dynamic> m) => Farmer(
      id: m['id'],
      name: m['name'],
      phone: m['phone'],
      district: m['district'],
      village: m['village'],
      language: m['language'],
      syncStatus: m['sync_status'] ?? 'SYNCED',
      lastUpdated:
          DateTime.tryParse(m['last_updated'] ?? '') ?? DateTime.now());
  Map<String, dynamic> toJson() => toMap();
}
