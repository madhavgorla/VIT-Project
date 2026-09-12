import 'package:sqflite/sqflite.dart';
import '../../../models/sync_item_model.dart';
import '../database_helper.dart';

class SyncDao {
  Future<void> add(SyncItem item) async {
    final db = await DatabaseHelper.instance.database;
    await db.insert('sync_queue', item.toMap(),
        conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<List<SyncItem>> pending() async {
    final db = await DatabaseHelper.instance.database;
    final r = await db.query('sync_queue',
        where: 'sync_status=?',
        whereArgs: ['PENDING'],
        orderBy: 'created_at ASC');
    return r.map(SyncItem.fromMap).toList();
  }

  Future<int> pendingCount() async {
    final db = await DatabaseHelper.instance.database;
    final r = await db.rawQuery(
        "SELECT COUNT(*) AS c FROM sync_queue WHERE sync_status='PENDING'");
    return Sqflite.firstIntValue(r) ?? 0;
  }

  Future<void> markSynced(String id) async {
    final db = await DatabaseHelper.instance.database;
    await db.update('sync_queue', {'sync_status': 'SYNCED'},
        where: 'id=?', whereArgs: [id]);
  }

  Future<void> markFailed(String id, String message, int retry) async {
    final db = await DatabaseHelper.instance.database;
    await db.update(
        'sync_queue',
        {
          'sync_status': 'PENDING',
          'error_message': message,
          'retry_count': retry
        },
        where: 'id=?',
        whereArgs: [id]);
  }
}
