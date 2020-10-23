package com.his.android.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.his.android.Activity.ChatActivity;
import com.his.android.Activity.NotificationDetail;
import com.his.android.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String CHANNEL_ID = "his_channel";
    String CHANNEL_NAME= "HIS";
    String CHANNEL_DESC = "HIS Channel";

    @Override
    public void onNewToken(String token) {
        Log.v(TAG, "Refreshed token: " + token);
        //sendRegistrationToServer(token);
    }
    public void showNotification(String title, String message, String tag) {
        Intent intent;
        if(title.equalsIgnoreCase("Reply Chat Message") || title.equalsIgnoreCase("New Chat Message")){
            intent=new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra("pid", tag.split("-")[1]);
            intent.putExtra("chatId", tag.split("-")[0]);
            intent.putExtra("title", title);
        }else {
            intent = new Intent(getApplicationContext(), NotificationDetail.class);
            intent.putExtra("nType", tag.split("-")[0]);
            intent.putExtra("title", title);
            intent.putExtra("nId", tag.split("-")[1]);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.logo)
                .setNumber(3)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setAutoCancel(false);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(99, notificationBuilder.build());
        }
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, "from" + remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.v(TAG, "Message data payload: " + remoteMessage.getData());
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //  scheduleJob();
            } else {
                // Handle message within 10 seconds
                //  handleNow();
            }
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "notification1:" + remoteMessage.getNotification().getTag());
            Log.d(TAG, "notification1:" + remoteMessage.getData().get("gcm.notification.extraParameter_1"));
            Log.d(TAG, "notification1:" + remoteMessage.getData().get("gcm.notification.e"));
            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTag());
        }
    }
}