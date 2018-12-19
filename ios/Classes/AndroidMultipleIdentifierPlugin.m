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
     result(FlutterMethodNotImplemented);
}

@end
