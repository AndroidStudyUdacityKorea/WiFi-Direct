/*
 * (c) COPYRIGHT 2009-2011 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPRIETARY
 * MOTOROLA Advanced Technology and Software Operations
 *
 * REVISION HISTORY:
 * Author        Date       CR Number         Brief Description
 * ------------- ---------- ----------------- ------------------------------
 * e51141        2010/08/27 IKCTXTAW-19		   Initial version
 */

package com.udacity.hackathon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.udacity.hackathon.Config;
import com.udacity.hackathon.ui.WiFiDirectApplication;

/**
 * <code><pre>
 * CLASS:
 *  To handle App setting and preference
 * <p/>
 * RESPONSIBILITIES:
 * <p/>
 * COLABORATORS:
 * <p/>
 * USAGE:
 * 	See each method.
 * <p/>
 * </pre></code>
 */
public class PrefUtils {

    private static final String TAG = "PTP_Pref";

    public static final String PREF_NAME = Config.PACKAGE_NAME;

    public static final String P2P_ENABLED = "p2pEnabled";

    private WiFiDirectApplication mApp;
    private SharedPreferences mPref;

    public PrefUtils(WiFiDirectApplication app) {
        mApp = app;
        mPref = mApp.getSharedPreferences(Config.PACKAGE_NAME, 0);
    }

    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    public static void setString(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }


    /**
     * Get the value of a key
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return mPref.getString(key, null);
    }

    /**
     * Set the value of a key
     *
     * @param key
     * @return
     */
    public void setString(String key, String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * Get the value of a key from a different preference in the same application
     * adb shell cat /data/data/com.motorola.contextual.smartrules/shared_prefs/com.motorola.contextual.virtualsensor.locationsensor.xml
     * <p/>
     * <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
     * <map>
     * <string name="poi">Location:1331910178767</string>
     * <string name="background_scan">1</string>
     *
     * @param preferenceFileName, the file name under shared_prefs dir
     * @param key                 the key
     *                            </map>
     */
    public static String getStringFromPref(Context ctx, String preferenceFileName, String key) {
        String value = null;
        SharedPreferences pref = ctx.getSharedPreferences(preferenceFileName, 0);
        if (pref != null) {
            value = pref.getString(key, null);
        }
        return value;
    }

    public static void setStringToPref(Context ctx, String preferenceFileName, String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(preferenceFileName, 0);
        if (pref != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    /**
     * Get the value of a key from a different preference in the same application
     * adb shell cat /data/data/com.motorola.contextual.smartrules/shared_prefs/com.motorola.contextual.virtualsensor.locationsensor.xml
     * <p/>
     * <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
     * <map>
     * <string name="poi">Location:1331910178767</string>
     * <string name="background_scan">1</string>
     * </map>
     *
     * @param preferenceFileName, the file name under shared_prefs dir
     * @param key                 the key
     */
    public static boolean getBooleanFromPref(Context ctx, String preferenceFileName, String key) {
        boolean value = false;
        SharedPreferences pref = ctx.getSharedPreferences(preferenceFileName, 0);
        if (pref != null) {
            value = pref.getBoolean(key, false);
        }
        return value;
    }
}
