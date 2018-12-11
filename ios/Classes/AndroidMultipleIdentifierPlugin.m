#import "AndroidMultipleIdentifierPlugin.h"

@implementation AndroidMultipleIdentifierPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"android_multiple_identifier"
            binaryMessenger:[registrar messenger]];
  AndroidMultipleIdentifierPlugin* instance = [[AndroidMultipleIdentifierPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {

   switch (call.method)

   {
       case @"getPlatformVersion":

            result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);

            break;

       case @"getIMEI":

            result([@"Not authorized for iOS"]);

            break;

       case @"getSerial":

            let device = UIDevice.current;

             result(device.identifierForVendor?.uuidString);

            break;

       case @"getAndroidID":

            result([@"Not available for iOS"]);

            break;

       default:

            result(FlutterMethodNotImplemented);

            break;

   }
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  }


  else {
    if ([@"getIMEI" isEqualToString:call.method]) {
        result([@"Not authorized for iOS"]);
      }

    result(FlutterMethodNotImplemented);
  }
}

@end
