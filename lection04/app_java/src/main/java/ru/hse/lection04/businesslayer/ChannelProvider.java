package ru.hse.lection04.businesslayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;


public class ChannelProvider {
    public void initializeChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkAndCreateChannels(context);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkAndCreateChannels(Context context) {
        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        for (Channels info: Channels.values()) {
            NotificationChannel channel = manager.getNotificationChannel(info.id);

            if (channel == null) {
                final String name = context.getString(info.nameResId);
                channel = new NotificationChannel(
                        info.id
                        , name
                        , NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setLightColor(Color.BLUE);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                manager.createNotificationChannel(channel);
            }
        }
    }
}
