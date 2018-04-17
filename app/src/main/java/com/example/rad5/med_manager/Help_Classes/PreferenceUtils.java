package com.example.rad5.med_manager.Help_Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by akwa on 4/16/18.
 */

public final class PreferenceUtils {
    public static final String KEY_MEDICATION_COUNT = "medication-count";

    private static final int DEFAULT_COUNT = 0;
    private static int MEDICATION_COUNT;

    synchronized private static void setMedicationCount(Context context, String KEY_MEDICATION_NAME, int medicationCount) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_MEDICATION_NAME, medicationCount);
        editor.apply();
        Log.d("debugger", "set_Medication_Count : "+ editor);
    }

    public static int getMedicationCount(Context context, String KEY_MEDICATION_NAME) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(KEY_MEDICATION_NAME, DEFAULT_COUNT);
    }

    synchronized public static void incrementMedicationCount(Context context, String KEY_MEDICATION_NAME) {
        int medicationCount = PreferenceUtils.getMedicationCount(context, KEY_MEDICATION_NAME);
        PreferenceUtils.setMedicationCount(context, KEY_MEDICATION_NAME, ++medicationCount);
        MEDICATION_COUNT = medicationCount;

        Log.d("debugger", "Now in Preferenceutils.increamentCount : "+ KEY_MEDICATION_NAME + medicationCount);
    }

}
