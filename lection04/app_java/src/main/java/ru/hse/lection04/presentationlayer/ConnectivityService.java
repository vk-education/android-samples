package ru.hse.lection04.presentationlayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

import ru.hse.lection04.R;
import ru.hse.lection04.businesslayer.Channels;
import ru.hse.lection04.businesslayer.LogProvider;
import ru.hse.lection04.businesslayer.ServiceLocator;
import ru.hse.lection04.businesslayer.connectivity.AbstractConnectivityProvider;
import ru.hse.lection04.objects.ConnectivityInfo;

public class ConnectivityService extends Service {
    protected static final int FOREGROUND_ID = 1338;

    protected static final String MESSAGE_CREATE = "SERVICE: CREATED";
    protected static final String MESSAGE_DESTROY = "SERVICE: DESTROYED";
    protected static final String MESSAGE_NO_CONNECTIVITY = "CONNECTIVITY: NO CONNECTIVITY";
    protected static final String MESSAGE_CONNECTIVITY_PATTERN = "CONNECTIVITY: type = %s ";


    public static Intent newInstance(Context context) {
        return new Intent(context, ConnectivityService.class);
    }


    protected final AbstractConnectivityProvider mConnectivityProvider = ServiceLocator.getConnectivityProvider();
    protected final LogProvider mLogProvider = ServiceLocator.getLogProvider();

    protected final ConnectivityListener mConnectivityListener = new ConnectivityListener();


    @Override
    public void onCreate() {
        super.onCreate();

        // Foreground сервис требует отображение уведомления в статусбаре
        final Notification notification = buildNotification();
        startForeground(FOREGROUND_ID, notification);


        final Date date = Calendar.getInstance().getTime();
        mLogProvider.write(date, MESSAGE_CREATE);

        mConnectivityProvider.register(mConnectivityListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mConnectivityProvider.unregister(mConnectivityListener);

        final Date date = Calendar.getInstance().getTime();
        mLogProvider.write(date, MESSAGE_DESTROY);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    // Конструктор уведомдения
    protected Notification buildNotification() {
        // Куда ведем, если кликнули на уведомление
        final Intent notificationIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Канал, в рамках которого запускаем уведомление
        final Channels channel = Channels.CONNECTIVITY;

        // Констурируем уведомление при помощи класса, который обеспечивает обратную совместимость
        return new NotificationCompat.Builder(this, channel.id)
                .setContentTitle(getText(R.string.caption_service_notification_title))
                .setContentText(getText(R.string.caption_service_notification_description))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.caption_service_notification_ticker))
                .build();
    }

    protected void connectivityUpdated(ConnectivityInfo info) {
        final String message;
        if (info == null) {
            message = MESSAGE_NO_CONNECTIVITY;
        } else {
            message = String.format(MESSAGE_CONNECTIVITY_PATTERN, info.type);
        }

        final Date time = Calendar.getInstance().getTime();

        mLogProvider.write(time, message);
    }


    protected class ConnectivityListener implements AbstractConnectivityProvider.IListener {
        @Override
        public void networkUpdated(AbstractConnectivityProvider provider, ConnectivityInfo info) {
            connectivityUpdated(info);
        }
    }
}
