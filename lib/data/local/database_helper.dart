import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._();
  DatabaseHelper._();
  Database? _db;

  Future<Database> get database async => _db ??= await _open();
  Future<Database> _open() async {
    final path = join(await getDatabasesPath(), 'code_rabbits_farmer.db');
    return openDatabase(path, version: 1, onCreate: (db, version) async {
      await db.execute(
          '''CREATE TABLE farmers (id TEXT PRIMARY KEY, name TEXT NOT NULL, phone TEXT NOT NULL, district TEXT NOT NULL, village TEXT NOT NULL, language TEXT NOT NULL DEFAULT 'te', sync_status TEXT NOT NULL DEFAULT 'SYNCED', last_updated TEXT NOT NULL)''');
      await db.execute(
          '''CREATE TABLE policies (id TEXT PRIMARY KEY, policy_id TEXT NOT NULL UNIQUE, farmer_id TEXT NOT NULL, crop TEXT NOT NULL, district TEXT NOT NULL, threshold REAL NOT NULL, coverage REAL NOT NULL, premium REAL NOT NULL, start_date TEXT NOT NULL, end_date TEXT NOT NULL, status TEXT NOT NULL, sync_status TEXT NOT NULL DEFAULT 'SYNCED', last_updated TEXT NOT NULL)''');
      await db.execute(
          '''CREATE TABLE rainfall_cache (id TEXT PRIMARY KEY, district TEXT NOT NULL, rainfall REAL NOT NULL, threshold REAL NOT NULL, source TEXT NOT NULL, timestamp TEXT NOT NULL, trigger_met INTEGER NOT NULL DEFAULT 0)''');
      await db.execute(
          '''CREATE TABLE payout_cache (id TEXT PRIMARY KEY, policy_id TEXT NOT NULL, farmer_id TEXT NOT NULL, amount REAL NOT NULL, status TEXT NOT NULL, trigger_date TEXT NOT NULL, payout_date TEXT, audit_ref TEXT, last_updated TEXT NOT NULL)''');
      await db.execute(
          '''CREATE TABLE notifications (id TEXT PRIMARY KEY, title TEXT NOT NULL, message TEXT NOT NULL, type TEXT NOT NULL, timestamp TEXT NOT NULL, is_read INTEGER NOT NULL DEFAULT 0)''');
      await db.execute(
          '''CREATE TABLE sync_queue (id TEXT PRIMARY KEY, entity_type TEXT NOT NULL, entity_id TEXT NOT NULL, operation TEXT NOT NULL, payload TEXT NOT NULL, created_at TEXT NOT NULL, retry_count INTEGER NOT NULL DEFAULT 0, sync_status TEXT NOT NULL DEFAULT 'PENDING', error_message TEXT)''');
      await db.execute('CREATE INDEX idx_policy_farmer ON policies(farmer_id)');
      await db.execute(
          'CREATE INDEX idx_rainfall_district ON rainfall_cache(district)');
      await db
          .execute('CREATE INDEX idx_payout_policy ON payout_cache(policy_id)');
      await db
          .execute('CREATE INDEX idx_sync_status ON sync_queue(sync_status)');
    });
  }
}
