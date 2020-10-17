package ru.hse.lection04.businesslayer;

import android.content.Context;
import android.os.Build;

import ru.hse.lection04.businesslayer.connectivity.AbstractConnectivityProvider;
import ru.hse.lection04.businesslayer.connectivity.ConnectivityPre24ApiProvider;
import ru.hse.lection04.businesslayer.connectivity.ConnectivityProvider;

public class ServiceLocator {
    protected static AbstractConnectivityProvider mConnectivityProvider;
    protected static ChannelProvider mChannelProvider;
    protected static LogProvider mLogProvider;


    public static void initialize(Context context) {
        mConnectivityProvider = initializeConnectivityProvider(context);
        mChannelProvider = new ChannelProvider();
        mLogProvider = new LogProvider(context);
    }

    public static AbstractConnectivityProvider getConnectivityProvider() {
        return mConnectivityProvider;
    }

    public static LogProvider getLogProvider() {
        return mLogProvider;
    }

    public static ChannelProvider getChannelProvider() {
        return mChannelProvider;
    }


    protected static AbstractConnectivityProvider initializeConnectivityProvider(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return new ConnectivityProvider(context);
        } else {
            return new ConnectivityPre24ApiProvider(context);
        }
    }
}
