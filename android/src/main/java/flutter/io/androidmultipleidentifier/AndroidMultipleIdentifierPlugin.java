package flutter.io.androidmultipleidentifier;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import static android.content.ContentValues.TAG;

/** AndroidMultipleIdentifierPlugin */
public class AndroidMultipleIdentifierPlugin implements MethodCallHandler {

  private final Activity activity;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "android_multiple_identifier");
    channel.setMethodCallHandler(new AndroidMultipleIdentifierPlugin(registrar.activity()));
//    registrar.addRequestPermissionsResultListener();
  }

//  @Override
//  public boolean onRequestPermissionsResult(int i, String[] strings, int[] ints) {
//    return false;
//  }


  private AndroidMultipleIdentifierPlugin(Activity activity) {
    this.activity = activity;
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      Log.i(TAG, "AndroidMultipleIdentifierPlugin: ATTEMPTING TO  REQUEST PERMISSIONS");
      activity.shouldShowRequestPermissionRationale(
              Manifest.permission.READ_CONTACTS);
    }


  }
//  public AndroidMultipleIdentifierPlugin(MethodChannel channel, Activity context, TextureRegistry textures) {
//    this.textures = textures;
//    this.channel = channel;
//    this.context = context;
//  }

  String getIMEI (Context c) {
    TelephonyManager telephonyManager;
    telephonyManager = (TelephonyManager) c.getSystemService(c.TELEPHONY_SERVICE);
    /*
     * getDeviceId() returns the unique device ID.
     * For example,the IMEI for GSM and the MEID or ESN for CDMA phones.
     */

    String deviceId = "";
    if(telephonyManager.getDeviceId() == null) {
      deviceId = "returned null";
    }
    else {
      deviceId = telephonyManager.getDeviceId();
    }
    return deviceId;
  }

  String getSerial () {
    String serial = "";

    if(Build.SERIAL == null) {
      serial = "returned null";
    }
    else {
      serial = Build.SERIAL;
    }
    return serial;
  }

  String getAndroidID (Context c) {

    String androidId = "";
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
      androidId = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
      if(androidId == null) {
        androidId = "returned null";
      }
    }
    return androidId;

  }


  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
      return;
    }
    if (call.method.equals("getIMEI")) {
       String imei = getIMEI(activity.getBaseContext());

      result.success(imei);
      return;
    }

    if (call.method.equals("getSerial")) {
      String serial = getSerial();

      result.success(serial);
      return;
    }
    if (call.method.equals("getAndroidID")) {
      String androidID = getAndroidID(activity.getBaseContext());

      result.success(androidID);
      return;
    }

    result.notImplemented();

  }


//  public boolean onRequestPermission () {
//    return
//  }
}
