import 'dart:async';

import 'package:flutter/services.dart';

class AndroidMultipleIdentifier {
  static const MethodChannel _channel =
      const MethodChannel('android_multiple_identifier');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  static Future<String> get imeiCode async {
    final String imei = await _channel.invokeMethod('getIMEI');
    return imei;
  }
  static Future<String> get serialCode async {
    final String serial = await _channel.invokeMethod('getSerial');
    return serial;
  }
  static Future<String> get androidID async {
    final String androidID = await _channel.invokeMethod('getAndroidID');
    return androidID;
  }
}
