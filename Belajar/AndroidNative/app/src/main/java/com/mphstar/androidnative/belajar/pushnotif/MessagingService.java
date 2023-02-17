package com.mphstar.androidnative.belajar.pushnotif;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mphstar.androidnative.R;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null){
            String title = remoteMessage.getNotification().getTitle();
            String text = remoteMessage.getNotification().getBody();
            //calling method to display notification
            NotificationHelper.displayNotification(getApplicationContext(), title, text, getString(R.string.CHANNEL_ID));
        }
    }
}