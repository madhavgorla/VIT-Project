import 'package:sqflite/sqflite.dart';
import '../../../models/payout_model.dart';
import '../database_helper.dart';

class PayoutDao {
  Future<void> upsert(Payout p) async {
    final db = await DatabaseHelper.instance.database;
    await db.insert('payout_cache', p.toMap(),
        conflictAlgorithm: ConflictAlgorithm.replace);
  }

  Future<Payout?> byPolicy(String policyId) async {
    final db = await DatabaseHelper.instance.database;
    final r = await db.query('payout_cache',
        where: 'policy_id=?',
        whereArgs: [policyId],
        orderBy: 'last_updated DESC',
        limit: 1);
    return r.isEmpty ? null : Payout.fromMap(r.first);
  }
}
