package flutter.io.androidmultipleidentifier;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
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
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

import static android.content.ContentValues.TAG;

/** AndroidMultipleIdentifierPlugin */
    public class AndroidMultipleIdentifierPlugin implements MethodCallHandler, PluginRegistry.RequestPermissionsResultListener {

  private Registrar registrar;
  private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
  private Result result;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "android_multiple_identifier");
    AndroidMultipleIdentifierPlugin plugin = new AndroidMultipleIdentifierPlugin(registrar);
    channel.setMethodCallHandler(plugin);
    registrar.addRequestPermissionsResultListener(plugin);
  }

  private AndroidMultipleIdentifierPlugin(Registrar registrar){      //constructor 1
      this.registrar = registrar;
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

    private boolean checkPermission (Activity thisActivity) {
        boolean res = false;
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            res = true;
        }
        return res;
    }

    private boolean checkPermissionRationale (Activity thisActivity) {
        boolean res = false;
        if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                Manifest.permission.READ_PHONE_STATE)) {
            res = true;
        }
        return  res;
    }

    private void requestPermission (Activity thisActivity) {

        Log.i(TAG, "requestPermission: REQUESTING");
            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
    }

    private boolean isAPI26Up () {
      return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

  @Override
  public void onMethodCall(MethodCall call, Result res) {
    if (call.method.equals("getPlatformVersion")) {
      res.success("Android " + android.os.Build.VERSION.RELEASE);
      return;
    }
    if (call.method.equals("getIMEI")) {
       String imei = getIMEI(registrar.activity().getBaseContext());

      res.success(imei);
      return;
    }

    if (call.method.equals("getSerial")) {
      String serial = getSerial();

      res.success(serial);
      return;
    }
    if (call.method.equals("getAndroidID")) {
      String androidID = getAndroidID(registrar.activity().getBaseContext());

      res.success(androidID);
      return;
    }
    if (call.method.equals("getIdMap")) {
      Map idMap = getIdMap(registrar.activity().getBaseContext());
      res.success(idMap);
      return;
    }
    if (call.method.equals("checkPermission")) {

        boolean response = isAPI26Up()? checkPermission(registrar.activity()) : true;
        res.success(response);
        return;
    }
    if (call.method.equals("checkPermissionRationale")) {
        boolean response = isAPI26Up()? checkPermissionRationale(registrar.activity()) : false;
        res.success(response);
        return;
    }
    if (call.method.equals("requestPermission")) {
        this.result = res;
        if (isAPI26Up()) {
            requestPermission(registrar.activity());
        }
        else {
            res.success(true);
        }
        return;

    }

    res.notImplemented();

  }



    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> statusMap = new HashMap<>();
//        boolean status = false;
        statusMap.put("status", false);
        statusMap.put("neverAskAgain", false);
        String permission = permissions[0];
        Log.i(TAG, "requestResponse: INITIALIZED");
        if (requestCode == 0 && grantResults.length > 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(registrar.activity(), permission)) {
                Log.e("ResquestResponse", "DENIED: " + permission);//allowed//denied
                statusMap.put("status", false);
            } else {
                if (ActivityCompat.checkSelfPermission(registrar.context(), permission) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("ResquestResponse", "ALLOWED: " + permission);//allowed
                    statusMap.put("status", true);
                }
                else {
                    //set to never ask again
                    Log.e("ResquestResponse", "set to never ask again" + permission);
                    statusMap.put("neverAskAgain", true);
                }
            }
        }

        Result res = this.result;
        this.result = null;
        if(res != null) {
            Log.i(TAG, "onRequestPermissionsResult: Returning result");
            res.success(statusMap);
        }
        else
        {
            Log.i(TAG, "onRequestPermissionsResult: NOT Returning result");
        }
        return true;
    }

}
