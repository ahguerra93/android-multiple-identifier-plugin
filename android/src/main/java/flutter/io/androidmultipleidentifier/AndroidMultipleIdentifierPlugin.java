package flutter.io.androidmultipleidentifier;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import static android.content.ContentValues.TAG;

/** AndroidMultipleIdentifierPlugin */
public class AndroidMultipleIdentifierPlugin implements MethodCallHandler{

  private final Activity activity;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "android_multiple_identifier");
    channel.setMethodCallHandler(new AndroidMultipleIdentifierPlugin(registrar.activity()));

  }

  private AndroidMultipleIdentifierPlugin(Activity activity) {
    this.activity = activity;
  }


  private String getIMEI (Context c) {
    Log.i(TAG, "ATTEMPTING TO getIMEI: ");
    TelephonyManager telephonyManager;
    telephonyManager = (TelephonyManager) c.getSystemService(c.TELEPHONY_SERVICE);


    String deviceId = "";
    if(telephonyManager.getDeviceId() == null) {
      deviceId = "returned null";
    }
    else {
      deviceId = telephonyManager.getDeviceId();
    }
    return deviceId;
  }

  private String getSerial () {
    Log.i(TAG, "ATTEMPTING TO getSerial: ");
    String serial = "";

    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      serial = Build.getSerial();
      if(serial == null) {
        serial = "returned null";
      }
    }
    else {
      serial = Build.SERIAL;
      if(serial == null) {
        serial = "returned null";
      }
    }

    return serial;
  }

  private String getAndroidID (Context c) {
    Log.i(TAG, "ATTEMPTING TO getAndroidID: ");
    String androidId = "";
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
      androidId = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
      if(androidId == null) {
        androidId = "returned null";
      }
    }
    return androidId;
  }

  private Map<String, String> getIdMap (Context c)  {
    String imei = getIMEI(c);
    String serial = getSerial();
    String androidId = getAndroidID(c);
    Map<String, String> idMap = new HashMap<>();
    idMap.put("imei",imei);
    idMap.put("serial", serial);
    idMap.put("androidId", androidId);
    return idMap;
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
    if (call.method.equals("getIdMap")) {
      Map idMap = getIdMap(activity.getBaseContext());
      result.success(idMap);
      return;
    }

    result.notImplemented();

  }

}
