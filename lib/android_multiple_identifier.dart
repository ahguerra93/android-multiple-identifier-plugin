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

  static Future<Map> get idMap async {

      final Map idMap = await _channel.invokeMethod('getIdMap');
      return idMap;

  }

  static Future<bool> requestPermission() async {
    final bool result = await _channel.invokeMethod('requestPermission');
    return result;
  }
  static Future<bool> checkPermission() async {
    final bool result = await _channel.invokeMethod('checkPermission');
    print("Checking permission: $result");
    return result;
  }
  static Future<bool> checkPermissionRationale() async {
    final bool result = await _channel.invokeMethod('checkPermissionRationale');
    print("Did the user rejected the permission?: $result");
    return result;
  }
}
