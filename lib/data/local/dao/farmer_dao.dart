import 'package:sqflite/sqflite.dart';
import '../../../models/farmer_model.dart';
import '../database_helper.dart';

class FarmerDao {
  Future<void> upsert(Farmer f) async {
    final db = await DatabaseHelper.instance.database;
    await db.insert('farmers', f.toMap(),
        conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<Farmer?> getById(String id) async {
    final db = await DatabaseHelper.instance.database;
    final r =
        await db.query('farmers', where: 'id=?', whereArgs: [id], limit: 1);
    return r.isEmpty ? null : Farmer.fromMap(r.first);
  }
}
