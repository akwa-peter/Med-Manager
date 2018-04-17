package com.example.rad5.med_manager.Help_Classes;

import android.content.Context;
import android.util.Log;

/**
 * Created by akwa on 4/16/18.
 */

public class ReminderTask {

    public static final String ACTION_INCREMENT_MEDICATION_COUNT = "increment-medication-count";
    public static final String ACTION_MAKE_AN_ALARM = "increment-medication-count";

    public static void executeTask(Context context, String action, String KEY_MEDICATION_NAME) {
        if (ACTION_INCREMENT_MEDICATION_COUNT.equals(action)) {
//            incrementMedicationCount(context);
            PreferenceUtils.incrementMedicationCount(context, KEY_MEDICATION_NAME);
        }
        else if (ACTION_MAKE_AN_ALARM.equals(action)){

        }
    }

    //create a method to increament the medication count
//    private static void incrementMedicationCount(Context context) {
//        PreferenceUtils.incrementMedicationCount(context);
//    }
}
