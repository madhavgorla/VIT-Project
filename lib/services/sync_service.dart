import 'dart:convert';
import '../data/local/dao/sync_dao.dart';
import '../data/remote/api_service.dart';

class SyncService {
  final SyncDao dao;
  final ApiService api;
  SyncService(this.dao, this.api);
  Future<int> pendingCount() => dao.pendingCount();
  Future<void> process() async {
    final items = await dao.pending();
    if (items.isEmpty) return;
    try {
      await api.sync(items
          .map((e) => jsonDecode(e.payload) as Map<String, dynamic>)
          .toList());
      for (final i in items) {
        await dao.markSynced(i.id);
      }
    } catch (e) {
      for (final i in items) {
        await dao.markFailed(i.id, e.toString(), i.retryCount + 1);
      }
      rethrow;
    }
  }
}
