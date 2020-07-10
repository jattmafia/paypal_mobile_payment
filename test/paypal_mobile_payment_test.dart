import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:paypal_mobile_payment/paypal_mobile_payment.dart';

void main() {
  const MethodChannel channel = MethodChannel('paypal_mobile_payment');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await PaypalMobilePayment.platformVersion, '42');
  });
}
