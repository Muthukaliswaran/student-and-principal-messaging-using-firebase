package com.prasanthkalis.gce_bodi.notification_service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.activity.Principal;
import com.prasanthkalis.gce_bodi.activity.Student;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static int count = 0, notificationId;
    String check;

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        count++;
        if (message.getData() != null) {
            Log.d(TAG, "From: " + message.getFrom());
            Log.d(TAG, "Notification Message Body: " + message.getData());
            //Calling method to generate notification
            check = message.getData().get("key2");
            //booking_id need to get this for further process

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ReceiveNotification(message.getData().get("message"));
            } else {
                receiveNotification(message.getData().get("message"));
            }

        } else {
            Log.d(TAG, "FCM Notification failed");
        }
    }

    private void receiveNotification(String messageBody) {
        if (check.equalsIgnoreCase("PrincipalToStudent")) {

            Intent intent = new Intent(this, Student.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("status", "2");
            intent.putExtra("Notification", messageBody);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder;

            if (Build.VERSION.SDK_INT < 26) {
                notificationBuilder = new NotificationCompat.Builder(this);
            } else {
                notificationBuilder = new NotificationCompat.Builder(this, "");
            }
            notificationBuilder.setContentTitle(getResources().getString(R.string.app_name))
                    .setContentTitle(count + " Message From Principal GCE,Bodi")
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setAutoCancel(true);

            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder), 1);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        } else {
            Intent intent = new Intent(this, Principal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("status", "2");
            intent.putExtra("Notification", messageBody);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder;

            if (Build.VERSION.SDK_INT < 26) {
                notificationBuilder = new NotificationCompat.Builder(this);
            } else {
                notificationBuilder = new NotificationCompat.Builder(this, "");
            }

            notificationBuilder.setContentTitle(getResources().getString(R.string.app_name))
                    .setContentTitle(count + " Message From Student GCE,Bodi")
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setAutoCancel(true);
            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder), 1);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }


    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
            return R.mipmap.ic_launcher;
        } else {
            return R.mipmap.ic_launcher;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ReceiveNotification(String messageBody) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String id = "id_product";
        // The user-visible name of the channel.
        CharSequence name = "Product";
        // The user-visible description of the channel.
        String description = "Notifications regarding our products";
        int importance = NotificationManager.IMPORTANCE_MAX;
        @SuppressLint("WrongConstant")
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);

        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
//        mChannel.setLightColor(R.color.colorPrimary);
        notificationManager.createNotificationChannel(mChannel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (check.equalsIgnoreCase("PrincipalToStudent")) {
            Intent intent = new Intent(this, Student.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("status", "2");
            intent.putExtra("Notification", messageBody);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 123, intent, PendingIntent.FLAG_ONE_SHOT);


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "id_product")
                    .setContentTitle(count + " Message From Principal GCE,Bodi")
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setAutoCancel(true);

            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder), 1);
            notificationManager.notify(1, notificationBuilder.build());

        } else {
            Intent intent = new Intent(this, Principal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("status", "2");
            intent.putExtra("Notification", messageBody);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 123, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "id_product")
                    .setContentTitle(count + " Message From Student GCE,Bodi")
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setAutoCancel(true);

            notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder), 1);
            notificationManager.notify(1, notificationBuilder.build());
        }
    }


    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}