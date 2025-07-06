package com.s23010920.studypal;



import android.app.NotificationChannel;

import android.app.NotificationManager;

import android.app.Notification;

import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent intent) {

        String subject = intent.getStringExtra("subject");

        String note = intent.getStringExtra("note");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "reminder_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId, "Reminders", NotificationManager.IMPORTANCE_HIGH);

            channel.enableVibration(true);

            channel.setVibrationPattern(new long[]{0, 500, 1000, 500});

            notificationManager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)

                .setSmallIcon(android.R.drawable.ic_dialog_info)

                .setContentTitle(subject)

                .setContentText(note)

                .setPriority(NotificationCompat.PRIORITY_HIGH)

                .setDefaults(Notification.DEFAULT_ALL) // enables sound & vibration

                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

    }

}

