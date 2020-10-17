package ru.hse.lection04.presentationlayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import ru.hse.lection04.businesslayer.ServiceLocator;
import ru.hse.lection04.businesslayer.connectivity.AbstractConnectivityProvider;

public class BootReceiver extends BroadcastReceiver {
    protected final AbstractConnectivityProvider mConnectivityProvider = ServiceLocator.getConnectivityProvider();


    @Override
    public void onReceive(Context context, Intent intent) {
        final boolean isRealInvoke = Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction());

        if (!isRealInvoke) {
            // Защита от того, что бы ресивер не использовался другим фильтром.
            return;
        }

        // Если у нас установлен трэкинг, надо будет запустить сервис
        if (mConnectivityProvider.isTrackEnabled()) {
            final Intent serviceIntent = ConnectivityService.newInstance(context);
            ContextCompat.startForegroundService(context, serviceIntent);
        }
    }
}
