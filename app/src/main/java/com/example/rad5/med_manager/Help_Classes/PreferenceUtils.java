package com.example.rad5.med_manager.Help_Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by akwa on 4/16/18.
 */

public final class PreferenceUtils {

    private static final int DEFAULT_COUNT = 0;

    synchronized private static void setMedicationCount(Context context, String KEY_MEDICATION_NAME, int medicationCount) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_MEDICATION_NAME, medicationCount);
        editor.apply();
    }

    public static int getMedicationCount(Context context, String KEY_MEDICATION_NAME) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(KEY_MEDICATION_NAME, DEFAULT_COUNT);
    }

    synchronized public static void incrementMedicationCount(Context context, String KEY_MEDICATION_NAME) {
        int medicationCount = PreferenceUtils.getMedicationCount(context, KEY_MEDICATION_NAME);
        PreferenceUtils.setMedicationCount(context, KEY_MEDICATION_NAME, ++medicationCount);
    }

}
