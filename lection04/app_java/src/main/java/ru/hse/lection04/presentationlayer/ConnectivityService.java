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


/**
 * Сервис для остлеживания изменения состояния соединения в фоне
 */
public class ConnectivityService extends Service {
    /**
     * Идентификатор уведомления, которое будет оторажаться в статусбаре
     */
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

        // Делаем запись в Лог о том, что сервис создан. Значит трекинг начался
        writeLog(MESSAGE_CREATE);

        // Подписываемся на изменение состояния подключения
        mConnectivityProvider.register(mConnectivityListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Отписываемся от получения состояния подключения
        mConnectivityProvider.unregister(mConnectivityListener);

        // Делаем запись в Лог о том, что сервис уничтожен. Значит трекинг прекратился
        writeLog(MESSAGE_DESTROY);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Один из возможных интерфейсов общения. Поскольку у нас сервис выполняет фоновую работу, байндер ему не нужен.
        return null;
    }


    /**
     * Конструктор уведомдения
     * @return Notification с информацией о сервисе
     */
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

    /**
     * Обрабатываем обновление подключения
     * @param info Информация о подключении
     */
    protected void connectivityUpdated(ConnectivityInfo info) {
        final String message;
        if (info == null) {
            message = MESSAGE_NO_CONNECTIVITY;
        } else {
            message = String.format(MESSAGE_CONNECTIVITY_PATTERN, info.type);
        }

        writeLog(message);
    }

    /**
     * Пишем в LogProvшder
     * @param message новая запись
     */
    protected void writeLog(String message) {
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
