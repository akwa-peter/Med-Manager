package com.example.rad5.med_manager.Help_Classes;

import android.content.Context;

/**
 * Created by akwa on 4/16/18.
 */

public class ReminderTask {

    public static final String ACTION_INCREMENT_MEDICATION_COUNT = "increment-medication-count";

    public static void executeTask(Context context, String action, String KEY_MEDICATION_NAME) {
        if (ACTION_INCREMENT_MEDICATION_COUNT.equals(action)) {
            //increment medication count
            PreferenceUtils.incrementMedicationCount(context, KEY_MEDICATION_NAME);
        }
    }
}
