import 'package:sqflite/sqflite.dart';
import '../../../models/policy_model.dart';
import '../database_helper.dart';

class PolicyDao {
  Future<void> upsert(Policy p) async {
    final db = await DatabaseHelper.instance.database;
    await db.insert('policies', p.toMap(),
        conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<List<Policy>> byFarmer(String id) async {
    final db = await DatabaseHelper.instance.database;
    final r = await db.query('policies',
        where: 'farmer_id=?', whereArgs: [id], orderBy: 'last_updated DESC');
    return r.map(Policy.fromMap).toList();
  }

  Future<Policy?> byId(String id) async {
    final db = await DatabaseHelper.instance.database;
    final r =
        await db.query('policies', where: 'id=?', whereArgs: [id], limit: 1);
    return r.isEmpty ? null : Policy.fromMap(r.first);
  }
}
