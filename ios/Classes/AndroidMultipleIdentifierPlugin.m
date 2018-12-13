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

  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
     
  }
  else {
    if ([@"getIMEI" isEqualToString:call.method]) {
        result([@"Not authorized for iOS"]);
     }
      else {
           if ([@"getSerial" isEqualToString:call.method]) {
               let device = UIDevice.current;
               result(device.identifierForVendor?.uuidString);
          }
          else {
               if ([@"getAndroidID" isEqualToString:call.method]) {
                    result([@"Not compatible with iOS"]);
               }
               else {
                    result(FlutterMethodNotImplemented);
               }
          }
      }
  }
}

@end
