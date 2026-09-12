class ApiConstants {
  static const String defaultBaseUrl = 'http://10.0.2.2:8080';
  static const Duration timeout = Duration(seconds: 12);

  static String farmers(String id) => '/api/farmers/$id';
  static const String createFarmer = '/api/farmers';
  static const String createPolicy = '/api/policies';
  static String farmerPolicies(String farmerId) =>
      '/api/policies/farmer/$farmerId';
  static String rainfall(String district) => '/api/rainfall/$district';
  static String payout(String policyId) => '/api/payout/$policyId';
  static String notifications(String farmerId) =>
      '/api/notifications/$farmerId';
  static const String sync = '/api/sync';
}
