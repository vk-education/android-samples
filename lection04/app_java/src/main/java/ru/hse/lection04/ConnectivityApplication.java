package ru.hse.lection04;

import android.app.Application;

import ru.hse.lection04.businesslayer.ServiceLocator;

public class ConnectivityApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ServiceLocator.initialize(this);

        ServiceLocator.getChannelProvider().initializeChannels(this);
    }
}
