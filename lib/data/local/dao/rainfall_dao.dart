import 'package:sqflite/sqflite.dart';
import '../../../models/rainfall_model.dart';
import '../database_helper.dart';

class RainfallDao {
  Future<void> upsert(Rainfall r) async {
    final db = await DatabaseHelper.instance.database;
    await db.insert('rainfall_cache', r.toMap(),
        conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<Rainfall?> latest(String district) async {
    final db = await DatabaseHelper.instance.database;
    final rows = await db.query('rainfall_cache',
        where: 'district=?',
        whereArgs: [district],
        orderBy: 'timestamp DESC',
        limit: 1);
    return rows.isEmpty ? null : Rainfall.fromMap(rows.first);
  }
}
