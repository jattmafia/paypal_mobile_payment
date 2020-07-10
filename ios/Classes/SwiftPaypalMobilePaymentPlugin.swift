import Flutter
import UIKit

public class SwiftPaypalMobilePaymentPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "paypal_mobile_payment", binaryMessenger: registrar.messenger())
    let instance = SwiftPaypalMobilePaymentPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
