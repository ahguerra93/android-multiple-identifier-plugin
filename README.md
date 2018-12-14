# android_multiple_identifier

Exclusive for Android
A plugin that provides multiple Android unique identifiers for you to choose the one that best applies to your needs. 
This plugin is aimed for projects that need to a unique identifier for Android devices and need multiple options to test in their applications:

 - `ANDROID_ID` usually works but it is know to not be 100% reliable.
 - `IMEI` code is unique for each device but only comes with devices that have telephony services. It is also not recommended for other than fraud prevention.
 - `Serial` Number is also unique for each device but it is only required for devices that do not have telephony services, so it is possible that some devices do not have a serial number (possible but very unlikely).

All there 3 options are provided so you can use the one that best applies to your needs (or maybe multiple options combined). 

Permission `READ_PHONE_STATE` are required but the plugin has a method that takes care of that. 

## Getting Started

Make sure you add the needed permission to your Android Manifest  [Permission](https://developer.android.com/reference/android/Manifest.permission.html)
and Info.plist.

```xml
<uses-permission android:name="android.permission.READ_PHONE_STATE" />

```
## API
### Android

Be sure to ask permissions first:
```dart
await AndroidMultipleIdentifier.requestPermissions();
```
Then you can call any of the three available methods:
```dart
String imei = await AndroidMultipleIdentifier.imeiCode;
String serial = await AndroidMultipleIdentifier.serialCode;
String androidID = await AndroidMultipleIdentifier.androidID;
```

### iOS

If you call the `AndroidMultipleIdentifier.serialCode` method, it will return the iOS `identifierForVendor`. The rest will return a String with the value `'Unauthorized for Android'`