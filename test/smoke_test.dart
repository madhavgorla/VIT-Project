import 'package:flutter_test/flutter_test.dart';
import 'package:code_rabbits_farmer_app/main.dart';

void main() {
  testWidgets('app boots', (tester) async {
    await tester.pumpWidget(const CodeRabbitsApp());
    expect(find.text('Code Rabbits'), findsOneWidget);
  });
}
