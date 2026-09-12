import 'dart:convert';
import 'package:flutter/foundation.dart';
import '../data/local/dao/farmer_dao.dart';
import '../data/local/dao/policy_dao.dart';
import '../data/local/dao/rainfall_dao.dart';
import '../data/local/dao/payout_dao.dart';
import '../data/local/dao/notification_dao.dart';
import '../data/local/dao/sync_dao.dart';
import '../data/remote/api_service.dart';
import '../models/farmer_model.dart';
import '../models/policy_model.dart';
import '../models/rainfall_model.dart';
import '../models/payout_model.dart';
import '../models/notification_model.dart';
import '../models/sync_item_model.dart';
import '../services/demo_service.dart';
import '../services/tts_service.dart';
import '../services/sync_service.dart';
import '../services/connectivity_service.dart';

class AppProvider extends ChangeNotifier {
  final farmerDao = FarmerDao();
  final policyDao = PolicyDao();
  final rainfallDao = RainfallDao();
  final payoutDao = PayoutDao();
  final notificationDao = NotificationDao();
  final syncDao = SyncDao();
  final api = ApiService();
  final tts = TtsService();
  final demo = DemoService();
  late final syncService = SyncService(syncDao, api);
  final connectivity = ConnectivityService();
  Farmer? farmer;
  Policy? policy;
  Rainfall? rainfall;
  Payout? payout;
  List<AppNotification> notifications = [];
  bool loading = true, syncing = false, online = true, initialized = false;
  String demoMode = 'NORMAL';
  int pending = 0;
  String selectedLanguage = 'te-IN';

  Future<void> initialize() async {
    if (initialized) return;
    initialized = true;
    loading = true;
    notifyListeners();
    await tts.init();
    demoMode = await demo.getMode();
    await connectivity.start();
    online = connectivity.isOnline;
    connectivity.stream.listen((v) async {
      online = v;
      notifyListeners();
      if (v) await syncPending(silent: true);
    });
    farmer = await farmerDao.getById('demo-farmer-001');
    if (farmer == null) await _seedDemo();
    await _loadLocal();
    if (online && demoMode == 'NORMAL') {
      await refreshFromBackend(silent: true);
    }
    loading = false;
    notifyListeners();
  }

  Future<void> _seedDemo() async {
    farmer = Farmer(
        id: 'demo-farmer-001',
        name: 'Ravi Kumar',
        phone: '9876543210',
        district: 'Anantapur',
        village: 'Kothapalli',
        language: 'te');
    policy = Policy(
        id: 'local-pol-001',
        policyId: 'POL-1001',
        farmerId: farmer!.id,
        crop: 'Groundnut',
        district: farmer!.district,
        threshold: 45,
        coverage: 10000,
        premium: 250,
        startDate: DateTime(2026, 6, 1),
        endDate: DateTime(2026, 11, 30),
        status: 'ACTIVE');
    rainfall = Rainfall(
        id: 'rain-001',
        district: farmer!.district,
        rainfall: 51,
        threshold: 45,
        source: 'DEMO / IMD',
        triggerMet: false);
    payout = Payout(
        id: 'pay-001',
        policyId: policy!.policyId,
        farmerId: farmer!.id,
        amount: 10000,
        status: 'MONITORING',
        auditRef: 'AUD-DEMO-001');
    await farmerDao.upsert(farmer!);
    await policyDao.upsert(policy!);
    await rainfallDao.upsert(rainfall!);
    await payoutDao.upsert(payout!);
    await notificationDao.upsert(AppNotification(
        id: 'n1',
        title: 'Policy Active',
        message: 'Your Groundnut policy is active.',
        type: 'POLICY'));
  }

  Future<void> _loadLocal() async {
    farmer ??= await farmerDao.getById('demo-farmer-001');
    if (farmer != null) {
      final ps = await policyDao.byFarmer(farmer!.id);
      if (ps.isNotEmpty) policy = ps.first;
      rainfall = await rainfallDao.latest(farmer!.district);
      if (policy != null) payout = await payoutDao.byPolicy(policy!.policyId);
      notifications = await notificationDao.all();
    }
    pending = await syncDao.pendingCount();
  }

  void setBaseUrl(String url) {
    api.baseUrl = url.trim();
    notifyListeners();
  }

  Future<void> setLanguage(String value) async {
    selectedLanguage = value;
    await tts.setLanguage(value);
    notifyListeners();
  }

  Future<void> setDemoMode(String mode) async {
    demoMode = mode;
    await demo.setMode(mode);
    await applyDemoMode();
    notifyListeners();
  }

  Future<void> applyDemoMode() async {
    if (policy == null || farmer == null) return;
    if (demoMode == 'NORMAL')
      rainfall = Rainfall(
          id: 'rain-demo-normal',
          district: farmer!.district,
          rainfall: 51,
          threshold: policy!.threshold,
          source: 'DEMO / IMD',
          triggerMet: false);
    if (demoMode == 'TRIGGER')
      rainfall = Rainfall(
          id: 'rain-demo-trigger',
          district: farmer!.district,
          rainfall: 32,
          threshold: policy!.threshold,
          source: 'DEMO / CHIRPS',
          triggerMet: true);
    if (demoMode == 'OFFLINE') {
      online = false;
    } else {
      online = connectivity.isOnline;
    }
    final triggered = rainfall!.triggerMet;
    payout = Payout(
        id: 'pay-demo',
        policyId: policy!.policyId,
        farmerId: farmer!.id,
        amount: policy!.coverage,
        status: triggered ? 'PAYOUT INITIATED' : 'MONITORING',
        auditRef: triggered ? 'AUD-DEMO-TRIGGER' : 'AUD-DEMO-NORMAL');
    await rainfallDao.upsert(rainfall!);
    await payoutDao.upsert(payout!);
    if (triggered)
      await notificationDao.upsert(AppNotification(
          id: 'trigger-${DateTime.now().millisecondsSinceEpoch}',
          title: 'Trigger Detected',
          message:
              'Rainfall is ${rainfall!.rainfall} mm against ${policy!.threshold} mm threshold.',
          type: 'TRIGGER'));
  }

  Future<void> refreshFromBackend({bool silent = false}) async {
    if (!online || farmer == null || demoMode != 'NORMAL') return;
    try {
      final f = await api.getFarmer(farmer!.id);
      farmer = f;
      await farmerDao.upsert(f);
      final ps = await api.policies(farmer!.id);
      if (ps.isNotEmpty) {
        policy = ps.first;
        await policyDao.upsert(policy!);
      }
      if (policy != null) {
        final r = await api.rainfall(policy!.district);
        rainfall = r;
        await rainfallDao.upsert(r);
        final p = await api.payout(policy!.policyId);
        payout = p;
        await payoutDao.upsert(p);
      }
      pending = await syncDao.pendingCount();
      if (!silent) notifyListeners();
    } catch (_) {
      if (!silent) notifyListeners();
    }
  }

  Future<void> registerFarmer(
      {required String name,
      required String phone,
      required String district,
      required String village,
      required String language}) async {
    final f = Farmer(
        id: 'farmer-${DateTime.now().millisecondsSinceEpoch}',
        name: name,
        phone: phone,
        district: district,
        village: village,
        language: language,
        syncStatus: online ? 'SYNCED' : 'PENDING');
    farmer = f;
    await farmerDao.upsert(f);
    if (online) {
      try {
        farmer = await api.createFarmer(f);
        await farmerDao.upsert(f);
      } catch (_) {
        await _queue('farmer', f.id, 'CREATE', f.toJson());
      }
    } else {
      await _queue('farmer', f.id, 'CREATE', f.toJson());
    }
    notifyListeners();
  }

  Future<void> _queue(
      String type, String id, String op, Map<String, dynamic> payload) async {
    await syncDao.add(SyncItem(
        id: 'sync-${DateTime.now().microsecondsSinceEpoch}',
        entityType: type,
        entityId: id,
        operation: op,
        payload: jsonEncode(payload)));
    pending = await syncDao.pendingCount();
  }

  Future<void> syncPending({bool silent = false}) async {
    if (!online) return;
    syncing = true;
    if (!silent) notifyListeners();
    try {
      await syncService.process();
    } catch (_) {}
    pending = await syncDao.pendingCount();
    syncing = false;
    notifyListeners();
  }

  String get protectionStatus => !online
      ? 'OFFLINE — LAST STATUS'
      : rainfall?.triggerMet == true
          ? 'TRIGGER DETECTED'
          : payout?.status == 'PAYOUT INITIATED'
              ? 'PAYOUT PROCESSING'
              : 'MONITORING';
  Future<void> speakSummary() async {
    final f = farmer, p = policy, r = rainfall;
    if (f == null || p == null || r == null) return;
    final msg = selectedLanguage.startsWith('te')
        ? 'నమస్తే ${f.name}. మీ పొలంలో వర్షపాతం ${r.rainfall} మిల్లీమీటర్లు. పరిమితి ${p.threshold} మిల్లీమీటర్లు. స్థితి $protectionStatus.'
        : selectedLanguage.startsWith('hi')
            ? 'नमस्ते ${f.name}. वर्तमान वर्षा ${r.rainfall} मिलीमीटर है। सीमा ${p.threshold} मिलीमीटर है। स्थिति $protectionStatus.'
            : 'Hello ${f.name}. Current rainfall is ${r.rainfall} millimeters against a threshold of ${p.threshold} millimeters. Status is $protectionStatus.';
    await tts.setLanguage(selectedLanguage);
    await tts.speak(msg);
  }

  Future<void> speakPayout() async {
    if (payout == null) return;
    await tts.setLanguage(selectedLanguage);
    await tts.speak(
        'Payout status is ${payout!.status}. Amount is ${payout!.amount.toStringAsFixed(0)} rupees.');
  }
}
