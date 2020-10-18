package ru.hse.lection04.businesslayer.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;

import ru.hse.lection04.businesslayer.AbstractCallbackProvider;
import ru.hse.lection04.datalayer.ConnectivityDataAccessor;
import ru.hse.lection04.objects.ConnectivityInfo;

/**
 * Базовая спеуицикация для работы с информацией о соединении. С небольшой имплементацией - в виде информации о состоянии трекинга
 */
public class AbstractConnectivityProvider extends AbstractCallbackProvider<AbstractConnectivityProvider.IListener> {
    /**
     * Слушаьель, который будет уведомляться при изменении данных о соединении
     */
    public interface IListener {
        public void networkUpdated(AbstractConnectivityProvider provider, ConnectivityInfo info);
    }


    protected final ConnectivityManager mConnectivityManager;
    protected final ConnectivityDataAccessor mAccessor;


    public AbstractConnectivityProvider(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mAccessor = new ConnectivityDataAccessor(context);
    }

    /**
     * Состояние трекинга
     * @return true/false
     */
    public boolean isTrackEnabled() {
        return mAccessor.isTrackEnabled();
    }

    /**
     * Проверить и изменить запись о том, какое состояние трекере
     * @param value новое значение
     * @return true - если записалось новое значение. false, если обновление не произошло
     */
    public boolean setTrackEnabled(boolean value) {
        if (isTrackEnabled() == value) {
            return false;
        }

        mAccessor.setTrackEnabled(value);

        return true;
    }
}
