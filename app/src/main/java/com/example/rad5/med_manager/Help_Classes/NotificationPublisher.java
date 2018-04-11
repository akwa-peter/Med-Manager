package com.example.rad5.med_manager.Help_Classes;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import com.example.rad5.med_manager.MainActivity;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by akwa on 4/10/18.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        assert notificationManager != null;
        notificationManager.notify(id, notification);

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(2000);

        // this will update the UI with message

        try {

            // this will sound the alarm tone
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

            // this will send a notification message
            ComponentName comp = new ComponentName(context.getPackageName(), AlarmService.class.getName());
            intent.setComponent(comp);

            // If extended by BroadcastReceiver class then comment this code
//            startWakefulService(context, (intent.setComponent(comp)));

            setResultCode(Activity.RESULT_OK);

        } catch (Exception ex) {

        }

    }

}
