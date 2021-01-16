package com.example.scorelive.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.scorelive.R;

public class Notify {
    public static final String FAVORITE_CHANNEL_ID = "favorite";
    public static final String DEFAULT_CHANNEL_ID = "misc";

    private static Notify instance;
    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;

    private Notify(NotificationCompat.Builder builder) {
        this.builder = builder;
    }

    private Notify(NotificationCompat.Builder builder, NotificationManagerCompat notificationManager) {
        this.builder = builder;
        this.notificationManager = notificationManager;

        builder.setSmallIcon(R.drawable.ic_notification);
    }

    public static Notify getInstance() {
        return instance;
    }

    public static Notify getInstance(NotificationCompat.Builder builder) {
        if (instance == null) {
            instance = new Notify(builder);
        }

        return instance;
    }

    public static Notify getInstance(NotificationCompat.Builder builder, NotificationManagerCompat notificationManager) {
        if (instance == null) {
            instance = new Notify(builder, notificationManager);
        }

        return instance;
    }

    public  NotificationCompat.Builder getBuilder() { return this.builder; }

    public NotificationManagerCompat getNotificationManager() {
        return notificationManager;
    }

    public void setNotificationManager(NotificationManagerCompat notificationManager) {
        this.notificationManager = notificationManager;
    }

    public void createNotificationChannel(String channelId, String name, String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, name, description, NotificationManager.IMPORTANCE_DEFAULT);
        }
    }

    public void createNotificationChannel(String channelId, String name, String description, int importance) {
        if (notificationManager != null) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void now(String title, String text, int priority, Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder.setContentTitle(title)
                .setContentText(text)
                .setPriority(priority)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }

    public void now(String title, String text, Context context, Intent intent) {
        now(title, text, NotificationCompat.PRIORITY_DEFAULT, context, intent);
    }

    public void now(String title, String text, int priority) {
        builder.setContentTitle(title)
                .setContentText(text)
                .setPriority(priority);

        notificationManager.notify(0, builder.build());
    }

    public void now(String title, String text) {
        now(title, text, NotificationCompat.PRIORITY_DEFAULT);
    }
}
