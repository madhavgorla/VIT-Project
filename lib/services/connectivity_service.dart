import 'dart:async';
import 'package:connectivity_plus/connectivity_plus.dart';

class ConnectivityService {
  final Connectivity _connectivity = Connectivity();
  final _controller = StreamController<bool>.broadcast();
  Stream<bool> get stream => _controller.stream;
  bool isOnline = true;
  Future<void> start() async {
    isOnline = await _check();
    _controller.add(isOnline);
    _connectivity.onConnectivityChanged.listen((r) async {
      isOnline = await _hasNetwork(r);
      _controller.add(isOnline);
    });
  }

  Future<bool> _check() async {
    final r = await _connectivity.checkConnectivity();
    return _hasNetwork(r);
  }

  Future<bool> _hasNetwork(List<ConnectivityResult> r) async =>
      r.any((x) => x != ConnectivityResult.none);
  void dispose() => _controller.close();
}
