package com.eventboard.eventboardapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public class DataPreference {
    public static final String PREFERENCE_NAME = "Data_Prefs";

    public static void setPref(Context c, String pref, String val) {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        e.putString(pref, val);
        e.commit();
    }

    public static String getPref(Context c, String pref, String val) {
        return c.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(pref, val);
    }

    public static void setPref(Context c, String pref, Boolean val) {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        e.putBoolean(pref, val);
        e.commit();
    }

    public static boolean getPref(Context c, String pref, Boolean val) {
        return c.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(pref, val);
    }
}
