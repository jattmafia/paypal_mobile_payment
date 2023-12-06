import 'dart:async';

import 'package:flutter/services.dart';

class PaypalMobilePayment {
  static const MethodChannel _channel =
      const MethodChannel('paypal_mobile_payment');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> startCheckout({required String clientId, required Map<String, String> items}) async {
    String result = await _channel.invokeMethod(
      "startCheckout",
      {
        "clientId": clientId,
        "items": items
      },

    );
    return result;
  }
}
