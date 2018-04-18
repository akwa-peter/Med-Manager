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

        // Set the alarm to at specified time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 18);

        // setInexactRepeating() lets you specify a precise custom interval--in this case,
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}
