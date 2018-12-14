package io.flutter.plugins;

import io.flutter.plugin.common.PluginRegistry;
import com.ethras.simplepermissions.SimplePermissionsPlugin;
import flutter.io.androidmultipleidentifier.AndroidMultipleIdentifierPlugin;

/**
 * Generated file. Do not edit.
 */
public final class GeneratedPluginRegistrant {
  public static void registerWith(PluginRegistry registry) {
    if (alreadyRegisteredWith(registry)) {
      return;
    }
    SimplePermissionsPlugin.registerWith(registry.registrarFor("com.ethras.simplepermissions.SimplePermissionsPlugin"));
    AndroidMultipleIdentifierPlugin.registerWith(registry.registrarFor("flutter.io.androidmultipleidentifier.AndroidMultipleIdentifierPlugin"));
  }

  private static boolean alreadyRegisteredWith(PluginRegistry registry) {
    final String key = GeneratedPluginRegistrant.class.getCanonicalName();
    if (registry.hasPlugin(key)) {
      return true;
    }
    registry.registrarFor(key);
    return false;
  }
}
