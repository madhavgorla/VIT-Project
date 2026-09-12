import 'dart:convert';
import 'package:http/http.dart' as http;
import '../../core/constants/api_constants.dart';
import '../../models/farmer_model.dart';
import '../../models/policy_model.dart';
import '../../models/rainfall_model.dart';
import '../../models/payout_model.dart';

class ApiService {
  String baseUrl;
  ApiService({this.baseUrl = ApiConstants.defaultBaseUrl});
  Uri _uri(String path) =>
      Uri.parse('${baseUrl.replaceFirst(RegExp(r'/$'), '')}$path');
  Map<String, String> get _headers =>
      {'Content-Type': 'application/json', 'Accept': 'application/json'};

  Future<Farmer> createFarmer(Farmer f) async {
    final r = await http
        .post(_uri(ApiConstants.createFarmer),
            headers: _headers, body: jsonEncode(f.toJson()))
        .timeout(ApiConstants.timeout);
    _ok(r);
    return Farmer.fromMap(jsonDecode(r.body));
  }

  Future<Farmer> getFarmer(String id) async {
    final r = await http
        .get(_uri(ApiConstants.farmers(id)), headers: _headers)
        .timeout(ApiConstants.timeout);
    _ok(r);
    return Farmer.fromMap(jsonDecode(r.body));
  }

  Future<Policy> createPolicy(Policy p) async {
    final r = await http
        .post(_uri(ApiConstants.createPolicy),
            headers: _headers, body: jsonEncode(p.toJson()))
        .timeout(ApiConstants.timeout);
    _ok(r);
    return Policy.fromMap(jsonDecode(r.body));
  }

  Future<List<Policy>> policies(String farmerId) async {
    final r = await http
        .get(_uri(ApiConstants.farmerPolicies(farmerId)), headers: _headers)
        .timeout(ApiConstants.timeout);
    _ok(r);
    return (jsonDecode(r.body) as List).map((e) => Policy.fromMap(e)).toList();
  }

  Future<Rainfall> rainfall(String district) async {
    final r = await http
        .get(_uri(ApiConstants.rainfall(Uri.encodeComponent(district))),
            headers: _headers)
        .timeout(ApiConstants.timeout);
    _ok(r);
    return Rainfall.fromMap(jsonDecode(r.body));
  }

  Future<Payout> payout(String policyId) async {
    final r = await http
        .get(_uri(ApiConstants.payout(policyId)), headers: _headers)
        .timeout(ApiConstants.timeout);
    _ok(r);
    return Payout.fromMap(jsonDecode(r.body));
  }

  Future<void> sync(List<Map<String, dynamic>> items) async {
    final r = await http
        .post(_uri(ApiConstants.sync),
            headers: _headers, body: jsonEncode({'items': items}))
        .timeout(ApiConstants.timeout);
    _ok(r);
  }

  static void _ok(http.Response r) {
    if (r.statusCode < 200 || r.statusCode >= 300)
      throw Exception('Server returned ${r.statusCode}');
  }
}
