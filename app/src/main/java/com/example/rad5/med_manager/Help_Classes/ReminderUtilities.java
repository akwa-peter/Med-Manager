package com.example.rad5.med_manager.Help_Classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by akwa on 4/16/18.
 */

public class ReminderUtilities {

    private static final int REMINDER_INTERVAL_MINUTES = 6;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEX_TIME = REMINDER_INTERVAL_SECONDS;

    private static final String JOB_REMINDER_TAG = "Notification_Tag";

    private static boolean isInitialized;

    public static void scheduleAlarm(Context context){
        if (isInitialized) return;
        com.firebase.jobdispatcher.Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job notifyJob = dispatcher.newJobBuilder()
                .setService(MedicationReminderFirebaseJobService.class)
                .setTag(JOB_REMINDER_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEX_TIME))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(notifyJob);
        isInitialized = true;

    }
}
