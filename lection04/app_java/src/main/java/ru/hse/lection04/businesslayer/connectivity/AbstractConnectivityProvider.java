package ru.hse.lection04.businesslayer.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;

import ru.hse.lection04.businesslayer.AbstractCallbackProvider;
import ru.hse.lection04.datalayer.ConnectivityDataAccessor;
import ru.hse.lection04.objects.ConnectivityInfo;

public class AbstractConnectivityProvider extends AbstractCallbackProvider<AbstractConnectivityProvider.IListener> {
    public interface IListener {
        public void networkUpdated(AbstractConnectivityProvider provider, ConnectivityInfo info);
    }


    protected final ConnectivityManager mConnectivityManager;
    protected final ConnectivityDataAccessor mAccessor;


    public AbstractConnectivityProvider(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mAccessor = new ConnectivityDataAccessor(context);
    }

    public boolean isTrackEnabled() {
        return mAccessor.isTrackEnabled();
    }

    public boolean setTrackEnabled(boolean value) {
        if (isTrackEnabled() == value) {
            return false;
        }

        mAccessor.setTrackEnabled(value);

        return true;
    }
}
