package com.mphstar.androidnative.belajar.pushnotif;


import android.app.Notification;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mphstar.androidnative.MainActivity;
import com.mphstar.androidnative.R;

public class NotificationHelper {
    public static void displayNotification(Context context, String title, String text, String channel){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channel)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_logo)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);
        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(1,mBuilder.build());
    }
}