import 'package:intl/intl.dart';

String money(double value) =>
    NumberFormat.currency(locale: 'en_IN', symbol: '₹', decimalDigits: 0)
        .format(value);
String dateTimeText(DateTime value) =>
    DateFormat('dd MMM yyyy, hh:mm a').format(value);
String mm(double value) =>
    '${value.toStringAsFixed(value % 1 == 0 ? 0 : 1)} mm';
