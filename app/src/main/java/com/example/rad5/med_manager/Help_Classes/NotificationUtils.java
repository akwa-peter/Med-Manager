package com.example.rad5.med_manager.Help_Classes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.rad5.med_manager.MainActivity;
import com.example.rad5.med_manager.R;

/**
 * Created by akwa on 4/15/18.
 */

public class NotificationUtils {

    private static final int MEDICATION_PENDING_INTENT_ID = 1234;

    private static final int MEDICATION_REMINDER_ID = 5678;
    private static final String MEDICATION_REMINDER_NOTIFICATION_CHANNEL_ID = "notification_channel_reminder";

    public static void remindUserForMedication(Context context){
        //get refernce to the notification manager using the Notification.getSystemService
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Create a notification channel for the android device
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    MEDICATION_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.action_bar_intro1),
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        //Now build the Notification using the notification builder
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, MEDICATION_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorWhite))
                .setSmallIcon(R.drawable.health_care_logo)
                .setLargeIcon(notificationIcon(context))
                .setContentTitle("Medication Reminder")
                .setContentText("Its time for your medication")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Its time for your medication"))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent(context))
                .setAutoCancel(true);

        //also set the notification priority to high if the build version
        //is greater than jelly bean and less than oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        notificationManager.notify(MEDICATION_REMINDER_ID, notificationBuilder.build());
    }

    //Create a pending intent method to launch the application when the notification is clicked
    public static PendingIntent pendingIntent(Context context){
        //create an intent to open the main activity
        Intent startActivity = new Intent(context, MainActivity.class);

        //return pending intent
        return PendingIntent.getActivity(
                context,
                MEDICATION_PENDING_INTENT_ID,
                startActivity,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static Bitmap notificationIcon(Context context){
        //get a resource object from the context
        Resources res = context.getResources();

        //create and return a bitmap using a BitmapFactory.decodeResource
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.health_care_logo);
        return bitmap;

    }
}
