package com.example.rad5.med_manager.Help_Classes;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by akwa on 4/16/18.
 */

public class MedicationReminderIntentService extends IntentService {
    //  create a default constructor for this class
    public MedicationReminderIntentService() {
        super("MedicationReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Get the action from the Intent that started this Service
        String action = intent.getAction();
        String KEY_MEDICATION_NAME = intent.getStringExtra("intent");

        //Call ReminderTasks.executeTask and pass in the action to be performed
        ReminderTask.executeTask(this, action, KEY_MEDICATION_NAME);
    }
}
