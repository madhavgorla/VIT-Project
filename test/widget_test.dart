import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:code_rabbits_farmer_app/main.dart';

void main() {
  testWidgets('application root mounts', (tester) async {
    await tester.pumpWidget(const CodeRabbitsApp());
    expect(find.byType(MaterialApp), findsOneWidget);
  });
}
