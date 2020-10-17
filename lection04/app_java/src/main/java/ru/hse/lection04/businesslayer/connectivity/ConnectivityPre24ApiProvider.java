package ru.hse.lection04.businesslayer.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.hse.lection04.objects.ConnectivityInfo;

public class ConnectivityPre24ApiProvider extends AbstractConnectivityProvider {
    protected final ConnectivityReceiver mReceiver = new ConnectivityReceiver();

    protected final Context mContext;


    public ConnectivityPre24ApiProvider(Context context) {
        super(context);

        mContext = context;
    }


    @Override
    protected void setActivation(boolean value) {
        super.setActivation(value);

        if (value) {
            mReceiver.register(mContext);
        } else {
            mReceiver.unregister(mContext);
        }
    }


    protected void networkChanged(NetworkInfo fallbackInfo) {
        final NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        final ConnectivityInfo info = extractInfo(networkInfo, fallbackInfo);

        for (IListener listener: mListeners) {
            listener.networkUpdated(this, info);
        }
    }

    protected ConnectivityInfo extractInfo(NetworkInfo networkInfo, NetworkInfo fallbackInfo) {
        final ConnectivityInfo info = new ConnectivityInfo();

        if (fallbackInfo != null && fallbackInfo.isConnectedOrConnecting()) {
            info.type = networkInfo.getTypeName();
        } else if (networkInfo == null && fallbackInfo == null) {
            info.type = ConnectivityInfo.NO_CONNECTION;
        } else if (networkInfo != null && fallbackInfo != null && networkInfo.isConnectedOrConnecting() != fallbackInfo.isConnectedOrConnecting()) {
            info.type = fallbackInfo.getTypeName();
        } else {
            // info.type = fallbackInfo.getTypeName();
        }

        return info;
    }


    protected class ConnectivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final NetworkInfo fallbackInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

            networkChanged(fallbackInfo);
        }


        public void register(Context context) {
            final IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(this, filter);
        }

        public void unregister(Context context) {
            context.unregisterReceiver(this);
        }
    }
}
