package ru.hse.lection04.businesslayer.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import ru.hse.lection04.objects.ConnectivityInfo;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ConnectivityProvider extends AbstractConnectivityProvider {
    protected static final String TYPE_UNKNOWN = "UNKNOWN";

    private static final String[] TRANSPORT_NAMES = {
            "CELLULAR",
            "WIFI",
            "BLUETOOTH",
            "ETHERNET",
            "VPN",
            "WIFI_AWARE",
            "LOWPAN",
            "TEST"
    };

    protected final ConnectivityCallback mConnectivityCallback = new ConnectivityCallback();


    public ConnectivityProvider(Context context) {
        super(context);
    }


    @Override
    protected void setActivation(boolean value) {
        super.setActivation(value);

        if (value) {
            mConnectivityManager.registerDefaultNetworkCallback(mConnectivityCallback);
        } else {
            mConnectivityManager.unregisterNetworkCallback(mConnectivityCallback);
        }
    }


    protected void networkChanged(Network network) {
        final ConnectivityInfo info = extractInfo(network);

        for (IListener listener: mListeners) {
            listener.networkUpdated(this, info);
        }
    }

    protected ConnectivityInfo extractInfo(Network network) {
        final ConnectivityInfo info = new ConnectivityInfo();

        if (network != null) {
            final NetworkCapabilities capabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());
            if (capabilities != null) {
                info.type = extractType(capabilities);
            }
        }

        return info;
    }

    protected String extractType(NetworkCapabilities capabilities) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_CELLULAR];
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_WIFI];
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_ETHERNET];
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_VPN];
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
            return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_BLUETOOTH];
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)) {
                return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_WIFI_AWARE];
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN)) {
                return TRANSPORT_NAMES[NetworkCapabilities.TRANSPORT_LOWPAN];
            }
        }

        return TYPE_UNKNOWN;
    }


    protected class ConnectivityCallback extends ConnectivityManager.NetworkCallback {
        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);

            networkChanged(network);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);

            networkChanged(null);
        }
    }
}
