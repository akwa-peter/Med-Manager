package com.example.rad5.med_manager.Help_Classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by akwa on 4/18/18.
 */

public class AlarmScheduler {

    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    public static void scheduleAlarm(Context context){

        alarmMgr = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("action", "android.intent.action.BOOT_COMPLETED");
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        //if the set time is less than the current time, add a day
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        // setRepeating() lets you specify a precise custom interval--in this case,
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

    }
}
