#import "PaypalMobilePaymentPlugin.h"
#if __has_include(<paypal_mobile_payment/paypal_mobile_payment-Swift.h>)
#import <paypal_mobile_payment/paypal_mobile_payment-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "paypal_mobile_payment-Swift.h"
#endif

@implementation PaypalMobilePaymentPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPaypalMobilePaymentPlugin registerWithRegistrar:registrar];
}
@end
