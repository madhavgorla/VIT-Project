import 'package:sqflite/sqflite.dart';
import '../../../models/notification_model.dart';
import '../database_helper.dart';

class NotificationDao {
  Future<void> upsert(AppNotification n) async {
    final db = await DatabaseHelper.instance.database;
    await db.insert('notifications', n.toMap(),
        conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<List<AppNotification>> all() async {
    final db = await DatabaseHelper.instance.database;
    final r = await db.query('notifications', orderBy: 'timestamp DESC');
    return r.map(AppNotification.fromMap).toList();
  }
}
