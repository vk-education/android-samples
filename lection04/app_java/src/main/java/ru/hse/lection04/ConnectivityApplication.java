package ru.hse.lection04;

import android.app.Application;
import android.content.Context;

import ru.hse.lection04.businesslayer.ServiceLocator;

public class ConnectivityApplication extends Application {
    public static final String TAG = "ConnectivityApplication";


    // Если требуется обернуть контекст, то можно это сделать здесь
    @Override
    protected void attachBaseContext(Context base) {
//        final Context wrapped = new MyContextWrapper(base);

        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Инициализация нашего класса ServiceLocator - он будет поставлять провайдеры
        ServiceLocator.initialize(this);

        // Поскольку у нас есть уведомления в статусбаре, новые Android требуют для них "Каналы"
        ServiceLocator.getChannelProvider().initializeChannels(this);
    }
}
